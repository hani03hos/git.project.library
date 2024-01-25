

import java.util.Scanner;

/**
 * This class represents a Teenager. A subclass of the book class for children and adolescents, called Teenager.The genre of books in this class is "Children and Adolescents" by default
 * @author Hanieh Hosseinkhani

 */

public class Teenager extends Book{//A subclass of the book class for children and adolescents, called Teenager, is formed by the keyword Extensions
	//The genre of books in this class is "Children and Adolescents" by default
	//Book age group property

	private char ageGroup;

	/**Create a setter and getter for a new property Book age group: (characters)*/

	public void setAgeGroup(char ageGroup) {this.ageGroup = ageGroup;}
	public char getAgeGroup() {return ageGroup;

	}


	/**Use the superclass constructor and use the word super to add new features and override
	 * @override
	 */

	public Teenager( String name, String author, String translator, String publisher, int year, char ageGroup) {
		super(name,author,translator,publisher,year, "Teenager" );
		this.ageGroup=ageGroup;

	}
	/**
	 * The age category is displayed next to all the book information
	 */

	public void ShowBookInfo() {//The age category is displayed next to all the book information
		super.ShowBookInfo();
		System.out.println("Book AgeGroup: "+this.getAgeGroup()); 

	}


	public void EditInfo(Book[]b1 , String e) {//Added to that age category
		Scanner scanner = new Scanner (System.in);
		super.EditInfo(b1 , e);
		System.out.println("Hey^^ Now You Can Enter your correct ageGroup^^!!!");
		this.setAgeGroup(scanner.next().charAt(0));

	}

}
