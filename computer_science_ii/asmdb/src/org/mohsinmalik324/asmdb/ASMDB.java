package org.mohsinmalik324.asmdb;

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
 *    <dd>Assignment #: 7
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class ASMDB {
	
	private static Scanner scanner = null;
	
	/**
	 * Main method.
	 * @param args Arguments.
	 */
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		
		
		
		scanner.close();
	}
	
	/**
	 * Prints the main menu options.
	 */
	public static void printMainMenu() {
		println("Main Menu:");
		println("\tI: Import Movie <Title>");
		println("\tD: Delete Movie <Title>");
		println("\tS: Sort Movies");
		println("\tA: Sort Actors");
		// TODO
	}
	
	/**
	 * Gets user input.
	 * @param prompt The prompt displayed to the user.
	 * @return The user input.
	 */
	public static String getInput(String prompt) {
		return scanner.nextLine();
	}
	
	/**
	 * Prints a message to stdout with a new line charactor.
	 * @param message The message to print.
	 */
	public static void println(String message) {
		System.out.println(message);
	}
	
	/**
	 * Prints a message to stdout.
	 * @param message The message to print.
	 */
	public static void print(String message) {
		System.out.print(message);
	}
	
}