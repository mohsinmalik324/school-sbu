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
	
	// TODO: Check if input is formatted correctly (ex: expecting double input, gets String input)
	public static void main(String[] args) {
		Menu menu = new Menu();
		
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
				print("Enter the name: ");
				String name = scanner.nextLine();
				print("Enter the description: ");
				String desc = scanner.nextLine();
				print("Enter the price: ");
				double price = scanner.nextDouble();
				print("Enter the position: ");
				int position = scanner.nextInt();
				MenuItem menuItem = new MenuItem(name, desc, price);
				try {
					menu.addItem(menuItem, position);
					println("Added \"" + name + ": " + desc + "\" for " +
					formatPrice(price) + " at position " + position);
				} catch (FullListException e) {
					e.printStackTrace();
				}
			} else if(operation.equalsIgnoreCase("g")) {
				print("Enter the position: ");
				int position = scanner.nextInt();
				try {
					MenuItem menuItem = menu.getItem(position);
					System.out.printf("%-5s%-20s%-50s%8s\n\n", "#", "Name",
					  "Description", "Price");
					System.out.printf("%-5d%-20s%-50s%8.2f\n", position,
					  menuItem.getName(), menuItem.getDescription(), menuItem.getPrice());
				} catch(IllegalArgumentException e) {
					e.printStackTrace();
				}
			} else if(operation.equalsIgnoreCase("r")) {
				
			} else if(operation.equalsIgnoreCase("p")) {
				menu.printAllItems();
			} else if(operation.equalsIgnoreCase("s")) {
				println("There are " + menu.size() + " item(s) in the menu");
			} else if(operation.equalsIgnoreCase("d")) {
				
			} else if(operation.equalsIgnoreCase("c")) {
				print("Enter the name of the item: ");
				String name = scanner.nextLine();
				print("Enter the new price: ");
				double price = scanner.nextDouble();
				try {
					MenuItem menuItem = menu.getItemByName(name);
					menuItem.setPrice(price);
					println("Changed the price of \"" + name + "\" to " + formatPrice(price));
				} catch(IllegalArgumentException e) {
					e.printStackTrace();
				}
			} else if(operation.equalsIgnoreCase("o")) {
				
			} else if(operation.equalsIgnoreCase("i")) {
				
			} else if(operation.equalsIgnoreCase("v")) {
				
			} else if(operation.equalsIgnoreCase("q")) {
				exit = true;
			}
		}
		
		scanner.close();
		
		println("Program exiting...");
	}
	
	public static String formatPrice(double price) {
		return String.format("$%.2f", price);
	}
	
	public static void print(String text) {
		System.out.print(text);
	}
	
	public static void println(String text) {
		System.out.println(text);
	}
	
	public static void errorln(String text) {
		System.err.println(text);
	}
	
}