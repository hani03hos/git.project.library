

import java.util.Scanner;


/**
 * This class represents a Precious book. A subclass of the book class 
 * @author Hanieh Hosseinkhani

 */

//Build a subclass named precious for book using the word extends
public class Precious extends Book{
	private String Kind; //Defining the characteristics of an exquisite book type and its privatization


	/**
	 * Create a setter and getter for a new property that has an exquisite book
	 * @param Kind
	 */
	public void setKind(String Kind) {this.Kind = Kind;}//Create a setter and getter for a new property that has an exquisite book
	public String getKind() {return Kind;

	}



	/**Use the superclass constructor and add exquisite book properties using the word super
	 * 
	 * @param name
	 * @param author
	 * @param translator
	 * @param publisher
	 * @param year
	 * @param kind
	 */
	public Precious( String name, String author, String translator, String publisher, int year, String kind) {
		super(name,author,translator,publisher,year, "Precious" );
		this.setKind(Kind);

	}
	/**Use the information display method in the superclass and override*/
	/**
	 * @override
	 */
	public void ShowBookInfo() {//Use the information display method in the superclass and override
		super.ShowBookInfo();
		System.out.println("Book Kind: "+this.getKind()); 

	}

	public void EditInfo(Book[]b1 , String e) {//Only the Kind feature has been added to change the type
		Scanner scanner = new Scanner (System.in);
		super.EditInfo(b1 , e);
		System.out.println("Hey^^ Now You Can Enter your correct bookkind^^!!!");
		this.setKind(scanner.next());

	}}

