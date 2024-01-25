

import java.util.*;
import java.util.InputMismatchException;


/**
 * This class represents the main class. 
 * @author Hanieh Hosseinkhani
 */

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner (System.in);

		Member[] me = new Member[300] ;
		boolean end = true ;
		int counter = 0 ; 
		int LEn =0 ; 
		me[LEn] = new Member("hanieh" , (short)17 , 'm' , "912702816" , "admin" , "8282") ;
		me[LEn].setID(counter);
		counter++ ; 
		LEn++ ; 
		me[LEn] = new Member("mohammad" , (short)9 , 'M', "369852147" , "ordinary" , "7896") ;
		me[LEn].setID(counter);
		counter++ ;
		LEn++ ;
		me[LEn] = new Member("zahra" ,(short)40 , 'F' , "789654123" , "admin" , "7412" ) ;
		me[LEn].setID(counter);
		counter++ ;
		LEn++ ;

		Book[] b1 = new Book[300] ; 
		int counter2 = 1; 
		int l2 =0 ;
		long id = 0 ;
		while(true) {
			try {
				System.out.println("please enter your Id");
				id = scanner.nextLong();
				break;
			}catch(InputMismatchException e) {
				System.out.println("wrong!!");
			}finally {
				scanner.nextLine();
			}
		}
		System.out.println("please enter your password:");
		String s = scanner.next();
		if(Login(id , s , LEn , me)) {
			int k = Member.search(id, LEn, me);

			while(end) {

				System.out.println("Hey!!! Welcome to Books Management System!!!\n" 
						+ " MENU ^^ \n"
						+ " 1.Register a New User*-* \n"
						+ " 2.Search a User*-* \n"
						+ " 3.Show a User Information*-* \n"
						+ " 4.Edit a User Profile*-* \n"
						+ " 5.Delete a User*-*\n"
						+ " 6.Add new Book^^\n"
						+ " 7.Search Book^^\n"
						+ " 8.Show Book Info^^\n"
						+ " 9.Edit Book Info^^\n"
						+ " 10.Delete Book^^\n"
						+ " 11.Search for BookCode^^\n"
						+ "12.ToLend a Book^^\n"
						+ "13.Restoration of a Book^^\n"
						+ "14.The names of those who borrowed a book^^\n"
						+ "You Can Enter 0 to exit^^");



				int n;
				while(true) {
					try {
						System.out.println("Enter the number of the desired option from the menu*-*:");
						n = scanner.nextInt() ; 
						break;

					}catch(InputMismatchException e) {
						System.out.println("wrong!!!");

					}finally {
						scanner.nextInt() ; 
					}
				}

				switch(n){

				case 1:
					Member.case1( me , counter , LEn) ; 
					counter++ ; 
					LEn++ ;
					break ; 

				case 2:

					System.out.println(Member.search(id , LEn , me)) ;
					break ;



				case 3:


					Member.case3(me , id , LEn) ; 
					break ; 

				case 4:

					Member.case4(me , id , LEn ) ; 
					break ; 

				case 5:


					Member.case5(me , id , LEn) ; 
					LEn-- ; 
					break ;

				case 6:
					
					if(me[k].getUserType().equals("admin")) {

					Book.AddNewBooks(b1 , counter2, l2) ; 
					counter2++ ;
					l2 ++;
					}
					else {
						System.out.println("you can not add a new book!!");
					}
					break;



				case 7:
					System.out.println("Please Enter The Intended Code That You Want^^: ") ;
					String c = scanner.next() ;
					System.out.println(Book.searchInfo(c , l2 , b1)) ;
					break ;

				case 8:
					System.out.println("Please Enter The Intended Code That You Want^^: ") ;
					String c1 = scanner .next() ;
					int nam = Book.searchInfo(c1 ,l2 ,b1) ; 
					if(nam>=0) { 
						b1[nam].ShowBookInfo();
					}
					else {System.out.println("Sorry!!!, but it does not exist!!!");} 

					break ;	

				case 9:


					if(me[k].getUserType().equals("admin")) {
						System.out.println("Please Enter The Intended Code That You Want^^: ") ;
						String c2 = scanner .next() ;
						int nam1 = Book.searchInfo(c2 , l2 , b1)  ; 
						if(nam1>=0) { 
							b1[nam1].EditInfo(b1, c2);
						}
						else{
							System.out.println(nam1) ;
						}
					}
						else {
							System.out.println("you can not edit book information!");
						}
						break ;

						case 10:
							
							if(me[k].getUserType().equals("admin")) {
							System.out.println("Please Enter The Intended Code That You Want^^: ") ;
							String c3 = scanner .next() ;
							Book.deleteInfo(b1, c3, l2);
							l2 --;
							}
							else {
								System.out.println("you can not delete a book!!");
							}
							break ;

						case 11:
							System.out.println("Please Enter The Intended Code That You Want^^: ") ;
							String c4 = scanner .next() ;
							Book.Code( b1,c4,l2);
							break ;

						case 12:

							System.out.println("Please Now Enter The Intended Code That You Want^^: ") ;
							String c5 = scanner .next() ;
							Member.ToLend(id, c5, me, b1, l2, LEn);
							break;

						case 13:

							System.out.println("Please Now Enter The Intended Code That You Want^^: ") ;
							String c6 = scanner .next() ;
							Member.Restoration(id, c6, me, b1, l2, LEn);
							break;


						case 14:
							System.out.println("Please Now Enter The Intended Code That You Want^^: ") ;
							String c7 = scanner .next() ;
							int p = Book.searchInfo(c7, l2 , b1);
							b1[p].Printlist();
							break;


						case 0:
							end = false ;
							break ; 
						}
					}
				}
				scanner.close(); 

			}

			public static boolean Login(long ID , String Password, int LEn , Member[] me) {
				int i = Member.search(ID, LEn, me);
				if(i==-1) {
					System.out.println("the entered id is invalid!!!");
					return false;
				}
				else if(me[i].getPassword().equals(Password)==false ) {
					System.out.println("the password is wrong!!");
					return false;
				}
				else {
					System.out.println("Dear user, you have successfully logged in*-*");
					return true;






				}




			}





		}


