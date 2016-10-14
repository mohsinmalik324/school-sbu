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
		try {
			// Get input.
			int servers = getIntInput("Number of servers: ");
			int downloadSpeed = getIntInput("Download speed: ");
			int time = getIntInput("Length of time: ");
			double premiumProb = getDoubleInput("Probability of new premium"
			  + " job per timestep: ");
			double regularProb = getDoubleInput("Probability of new regular"
			  + " job per timestep: ");
			DownloadScheduler scheduler = new DownloadScheduler(time,
			  downloadSpeed, servers, premiumProb, regularProb);
			println(scheduler.simulate());
		} catch (InvalidInputTypeException e) {
			println(e.getMessage());
		}
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
	 * 
	 * <dt>Precondition:
	 *    <dd>User input must be an int.
	 * 
	 * @throws InvalidInputTypeException An int was not input.
	 */
	public static int getIntInput(String prompt)
	  throws InvalidInputTypeException {
		try {
			return Integer.valueOf(getStringInput(prompt));
		} catch(NumberFormatException e) {
			throw new InvalidInputTypeException("Input must be an integer.");
		}
	}
	
	/**
	 * Gets user double input.
	 * 
	 * @param prompt Message displayed to user.
	 * 
	 * @return User double input.
	 * 
	 * <dt>Precondition:
	 *    <dd>User input must be a double.
	 * 
	 * @throws InvalidInputTypeException A double was not input.
	 */
	public static double getDoubleInput(String prompt)
	  throws InvalidInputTypeException {
		try {
			return Double.valueOf(getStringInput(prompt));
		} catch(NumberFormatException e) {
			throw new InvalidInputTypeException("Input must be a double.");
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