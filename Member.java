
//Manage book borrowing and return operations
import java.util.Scanner; //program uses scanner
import java.util.InputMismatchException;
/**
 * This class represents a member of our library. 
 * @author Hanieh Hosseinkhani
 */
public class Member {//Create a member class
	/**attributes that are member's name, age, phone number, sex and membership id and An array in which we throw borrowed books*/
	private String name ;//Create attributes for names

	private short age;//Create attributes for age
	private String phoneNumber ;//Create attributes for phone numbers
	private char sex ; //Create attributes for gender
	private long ID ;//Create a feature for the membership number
	private Book [] Borrowed_Book = new Book[5];//An array in which we throw borrowed books
	private String UserType;
	private String Password;

	/**
	 * setter and getter For each private attribute we create cause we need a set to change and a get to access
	 */



	public void setName(String name) {this.name = name ; }//For each private attribute we create we need a set to change and a get to access and Here we use set to define the name and use this to refer to the name outside the method
	public String getName() {return name;}// we access the name using get

	public void setAge(short age) {this.age = age ; }//In the above part, we defined the name from string, but here we define age from short
	public short getAge() {return age;}

	public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber ; }// need more digits for the phone number, after the long gender will be defined
	public String getPhoneNumber() {return phoneNumber;}

	public void setSex(char sex) {this.sex = sex ;}//for gender, we use the character  f F  that indicates female and M m indicates male gender.
	public char getSex() {return sex;}

	public void setID(long ID) {this.ID = ID ;}//For the membership number, because we use a large number, we define long
	public long getID() {return ID ; }

	public Book[] getBorrowed_Book() {return Borrowed_Book;}
	public void setBorrowed_Book(Book[] borrowed_Book) {Borrowed_Book = borrowed_Book;}

	public String getUserType() {
		return UserType;
	}
	public void setUserType(String userType) {
		UserType = userType;
	}

	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	/**
	 * The constructor of the member class
	 * @param name,could be The full-name or first-name or last-name 
	 * @param age,has to be a short number
	 * @param sex,has to be f or m or F or M
	 * @param phonenumber, should be entered like 09.........
	 */

	public Member (String name, short age, char sex, String phoneno, String userType, String password) {
		this.setName(name);
		this.setAge(age);
		this.setSex(sex);
		this.setPhoneNumber(phoneno);
		this.setUserType(userType);
		this.setPassword(password);

	}

	/**search method to find a membership number
	 * 
	 * @param r
	 * @param LEn
	 * @param me
	 * @return d index of the target member
	 */

	static int search(long r , int LEn , Member[] me) {
		int d = -1 ;//create a variable  to determine if the membership number entered by the user exists among the existing membership numbers or not
		for(int sh = 0 ; sh <LEn ; sh++) {//Create a for loop to check from member's ideas to the last member
			if(me[sh].ID==r) {
				d = sh ;//If it is equal, we will equalize the variable d with sh, which is the index of the target member
			}
		}
		return d ;	
	}

	/**
	 * 
	 * @param me
	 * @param counter
	 * @param LEn
	 */

	static void case1(Member[]me , int counter , int LEn) {//Create a method for the first case

		Scanner scanner = new Scanner(System.in);
		System.out.println("Please Write Your Name^^ :");//The user will be prompted to enter their name
		String n = scanner.next();//The desired name will be taken from the user
		short a;
		while(true) {
			try {
				System.out.println("Please Write Your Age^^ :");//Like above only about age
				a = scanner.nextShort();
				break;
			}catch( InputMismatchException e) {
				System.out.println("wrong!!");

			}finally {
				scanner.nextShort();
			}
		}
		System.out.println("Please Write Your sex^^ :");//Like above only about gender
		char s = scanner.next().charAt(0);
		while(s!='F' && s!='f' && s!='m' && s!='M') {//The gender that is selected with f or m is entered by the user. I create a loop that if the user enters something other than f or m for the gender, this loop will continue until the user enters the correct data.

			System.out.println("Oh No! You Should Enter F or M based On Your Sex And Gender !! - :");
			s = scanner.next().charAt(0);//Will show the user, enter f or m and receive the character from the user
		}
		System.out.println("Please Write Your Phone Number^^ :");//show the user to enter the phone number
		System.out.println("09.........");
		String p = scanner.next();//We will receive the number from the user
		System.out.println("Please enter your type!!:");
		String u = scanner.next();
		while(u.equals("admin")== false && u.equals("ordinary")== false){
			System.out.println("Oh No! You Should Enter admin or ordinary :");
			u = scanner.next();
		}
		System.out.println("Please enter your password!!:");
		String w = scanner.next();
		me[LEn] = new Member(n, a, s, p, u, w);//Create new members in existing arrays
		System.out.println("So This Will Be Your ID^^ :");//We will show the user the desired idea
		me[LEn].setID(1000000 + counter);//We save an idea and an idea will be shown to the user

		System.out.println(me[LEn].getID());
		System.out.println("Its Done!!! Have Fun And Enjoy!!!");
	}
	/**
	 * 
	 * @param me
	 * @param p
	 * @param LEn
	 */
	static void case3(Member[]me , long p , int LEn) {//Create a method for the third case that has been named 3
		int sh = search(p , LEn  ,me);//We create a variable to use the search method and confirm or deny the existence of a membership number among the existing membership numbers
		if(sh>=0) {//If the membership number is confirmed, we will show the saved data to the user using get
			System.out.println("Name: "+me[sh].getName()) ;//The saved information is displayed
			System.out.println("Age: "+me[sh].getAge()) ;
			System.out.println("Sex: "+me[sh].getSex()) ;
			System.out.println("PhoneNumber: "+"09"+me[sh].getPhoneNumber()) ;
		}else {System.out.println(sh);}//And if the membership number given by the user was not confirmed, it will be displayed as sh = -1
	}

	static void case4(Member[]me , long r , int LEn) {//Create a method for the fourth case named 4
		Scanner scanner = new Scanner (System.in);
		int sh = search(r , LEn ,me);//We create a variable to use the search method and confirm or deny the existence of a membership number among the existing membership numbers
		if(sh>=0) {//If the membership number is confirmed, it will show the user to enter the correct name and we will get the desired name from the user and ... and finally it will show the corrected information to the user
			System.out.println("Please Enter The Correct Name^^ : ");
			me[sh].setName(scanner.next());
			short b =0;
			while(true) {

				try {
					System.out.println("Please Enter The Correct Age^^ : ");
					b = scanner.nextShort();



					break;
				}catch(InputMismatchException e) {
					System.out.println("wrong!!!");

				}finally {
					scanner.nextShort();
				}
			}
			me[sh].setAge(b);

			System.out.println("Please Enter The Correct Sex^^ : ");
			me[sh].setSex(scanner.next().charAt(0));
			System.out.println("Please Enter The Correct PhoneNumber^^ : ");
			me[sh].setPhoneNumber(scanner.next());
			System.out.println("Now You Can Have Your Correct And New Information^^ : ");
			System.out.println(me[sh].getName()+"\n"+me[sh].getAge()+"\n"+me[sh].getSex()+"\n"+me[sh].getPhoneNumber()) ;
		}else {System.out.println(sh);}
	}

	static void case5(Member[]me , long v , int LEn) {//Create a method for the fifth case named 5
		int sh = search(v , LEn , me);//We create a variable to use the search method and confirm or deny the existence of a membership number among the existing membership numbers, and if the existence of such a membership number is confirmed, we will set that member to null and delete it.
		if(sh>=0) {
			me[sh] = null;
			if(sh != LEn) { for(; sh<LEn ; sh++) {//If the deleted member is the last member received, there will be no problem, but if a user is deleted from among the users, so that the number of existing members is not confused and we have an index of the number of existing members,
				me[sh] = me[sh+1];//Use the for loop to continue counting until sh reaches the current number of members
			}
			}
		}else {System.out.println("We Are Sorry But There Is No Result For That So Please Try Again!!!");}
	}

	static boolean AddBorrowed( Member h, Book b1 ){
		int r = -1;
		for(int i = 0; i < h.Borrowed_Book.length; i++) {//Is the array of borrowed books full or not?
			if(h.getBorrowed_Book()[i] == null) {
				r = i;
				break;
			}
		}
		if(r == -1) {//An array of borrowed books is filled in the membership
			System.out.println("Sorry!!But You have filled the allowable ceiling and you can no longer borrow books!");
			return false;
		}
		else {//If it is not filled, the desired book is thrown in the array
			h.getBorrowed_Book()[r] = b1;
			return true;
		}
	}
	/**
	 * Just like above we wanted to see if it was completely filled or not
	 * We want to search and see if there is a book as input between the member arrays that we want to delete
	 * If there is such a book between the arrays, we delete it
	 * @param h
	 * @param b1
	 * @return
	 */
	static boolean DeleteReturned( Member h, Book b1 ) {
		int r = -1;
		for(int i = 0; i < h.Borrowed_Book.length; i++) {//Just like above we wanted to see if it was completely filled or not
			if(h.getBorrowed_Book()[i] == null) {
				r = i;
				break;
			}
		}
		if(r == -1) {
			r = h.Borrowed_Book.length;
		}
		int m = Book.searchInfo(b1.getBookCode(), r, h.Borrowed_Book);
		if(m == -1) {//We want to search and see if there is a book as input between the member arrays that we want to delete
			System.out.println("You did not borrow such a book!!");
			return false;
		}
		else {//If there is such a book between the arrays, we delete it
			h.getBorrowed_Book()[m] = null;
			if( m != r) {	
				while( m <= r ) {	
					h.getBorrowed_Book()[m] = h.getBorrowed_Book()[ m+1 ];
					m++;
				}
			}
			return true;
		}}
	/**
	 * It takes the ID and book code and in the main array of books and members, and if we do not have a member with this ID or a book with this bookcode, one of these two items becomes -1
	 * @param ID
	 * @param BookCode
	 * @param me
	 * @param bo
	 * @param f
	 * @param LEn
	 */
	static void ToLend(long ID, String BookCode, Member [] me, Book[] bo, int f, int LEn ) {
		int m = Book.searchInfo(BookCode, f, bo);
		int z = search(ID, LEn, me);
		if(m == -1||z == -1) {//It takes the ID and book code and in the main array of books and members, and if we do not have a member with this ID or a book with this bookcode, one of these two items becomes -1	 
			System.out.println("Sorry!!!But The entered bookcode or ID is incorrect!!!");
		}

		else if
		(bo[m].getGenres().equals("Precious")) {
			System.out.println("We're really sorry, but you can not borrow this book because it is one of the exquisite books!!!");

		}


		else if
		(bo[m].getGenres().equals("Teenager")) {
			System.out.println("We're really sorry, but you can not borrow this book because it is Because it is one of the books for children and teenagers, so please go to the menu and select the appropriate option!!!");

		}
		else {//If there is, it should check if the book is available and not lent
			if(bo[m].isExistence()) {	
				if(AddBorrowed(me[z], bo[m])) {	
					bo[m].setExistence(false);
					System.out.println("You have successfully borrowed your book^^!!!");
				}


			}
			else {
				System.out.println("Sorry, but this book is not currently available and has been borrowed");

			}	}}

	static void Restoration(long ID, String BookCode, Member [] me, Book[] bo, int f, int LEn) {
		int m = Book.searchInfo(BookCode, f, bo);
		int z = search(ID, LEn, me);
		if(m == -1||z == -1) {	 
			System.out.println("Sorry!!!But The entered bookcode or ID is incorrect!!!");
		}
		else {			
			if(DeleteReturned(me[z], bo[m])) {	
				bo[z].setExistence(true);
				System.out.println("hey!!You have successfully returned the book!!");
			}


		}	
	}

	/**
	 * 
	 * @param ID
	 * @param me
	 * @param bo
	 * @param f
	 * @param LEn
	 */



	static void ToLendTeen(long ID,  Member [] me, Book[] bo, int f, int LEn ) {
		Scanner scanner = new Scanner (System.in);
		int z = search(ID, LEn, me);
		if(z == -1) {//It takes the ID and book code and in the main array of books and members, and if we do not have a member with this ID or a book with this bookcode, one of these two items becomes -1	 
			System.out.println("Sorry!!!But The entered ID is incorrect!!!");
		}

		else{
			int g = me[z].getAge();
			if (g>15) {//This section is for grading the working age and uses if
				System.out.println("We are very sorry!!!! but this book is not for your age^^!!!");
			}
			char k = 0;

			if(g<6) {
				k='a';
			}

			if(g==6 || g==7) {
				k='b';
			}

			if(g==8 || g==9) {
				k='c';
			}
			if(g==10 || g==11) {
				k='d';
			}

			if(g>=12 && g<=15) {
				k='e';	
			}

			for (int i = 0; i<f; i++) {//When she wants to borrow a teenager's book, she finds and publishes books that are the same age as the user

				if(bo[i].getGenres().equals("Teenager") && k==((Teenager)bo[i]).getAgeGroup())//It prints the i to know which array the user belongs to, then prints its information
				{

					System.out.println(i);
					bo[i].ShowBookInfo();
				}

			}
			System.out.println("Please select one of the above*^*");
			int p = scanner.nextInt();
			if(bo[p].isExistence()) {//Selects one and enters the i and if there is a book and it is added to the array of member books without any problem
				if(AddBorrowed(me[z], bo[p]))

				{bo[p].setExistence(false);//It falsifies the existence and informs you that you have borrowed this book
				System.out.println("You have successfully borrowed your book^^!!!");
				}
			}
			else {
				System.out.println("Sorry, but this book is not currently available and has been borrowed");//And if it does not exist, it is as if the book has already been borrowed, so it publishes this for the user

			}}}}



