
import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * This class represents a member of our library. 
 * @author Hanieh Hosseinkhani
 */

public class Book {
	/**attributes of our book*/
	private String BookCode;
	private String Name;
	private String Author;
	private String Translator;
	private String Publisher;
	private int Year;
	private boolean existence;
	private String Genres;
	private Member [] Member = new Member[300];

	/** Setter and Getter Methods*/
	public void setBookCode(String BookCode) {this.BookCode = BookCode;}
	public String getBookCode() {return BookCode;

	}
	public void setName(String Name) {this.Name = Name;}
	public String getName() {return Name;
	}
	public void setAuthor(String Author) {this.Author = Author;}
	public String getAuthor() {return Author;
	}
	public void setTranslator(String Translator) {this.Translator = Translator;}
	public String getTranslator() {return Translator;
	}
	public void setPublisher(String Publisher) {this.Publisher = Publisher;}
	public String getPublisher() {return Publisher;
	}
	public void setYear(int Year) {this.Year = Year;}
	public int getYear() {return Year;
	}
	public boolean isExistence() {return existence;}
	public void setExistence(boolean existence) {this.existence = existence;
	}
	public void setGenres(String Genres) {this.Genres = Genres;}
	public String getGenres() {return Genres;
	}

	public Member[] getMember() {
		return Member;
	}
	public void setMember(Member[] member) {
		Member = member;
	}
	/**We use Constructor to build a new book*/
	public Book( String name, String author, String translator, String publisher, int year, String genres  ) {
		setName(name);
		setAuthor(author);
		setTranslator(translator);
		setPublisher(publisher);
		setYear(year);
		setGenres(Genres);
	}
	/**We use this to build book code*/

	public static String BookCode(String Author, int Years , int counter ) {//We use this to build book code
		char a = Author.toUpperCase().charAt(0);
		int h = 0;
		while(Author.charAt(h)!=' ') {
			h++;
		}
		char b = Author.toUpperCase().charAt(h+1);
		int c = (Years%100);
		String d = String.valueOf(c);
		int e = 1000+counter ;
		String end = e +"/"+a+b+"/"+d ;//Book number plus first name and last name plus two last year digits
		return end;
	}

	/**Add new book information using information entered by the user
	 * we ask the user to enter the name of her book,author of the book,translator of the book,name of the book publisher,new Publication Year
	 * @param b
	 * @param counter
	 * @param length
	 */

	public static void AddNewBooks(Book []b ,int counter, int length) {//Add new book information using information entered by the user
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter your new BookName^^");//Here we ask the user to enter the name of her book
		String na = scanner.nextLine();

		System.out.println("Please enter your new BookAuthor^^");//Here we ask the user to enter the name of the author of the book
		String au = scanner.nextLine();

		System.out.println("Please enter your new BookTranslator^^");//Here we ask the user to enter the name of the translator of the book
		String tr = scanner.nextLine();

		System.out.println("Please enter your new BookPublisher^^");//Here we ask the user to enter the name of the book publisher
		String pu = scanner.next();
		int ye;
		while(true) {
			try {
				System.out.println("Please enter your new Publication Year^^");
				ye = scanner.nextInt();
				break;
			}catch(InputMismatchException e) {
				System.out.println("wrong!!!");
			}finally {
				scanner.nextInt();
			}
		}
		scanner.nextLine();
		System.out.println("Please enter your new genres^^");
		String ge = scanner.nextLine();
		if(ge.equals("Precious")) {
			System.out.println("Please enter your precious book kind^^:");
			String ki = null;
			int kn=0;

			while(true) {
				try {


					System.out.println("Hey!!! You Can Choose Your Precious Book kind!!!\n" 

					+ " 1.Handwritten*-* \n"
					+ " 2.Fragrant*-* \n"
					+ " 3.Leathern*-* \n"
							);
					kn = scanner.nextInt();

					break;
				}catch(InputMismatchException e) {
					System.out.println("wrong!!");
				}finally {
					scanner.nextInt();
				}

			}
			if(kn==1) {
				ki="Handwritten";
			}


			if(kn==2) {
				ki="Fragrant";
			}


			if(kn==3) {
				ki="Leathern";
			}else {
				System.out.println("choose one of them");
			}
			Precious b1 = new Precious(na, au, tr, pu, ye, ki);
			b1.setBookCode(BookCode(au , ye ,counter));//Creating a book code and setting it

			b[length] = b1;//Pour in the array
			System.out.println(b1.getBookCode()) ;}


		if(ge.equals("Teenager")) {
			System.out.println("Now Please enter the age category of your children's book^^:");
			char gr = scanner.next().charAt(0);



			while(gr!='a' && gr!='b' && gr!='c' && gr!='d' && gr!='A' && gr!='B' && gr!='C' && gr!='D') {//The gender that is selected with f or m is entered by the user. I create a loop that if the user enters something other than f or m for the gender, this loop will continue until the user enters the correct data.

				System.out.println("Oh No!! You Should Enter a or A or B or C or D based On Your Age Goupe!! :");
				gr = scanner.next().charAt(0);//Will show the user, enter f or m and receive the character from the user
			}

			Teenager b1 = new Teenager(na, au, tr, pu, ye, gr);

			b1.setBookCode(BookCode(au , ye ,counter));//Creating a book code and setting it
			b[length] = b1;//Pour in the array
			System.out.println(b1.getBookCode()) ;

		}


		else {
			Book b1 = new Book(na, au, tr, pu, ye, ge);//Make a new book
			b1.setBookCode(BookCode(au , ye ,counter));//Creating a book code and setting it
			b[length] = b1;//Pour in the array
			System.out.println(b1.getBookCode()) ;

		}}


	public static int searchInfo(String e , int f , Book[] b1 ) { //We look at the book array to see which member of the array is the same as the code given to us by the user
		int tra = -1 ;
		for(int nam = 0 ; nam < f ; nam++ ) { 
			if(b1[nam].BookCode.equalsIgnoreCase(e)) { 
				tra = nam ; 
			}
		}
		return tra ;
	}


	public void ShowBookInfo() { //The book information is displayed to us
		System.out.println("Book Name: "+this.getName()); 
		System.out.println("Book Author: "+this.getAuthor());
		System.out.println("Book Translator: "+this.getTranslator()); 
		System.out.println("Book Publisher: "+this.getPublisher()); 
		System.out.println("Year Of Publication: "+this.getYear());
		System.out.println("Book Genres: "+this.getGenres());
	}



	/**We are editing the information of the previously entered book here
		//If the user does not enter new information and specifications, the same information will be saved*/


	public void EditInfo(Book[]b1 , String e ) { //We are editing the information of the previously entered book here
		//If the user does not enter new information and specifications, the same information will be saved
		Scanner scanner = new Scanner(System.in) ;
		int h = e.charAt(1) + e.charAt(2) + e.charAt(3);


		System.out.println("Hey^^ Now You Can Enter your correct Book Name!!: ") ; 
		String k = scanner.nextLine();
		if(k.isEmpty() == false){ 
			this.setName(k);
		}
		System.out.println("Hey^^ Now You Can Enter your correct Book Author!!: ") ;
		String m = scanner.nextLine();
		if(m.isEmpty()== false){
			this.setAuthor(m);}
		System.out.println("Hey^^ Now You Can Enter your correct Book Translator!!: "); 
		String n = scanner.nextLine();
		if(n.isEmpty() == false){
			this.setTranslator(n);}
		System.out.println("Hey^^ Now You Can Enter your correct Book Publisher!!: ") ; 
		String p = scanner.nextLine();
		if(p.isEmpty()==false){
			this.setPublisher(p);}

		String q;
		while(true) {
			try {



				System.out.println("Hey^^ Now You Can Enter your correct Year Of Publication!!: ") ;
				q = scanner.nextLine();
				break;
			}catch(InputMismatchException e1) {
				System.out.println("wrong!!");
			}finally {
				scanner.nextLine();
			}
		}
		if(q.isEmpty()==false){
			Integer q1 = Integer.valueOf(q);
			this.setYear(q1);}//The book code is changed and saved again
		this.setBookCode(BookCode(this.getAuthor(),this.getYear() , h));
		System.out.println("Dear User^^ These Are Your New Information^^: ") ; 
		System.out.println("your Book Name: "+this.getName()+"\nyour Book Author: "+this.getAuthor()+"\nyour Book Translator: "+this.getTranslator()+"\nyour Book Publisher: "+this.getPublisher()+"\nyour Year Of Publication: "+this.getYear()+"\nyour Book Code: "+this.getBookCode());

	}


	/**Here the information of a book is completely deleted*/

	public static void deleteInfo(Book[]b1 , String e , int f) { //Here the information of a book is completely deleted
		int nam = searchInfo(e , f , b1) ; 
		if(nam>=0){
			b1[nam] = null ; 
			if(nam != f) {	for(; nam<f ; nam++) { 
				b1[nam] = b1[nam+1] ;
			}
			}
		}else{System.out.println("We Are So Sorry!!! But There is no result For That!!!");}
	}


	/**
	 * To search the book code is for when the user gives us the name and we want to find the book code
	 * If the name entered by the user is part of the name of the members of our book array, we will print the information
	 * @param b
	 * @param n
	 * @param l
	 */

	public static void Code(Book[] b, String n, int l ) {//To search the book code is for when the user gives us the name and we want to find the book code
		Scanner scanner = new Scanner(System.in) ;
		for(int i = 0; i<l; i++) {
			if(b[i].getName().contains(n)) {//If the name entered by the user is part of the name of the members of our book array, we will print the information
				System.out.println(i);
				System.out.println("Book Name: "+b[i].getName()); 
				System.out.println("Book Author: "+b[i].getAuthor());
				System.out.println("Book Translator: "+b[i].getTranslator()); 
				System.out.println("Book Publisher: "+b[i].getPublisher()); 
				System.out.println("Year Of Publication: "+b[i].getYear());
			}
		}
		System.out.println("Choose: ");
		int c = scanner.nextInt();
		System.out.println(b[c].BookCode);

	}



	public void Printlist() {
		int i=0;
		while( i<this.Member.length ) {
			System.out.println(this.Member[i].getName());
			i++;
		}
		if(this.isExistence()) {
			System.out.println("Sorry, but this book has not been borrowed*-*");
		}else {System.out.println(this.Member[i].getName());
		}
	}
}