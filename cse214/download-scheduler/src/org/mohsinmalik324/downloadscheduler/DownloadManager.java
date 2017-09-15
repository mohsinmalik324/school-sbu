package org.mohsinmalik324.downloadscheduler;

import java.util.Scanner;

/**
 * The main class.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 4
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class DownloadManager {
	
	private static Scanner scanner = null;
	
	/**
	 * Main method.
	 * 
	 * @param args
	 *    Arguments passed to program.
	 */
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		int servers = 0;
		int downloadSpeed = 0;
		int time = 0;
		double premiumProb = 0D;
		double regularProb = 0D;
		// Get input.
		while((servers = getIntInput("Number of servers: ")) <= 0) {
			println("Input must be positive. Try again.");
		}
		while((downloadSpeed = getIntInput("Download speed: ")) <= 0) {
			println("Input must be positive. Try again.");
		}
		while((time = getIntInput("Length of time: ")) < 0) {
			println("Input must be non-negative. Try again.");
		}
		premiumProb = getDoubleInput("Probability of new premium job per timestep: ");
		regularProb = getDoubleInput("Probability of new regular job per timestep: ");
		DownloadScheduler scheduler = new DownloadScheduler(time,
		  downloadSpeed, servers, premiumProb, regularProb);
		println(scheduler.simulate());
		// Close scanner.
		scanner.close();
	}
	
	/**
	 * Gets user String input.
	 * 
	 * @param prompt Message displayed to user.
	 * 
	 * @return User String input.
	 */
	public static String getStringInput(String prompt) {
		print(prompt);
		return scanner.nextLine();
	}
	
	/**
	 * Gets user int input.
	 * 
	 * @param prompt Message displayed to user.
	 * 
	 * @return User int input.
	 */
	public static int getIntInput(String prompt) {
		while(true) {
			try {
				return Integer.valueOf(getStringInput(prompt));
			} catch(NumberFormatException e) {
				println("Input must be an int. Try again.");
			}
		}
	}
	
	/**
	 * Gets user double input.
	 * 
	 * @param prompt Message displayed to user.
	 * 
	 * @return User double input.
	 */
	public static double getDoubleInput(String prompt) {
		while(true) {
			try {
				return Double.valueOf(getStringInput(prompt));
			} catch(NumberFormatException e) {
				println("Input must be a double. Try again.");
			}
		}
	}
	
	/**
	 * Prints a message to console with a new line
	 * character via standard output.
	 * 
	 * @param message
	 *    Message to print.
	 */
	public static void println(String message) {
		System.out.println(message);
	}
	
	/**
	 * Prints a message to console via standard output.
	 * 
	 * @param message
	 *    Message to print.
	 */
	public static void print(String message) {
		System.out.print(message);
	}
	
}