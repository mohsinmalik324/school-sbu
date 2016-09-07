package org.mohsinmalik324.cse214hw1;

import java.util.Scanner;

/**
 * The <code>MenuOperations</code> class implements the main method which
 * allows user interaction with this program.
 * 
 * @author Mohsin Malik
 *    <dd>email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class MenuOperations {
	
	public static void main(String[] args) {
		boolean exit = false;
		
		println("Main menu:");
		println("");
		println("A) Add Item");
		println("G) Get Item");
		println("R) Remove Item");
		println("P) Print All Items");
		println("S) Size");
		println("D) Update description");
		println("C) Update price");
		println("O) Add to order");
		println("I) Remove from order");
		println("V) View order");
		println("Q) Quit");
		println("");
		
		Scanner scanner = new Scanner(System.in);
		
		while(!exit) {
			print("Select an operation: ");
			String operation = scanner.nextLine();
			if(operation.equalsIgnoreCase("a")) {
				
			} else if(operation.equalsIgnoreCase("g")) {
				
			} else if(operation.equalsIgnoreCase("r")) {
				
			} else if(operation.equalsIgnoreCase("p")) {
				
			} else if(operation.equalsIgnoreCase("s")) {
				
			} else if(operation.equalsIgnoreCase("d")) {
				
			} else if(operation.equalsIgnoreCase("c")) {
				
			} else if(operation.equalsIgnoreCase("o")) {
				
			} else if(operation.equalsIgnoreCase("i")) {
				
			} else if(operation.equalsIgnoreCase("v")) {
				
			} else if(operation.equalsIgnoreCase("q")) {
				exit = true;
			}
		}
		
		scanner.close();
		
		println("Program exiting...");
		
		/*System.out.printf("%-5s%-20s%-50s%8s\n\n", "#", "Name", "Description", "Price");
		
		int position = 1;
		
		MenuItem menuItem = new MenuItem("Beef", "This yummy shit", 1.99);
		System.out.printf("%-5d%-20s%-50s%8.2f\n", position++, menuItem.getName(),
				  menuItem.getDescription(), menuItem.getPrice());
		
		menuItem = new MenuItem("Cum", "The most delicious substance", 69.99);
		System.out.printf("%-5d%-20s%-50s%8.2f", position++, menuItem.getName(),
				  menuItem.getDescription(), menuItem.getPrice());*/
	}
	
	public static void print(String text) {
		System.out.print(text);
	}
	
	public static void println(String text) {
		System.out.println(text);
	}
	
}