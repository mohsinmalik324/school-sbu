package org.mohsinmalik324.decisiontreeclassifier;

import java.util.Scanner;

/**
 * The main driver class.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 5
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class DecisionTreeClassifier {
	
	private static Scanner scanner = null;
	
	/**
	 * The main class.
	 * 
	 * @param args Arguments for the program.
	 */
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		
		boolean mainMenu = true;
		boolean quit = false;
		TreeNavigator tree = new TreeNavigator();
		
		while(!quit) {
			printMenu(mainMenu);
			String operator = getInput("Select an option: ");
			if(mainMenu) {
				switch(operator.toUpperCase()) {
					case "I":
						while(true) {
							String fileName = getInput("Input file name (type"
							  + " -1 to escape): ");
							
							if(fileName.equals("-1")) {
								break;
							}
							try {
								tree = TreeNavigator.buildTree(fileName);
								break;
							} catch(IllegalArgumentException e) {
								println(e.getMessage() + " Try again.");
							}
						}
						break;
					case "E":
						mainMenu = false;
						tree.resetCursor();
						break;
					case "C":
						String text = getInput("Enter some text: ");
						println("Your request is classified as: " + tree.classify(text));
						break;
					case "P":
						text = getInput("Enter some text: ");
						tree.classify(text);
						println("Decision path: " + tree.getPath());
						break;
					case "Q":
						quit = true;
						break;
				}
			} else {
				
			}
		}
		
		scanner.close();
	}
	
	/**
	 * Prints a menu.
	 * 
	 * @param mainMenu If the main menu should be printed.
	 */
	private static void printMenu(boolean mainMenu) {
		if(mainMenu) {
			println("Main Menu:");
			println("\tI) Import a tree from file");
			println("\tE) Edit current tree");
			println("\tC) Classify a description");
			println("\tP) Show decision path for a description");
			println("\tQ) Quit");
		} else {
			println("Edit Menu:");
			println("\tE) Edit keywords");
			println("\tC) Add children");
			println("\tD) Delete children and make leaf");
			println("\tN) Cursor to no child");
			println("\tY) Cursor to yes child");
			println("\tR) Cursor to root");
			println("\tP) Cursor to parent root");
			println("\tM) Main Menu");
		}
	}
	
	/**
	 * Returns user input as a String.
	 * 
	 * @param prompt Text displayed to the user.
	 * 
	 * @return User input as a String.
	 */
	public static String getInput(String prompt) {
		print(prompt);
		return scanner.nextLine();
	}
	
	/**
	 * Prints the text to stdout with a new line character.
	 * 
	 * @param message The message to print.
	 */
	public static void println(String message) {
		System.out.println(message);
	}
	
	/**
	 * Prints the message to stdout.
	 * 
	 * @param message The message to print.
	 */
	public static void print(String message) {
		System.out.print(message);
	}
	
}