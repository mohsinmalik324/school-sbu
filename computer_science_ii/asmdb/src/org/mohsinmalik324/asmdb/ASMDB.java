package org.mohsinmalik324.asmdb;

import java.util.Scanner;

import big.data.DataSource;

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
	
	private static MovieManager movieManager = new MovieManager();
	private static Scanner scanner = null;
	
	/**
	 * Main method.
	 * @param args Arguments.
	 */
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		
		boolean run = true;
		
		while(run) {
			printMainMenu();
			String operation = getInput("Enter an option: ");
			switch(operation.toLowerCase()) {
				// Import.
				case "i": {
					String title = getInput("Enter a movie title: ");
					addTitle(title);
					break;
				}
				// Delete.
				case "d": {
					String title = getInput("Enter a movie title: ");
					// TODO
					break;
				}
				// Sort movies.
				case "s": {
					break;
				}
				// Sort actors.
				case "a": {
					break;
				}
				case "q": {
					run = false;
					break;
				}
				default: {
					println("Invalid operation. Try again.");
					break;
				}
			}
		}
		
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
		println("\tQ: Quit");
	}
	
	/**
	 * Gets user input.
	 * @param prompt The prompt displayed to the user.
	 * @return The user input.
	 */
	public static String getInput(String prompt) {
		print(prompt);
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
	
	private static void addTitle(String title) {
		if(title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		title = title.replace(" ", "+");
		String prefix = "http://www.omdbapi.com/?t=";
		String postfix = "&y=&plot=short&r=xml";
		String url = prefix + title + postfix;
		DataSource dataSource = DataSource.connectXML(url);
		dataSource.load();
		boolean response = dataSource.fetchBoolean("response");
		if(!response) {
			println("'" + title + "' not found in database.");
			return;
		}
		String rTitle = dataSource.fetchString("movie/title");
		Movie movie = movieManager.getMovie(rTitle);
		if(movie != null) {
			println("'" + rTitle + "' is already in your library.");
			return;
		}
		String actors = dataSource.fetchString("movie/actors");
		int rYear = dataSource.fetchInt("movie/year");
		movie = new Movie(rTitle, rYear);
		movieManager.getMovies().add(movie);
		for(String actorName : actors.split(", ")) {
			Actor actor = movieManager.getActor(actorName);
			if(actor == null) {
				actor = new Actor(actorName);
				movieManager.getActors().add(actor);
			}
			actor.setCount(actor.getCount() + 1);
			movie.getActors().add(actor);
		}
		println("'" + rTitle + "' added.");
	}
	
	public static void deleteTitle(String title) {
		if(title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		title = title.replace(" ", "+");
		String prefix = "http://www.omdbapi.com/?t=";
		String postfix = "&y=&plot=short&r=xml";
		String url = prefix + title + postfix;
		DataSource dataSource = DataSource.connectXML(url);
		dataSource.load();
		boolean response = dataSource.fetchBoolean("response");
		if(!response) {
			println("'" + title + "' not found in database.");
			return;
		}
		String rTitle = dataSource.fetchString("movie/title");
		Movie movie = movieManager.getMovie(rTitle);
		if(movie == null) {
			println("'" + rTitle + "' is not in your library.");
			return;
		}
		
	}
	
}