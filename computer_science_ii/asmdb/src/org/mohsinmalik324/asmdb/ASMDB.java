package org.mohsinmalik324.asmdb;

import java.util.List;
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
	private static enum MovieSort {TA, TD, YA, YD};
	private static enum ActorSort {AA, AD, NA, ND};
	
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
					try {
						addTitle(title);
					} catch(IllegalArgumentException e) {
						println(e.getMessage());
					}
					break;
				}
				// Delete.
				case "d": {
					String title = getInput("Enter a movie title: ");
					try {
						deleteTitle(title);
					} catch(IllegalArgumentException e) {
						println(e.getMessage());
					}
					break;
				}
				// Sort movies.
				case "s": {
					printSortMovieMenu();
					String sort = getInput("Select a sort method: ");
					MovieSort movieSort = null;
					try {
						movieSort = MovieSort.valueOf(sort.toUpperCase());
					} catch(IllegalArgumentException e) {
						println("'" + sort +
						  "' is not a valid movie sort method.");
						break;
					}
					sortMovies(movieSort);
					break;
				}
				// Sort actors.
				case "a": {
					printSortActorMenu();
					String sort = getInput("Select a sort method: ");
					ActorSort actorSort = null;
					try {
						actorSort = ActorSort.valueOf(sort.toUpperCase());
					} catch(IllegalArgumentException e) {
						println("'" + sort +
						  "' is not a valid actor sort method.");
						break;
					}
					sortActors(actorSort);
					break;
				}
				case "q": {
					run = false;
					break;
				}
				case "t": {
					println("Movies");
					for(Movie movie : movieManager.getMovies()) {
						println("\t" + movie.getTitle() + " - " + movie.getYear());
						for(Actor actor : movie.getActors()) {
							println("\t\t" + actor.getName());
						}
					}
					println("Actors");
					for(Actor actor : movieManager.getActors()) {
						println("\t" + actor.getName() + " - " + actor.getCount());
					}
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
	private static void printMainMenu() {
		println("Main Menu:");
		println("\tI: Import Movie <Title>");
		println("\tD: Delete Movie <Title>");
		println("\tS: Sort Movies");
		println("\tA: Sort Actors");
		println("\tQ: Quit");
	}
	
	private static void printSortMovieMenu() {
		println("Movie Sorting Options:");
		println("\tTA: Title Ascending (A-Z)");
		println("\tTD: Title Descending (Z-A)");
		println("\tYA: Year Ascending");
		println("\tYD: Year Descending");
	}
	
	private static void printSortActorMenu() {
		println("Actor Sorting Options:");
		println("\tAA: Alphabetically Ascending");
		println("\tAD: Alphabetically Descending");
		println("\tNA: By Number of Movies They Are In Ascending");
		println("\tND: By Number of Movies They Are In");
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
		String urlTitle = title.replace(" ", "+");
		String prefix = "http://www.omdbapi.com/?t=";
		String postfix = "&y=&plot=short&r=xml";
		String url = prefix + urlTitle + postfix;
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
		println("'" + rTitle + "' added to your library.");
	}
	
	private static void deleteTitle(String title) {
		if(title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		String urlTitle = title.replace(" ", "+");
		String prefix = "http://www.omdbapi.com/?t=";
		String postfix = "&y=&plot=short&r=xml";
		String url = prefix + urlTitle + postfix;
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
		for(Actor actor : movie.getActors()) {
			actor.setCount(actor.getCount() - 1);
			if(actor.getCount() <= 0) {
				movieManager.getActors().remove(actor);
			}
		}
		movieManager.getMovies().remove(movie);
		println("'" + rTitle + "' removed from your library.");
	}
	
	private static void sortMovies(MovieSort movieSort) {
		List<Movie> movies = null;
		if(movieSort == MovieSort.TA || movieSort == MovieSort.TD) {
			movies = movieManager.getSortedMovies(new TitleComparator());
		} else if(movieSort == MovieSort.YA || movieSort == MovieSort.YD) {
			movies = movieManager.getSortedMovies(new YearComparator());
		}
		if(movies != null) {
			println("Title                           Year  Actors");
			println("------------------------------------------------"
			  + "-------------------------------------------");
			if(movieSort == MovieSort.TD || movieSort == MovieSort.YD) {
				for(int i = movies.size() - 1; i >= 0; i--) {
					Movie movie = movies.get(i);
					printMovieTab(movie);
				}
			} else {
				for(Movie movie : movies) {
					printMovieTab(movie);
				}
			}
		}
	}
	
	private static void printMovieTab(Movie movie) {
		String title = movie.getTitle();
		int year = movie.getYear();
		String actors = formatActorsString(movie.getActors());
		println(padMovieTitle(title) + " " + year + "  " + actors);
	}
	
	private static String padMovieTitle(String title) {
		if(title.length() >= 32) {
			title = title.substring(0, 28);
			title += "...";
		}
		while(title.length() != 31) {
			title += " ";
		}
		return title;
	}
	
	private static String formatActorsString(List<Actor> actors) {
		String actorsString = "";
		for(int i = 0; i < actors.size(); i++) {
			Actor actor = actors.get(i);
			if(i != actors.size() - 1) {
				actorsString += actor.getName() + ", ";
			} else {
				actorsString += actor.getName();
			}
		}
		if(actorsString.length() >= 54) {
			actorsString = actorsString.substring(0, 50);
			actorsString += "...";
		}
		return actorsString;
	}
	
	private static void sortActors(ActorSort actorSort) {
		List<Actor> actors = null;
		if(actorSort == ActorSort.AA || actorSort == ActorSort.AD) {
			actors = movieManager.getSortedActors(new NameComparator());
		} else if(actorSort == ActorSort.NA || actorSort == ActorSort.ND) {
			actors = movieManager.getSortedActors(new CountComparator());
		}
		if(actors != null) {
			println("Actor                        Number of Movies");
			println("------------------------------------------------"
			  + "-------------------------------------------");
			if(actorSort == ActorSort.AD || actorSort == ActorSort.ND) {
				for(int i = actors.size() - 1; i >= 0; i--) {
					Actor actor = actors.get(i);
					printActorTab(actor);
				}
			} else {
				for(Actor actor : actors) {
					printActorTab(actor);
				}
			}
		}
	}
	
	private static void printActorTab(Actor actor) {
		String name = actor.getName();
		println(padActorName(name) + " " + actor.getCount());
	}
	
	private static String padActorName(String name) {
		if(name.length() >= 29) {
			name = name.substring(0, 25);
			name += "...";
		}
		while(name.length() != 28) {
			name += " ";
		}
		return name;
	}
	
}