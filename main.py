import os
import sys
import random
import subprocess
import webbrowser
import time
import numpy as np
import torch
from torch.utils.data import Dataset, DataLoader
from transformers import BertTokenizer, BertForSequenceClassification, get_linear_schedule_with_warmup
from torch.optim import AdamW
from sklearn.metrics import accuracy_score, f1_score
from sklearn.model_selection import train_test_split
from datasets import load_dataset
import pyttsx3
import speech_recognition as sr
from datetime import datetime

# -----------------------------
# 0. Configuration
# -----------------------------
SEED = 42
BATCH_SIZE = 16
EPOCHS = 3
LR = 2e-5
MAX_LEN = 64
CONFIDENCE_THRESHOLD = 0.40  # ML fallback confidence threshold

# -----------------------------
# 1. Reproducibility
# -----------------------------


def set_seed(seed=SEED):
    random.seed(seed)
    np.random.seed(seed)
    torch.manual_seed(seed)
    torch.cuda.manual_seed_all(seed)


set_seed()
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
print("Using device:", device)

# -----------------------------
# 2. Text-to-Speech
# -----------------------------


def speak(text):
    try:
        engine = pyttsx3.init()
        engine.say(text)
        engine.runAndWait()
    except Exception as e:
        print("[TTS error]", e)
        print("Assistant:", text)

# -----------------------------
# 3. OS Helpers & Actions
# -----------------------------


def _is_windows(): return sys.platform.startswith("win")
def _is_mac(): return sys.platform == "darwin"


def open_browser():
    webbrowser.open("https://www.google.com")
    speak("I opened the browser for you")


def tell_time():
    current_time = datetime.now().strftime("%H:%M")
    speak(f"The time is {current_time}")


def open_notepad():
    try:
        if _is_windows():
            subprocess.Popen(["notepad.exe"])
        elif _is_mac():
            subprocess.Popen(["open", "-a", "TextEdit"])
        else:
            for cmd in (["gedit"], ["xed"], ["kate"], ["leafpad"], ["mousepad"]):
                try:
                    subprocess.Popen(cmd)
                    break
                except Exception:
                    continue
            else:
                webbrowser.open("https://docs.new")
        speak("Opened a text editor.")
    except Exception:
        webbrowser.open("https://docs.new")
        speak("I opened a web editor instead.")


def open_calculator():
    try:
        if _is_windows():
            subprocess.Popen(["calc.exe"])
        elif _is_mac():
            subprocess.Popen(["open", "-a", "Calculator"])
        else:
            for cmd in (["gnome-calculator"], ["kcalc"], ["qalculate-gtk"], ["xcalc"]):
                try:
                    subprocess.Popen(cmd)
                    break
                except Exception:
                    continue
            else:
                webbrowser.open("https://www.google.com/search?q=calculator")
        speak("Calculator opened.")
    except Exception:
        webbrowser.open("https://www.google.com/search?q=calculator")
        speak("I opened a web calculator.")


def play_youtube():
    webbrowser.open("https://www.youtube.com")
    speak("I opened YouTube for you")


def say_hello():
    speak("Hello! How can I help you?")


def say_goodbye():
    speak("Goodbye! See you later.")
    time.sleep(0.5)
    sys.exit(0)


# -----------------------------
# 4. Load SNIPS + add custom intents
# -----------------------------
print("Loading SNIPS dataset...")
snips = load_dataset("snips_built_in_intents")
snips_texts = list(snips["train"]["text"])
snips_labels = list(snips["train"]["label"])
snips_label_names = snips["train"].features["label"].names
print("SNIPS label names:", snips_label_names)

custom_intent_names = [
    "open_browser_custom",
    "open_notepad_custom",
    "open_calculator_custom",
    "play_youtube_custom",
    "greet_custom",
    "goodbye_custom"
]

custom_phrases = {
    "open_browser_custom": ["open the browser", "please open browser", "launch browser", "open google", "start a web browser"],
    "open_notepad_custom": ["open notepad", "open text editor", "start notepad", "please open notepad", "open a text editor"],
    "open_calculator_custom": ["open calculator", "start calculator", "please open calculator", "launch calculator"],
    "play_youtube_custom": ["open youtube", "play youtube", "play a youtube video", "open youtube please", "launch youtube"],
    "greet_custom": ["hello", "hi", "hey", "good morning", "good evening"],
    "goodbye_custom": ["goodbye", "bye", "see you", "exit", "stop assistant"]
}

all_label_names = snips_label_names + custom_intent_names
label2id = {name: idx for idx, name in enumerate(all_label_names)}
id2label = {v: k for k, v in label2id.items()}

texts, labels = [], []

# SNIPS examples
for t, l in zip(snips_texts, snips_labels):
    texts.append(t)
    labels.append(l)

# Custom examples: duplicate each 5x for stratify
for cname in custom_intent_names:
    cid = label2id[cname]
    examples = custom_phrases.get(cname, [])
    for ex in examples:
        for _ in range(5):  # duplicate
            texts.append(ex)
            labels.append(cid)

print("Total combined examples:", len(texts),
      "Total labels:", len(set(labels)))

# -----------------------------
# 5. Train/Val/Test Split
# -----------------------------
print("Splitting into train/val/test...")
train_texts, temp_texts, train_labels, temp_labels = train_test_split(
    texts, labels, test_size=0.25, random_state=SEED, stratify=labels
)
val_texts, test_texts, val_labels, test_labels = train_test_split(
    temp_texts, temp_labels, test_size=0.5, random_state=SEED, stratify=temp_labels
)
print(
    f"Train: {len(train_texts)}, Val: {len(val_texts)}, Test: {len(test_texts)}")

# -----------------------------
# 6. Dataset class + Tokenizer
# -----------------------------
tokenizer = BertTokenizer.from_pretrained("bert-base-uncased")


class IntentDataset(Dataset):
    def __init__(self, texts, labels, tokenizer, max_len=MAX_LEN):
        self.texts = texts
        self.labels = labels
        self.tokenizer = tokenizer
        self.max_len = max_len

    def __len__(self): return len(self.texts)

    def __getitem__(self, idx):
        text = str(self.texts[idx])
        label = int(self.labels[idx])
        encoding = self.tokenizer(
            text, add_special_tokens=True, padding="max_length",
            truncation=True, max_length=self.max_len, return_tensors="pt"
        )
        return {
            "input_ids": encoding["input_ids"].squeeze(0),
            "attention_mask": encoding["attention_mask"].squeeze(0),
            "labels": torch.tensor(label, dtype=torch.long)
        }


train_dataset = IntentDataset(train_texts, train_labels, tokenizer)
val_dataset = IntentDataset(val_texts, val_labels, tokenizer)
test_dataset = IntentDataset(test_texts, test_labels, tokenizer)

train_loader = DataLoader(train_dataset, batch_size=BATCH_SIZE, shuffle=True)
val_loader = DataLoader(val_dataset, batch_size=BATCH_SIZE)
test_loader = DataLoader(test_dataset, batch_size=BATCH_SIZE)

# -----------------------------
# 7. Model setup
# -----------------------------
num_labels = len(all_label_names)
model = BertForSequenceClassification.from_pretrained(
    "bert-base-uncased", num_labels=num_labels)
model.to(device)
optimizer = AdamW(model.parameters(), lr=LR)

# -----------------------------
# 8. Training + evaluation
# -----------------------------


def evaluate(loader, name="Validation", silent=False):
    model.eval()
    preds, golds = [], []
    with torch.no_grad():
        for batch in loader:
            input_ids = batch["input_ids"].to(device)
            attention_mask = batch["attention_mask"].to(device)
            labels_b = batch["labels"].to(device)
            logits = model(input_ids, attention_mask=attention_mask).logits
            preds.extend(torch.argmax(logits, -1).cpu().numpy())
            golds.extend(labels_b.cpu().numpy())
    acc = accuracy_score(golds, preds)
    f1 = f1_score(golds, preds, average="weighted")
    if not silent:
        print(f"{name} Accuracy: {acc:.4f}, F1: {f1:.4f}")
    return acc, f1


def train_model(epochs=EPOCHS):
    total_steps = len(train_loader) * epochs
    scheduler = get_linear_schedule_with_warmup(
        optimizer, int(0.1 * total_steps), total_steps)
    best_f1 = -1.0
    for epoch in range(1, epochs + 1):
        model.train()
        running_loss = 0.0
        for batch in train_loader:
            optimizer.zero_grad()
            input_ids = batch["input_ids"].to(device)
            attention_mask = batch["attention_mask"].to(device)
            labels_b = batch["labels"].to(device)
            out = model(input_ids, attention_mask=attention_mask,
                        labels=labels_b)
            loss = out.loss
            loss.backward()
            torch.nn.utils.clip_grad_norm_(model.parameters(), 1.0)
            optimizer.step()
            scheduler.step()
            running_loss += loss.item()
        avg_loss = running_loss / len(train_loader)
        val_acc, val_f1 = evaluate(
            val_loader, name=f"Validation (epoch {epoch})", silent=True)
        print(
            f"[Epoch {epoch}] loss={avg_loss:.4f} val_acc={val_acc:.3f} val_f1={val_f1:.3f}")
        if val_f1 > best_f1:
            best_f1 = val_f1
            torch.save(model.state_dict(), "best_combined.pt")
    print(f"Training done. Best val F1 = {best_f1:.3f}")


# -----------------------------
# 9. Predict & Act
# -----------------------------
custom_action_map = {
    label2id["open_browser_custom"]: open_browser,
    label2id["open_notepad_custom"]: open_notepad,
    label2id["open_calculator_custom"]: open_calculator,
    label2id["play_youtube_custom"]: play_youtube,
    label2id["greet_custom"]: say_hello,
    label2id["goodbye_custom"]: say_goodbye
}


def predict_and_act(text, threshold=CONFIDENCE_THRESHOLD):
    text_lower = text.lower()
    # Rules first
    if any(k in text_lower for k in ["browser", "open google", "open the browser", "open browser"]):
        open_browser()
        return
    if "youtube" in text_lower:
        play_youtube()
        return
    if any(k in text_lower for k in ["notepad", "text editor", "open notepad", "open text editor"]):
        open_notepad()
        return
    if "calculator" in text_lower:
        open_calculator()
        return
    if "time" in text_lower and "what" in text_lower or text_lower.strip().startswith("time"):
        tell_time()
        return
    if any(g in text_lower for g in ["hello", "hi", "hey"]):
        say_hello()
        return
    if any(b in text_lower for b in ["bye", "goodbye", "see you", "exit", "stop"]):
        say_goodbye()
        return

    # ML fallback
    model.eval()
    encoding = tokenizer(text, padding="max_length",
                         truncation=True, max_length=MAX_LEN, return_tensors="pt")
    input_ids = encoding["input_ids"].to(device)
    attention_mask = encoding["attention_mask"].to(device)
    with torch.no_grad():
        outputs = model(input_ids, attention_mask=attention_mask)
        probs = torch.nn.functional.softmax(outputs.logits, dim=-1)
        confidence, pred = torch.max(probs, dim=-1)
        pred_id = int(pred.item())
        confidence = float(confidence.item())

    if confidence < threshold:
        speak("Sorry, I didn't catch that clearly. Could you repeat?")
        return

    if pred_id in custom_action_map:
        custom_action_map[pred_id]()
        return

    intent_name = id2label[pred_id] if pred_id in id2label else f"intent_{pred_id}"
    speak(f"I understood intent: {intent_name} (confidence {confidence:.2f}).")

# -----------------------------
# 10. Voice listening loop
# -----------------------------


def listen_loop():
    try:
        mic = sr.Microphone()
    except Exception as e:
        print("Microphone error:", e)
        speak("Microphone not available. Exiting.")
        return

    r = sr.Recognizer()
    with mic as source:
        r.adjust_for_ambient_noise(source, duration=1)
    speak("Assistant is ready and listening. Say 'goodbye' to stop.")

    while True:
        r = sr.Recognizer()
        with mic as source:
            print("Listening... (speak now)")
            try:
                audio = r.listen(source, timeout=12, phrase_time_limit=7)
                text = r.recognize_google(audio)
                print("You said:", text)
            except sr.WaitTimeoutError:
                continue
            except sr.UnknownValueError:
                speak("Sorry, I couldn't understand. Please repeat.")
                continue
            except sr.RequestError as e:
                print("Speech recognition request error:", e)
                speak("There is a problem with the speech recognition service.")
                time.sleep(2)
                continue
        if text.lower().strip() in {"stop", "quit", "exit", "goodbye", "bye"}:
            say_goodbye()
            break
        predict_and_act(text)


# -----------------------------
# 11. Main
# -----------------------------
if __name__ == "__main__":
    if os.path.exists("best_combined.pt"):
        print("Loading saved model 'best_combined.pt' ...")
        model.load_state_dict(torch.load(
            "best_combined.pt", map_location=device))
    else:
        print("Training model on SNIPS + custom intents ...")
        train_model(epochs=EPOCHS)
        if os.path.exists("best_combined.pt"):
            model.load_state_dict(torch.load(
                "best_combined.pt", map_location=device))

    evaluate(test_loader, name="Test")
    say_hello()
    listen_loop()
