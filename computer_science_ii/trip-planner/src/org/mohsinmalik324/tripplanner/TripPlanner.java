package org.mohsinmalik324.tripplanner;

import java.util.Scanner;

/**
 * <code>TripPlanner</code> implements the main method, which allows user
 * interaction with the program.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class TripPlanner {
	
	private static Scanner scanner = null;
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *     Arguments for the program.
	 */
	public static void main(String[] args) {
		println("Welcome to Trip Planner!\n");
		boolean exit = false;
		scanner = new Scanner(System.in);
		Itinerary tripOne = new Itinerary();
		Itinerary tripTwo = new Itinerary();
		Itinerary tripCurrent = tripOne; // The trip selected.
		// Loop until user exits program.
		while(!exit) {
			printMenu();
			// Get operation from user input.
			String operation = getStringInput("Operation: ");
			if(operation.equalsIgnoreCase("f")) {
				// Check if cursor is pointing to anything.
				if(tripCurrent.getCursorStop() != null) {
					try {
						tripCurrent.cursorForward();
						println("Cursor moved forward.\n");
					} catch (EndOfItineraryException e) {
						println(e.getMessage() + "\n");
					}
				} else {
					println("Cursor is not pointing to anything.\n");
				}
			} else if(operation.equalsIgnoreCase("b")) {
				// Check if cursor is pointing to anything.
				if(tripCurrent.getCursorStop() != null) {
					try {
						tripCurrent.cursorBackward();
						println("Cursor moved back.\n");
					} catch (EndOfItineraryException e) {
						println(e.getMessage() + "\n");
					}
				} else {
					println("Cursor is not pointing to anything.\n");
				}
			} else if(operation.equalsIgnoreCase("i")) {
				TripStop newStop = createNewStop();
				tripCurrent.insertBeforeCursor(newStop);
				println("Added.\n");
			} else if(operation.equalsIgnoreCase("a")) {
				TripStop newStop = createNewStop();
				tripCurrent.appendToTail(newStop);
				println("Added.\n");
			} else if(operation.equalsIgnoreCase("d")) {
				try {
					tripCurrent.removeCursor();
					tripCurrent.cursorForward();
				} catch (EndOfListException e) {
					println(e.getMessage());
				} catch (EndOfItineraryException e) {
					println(e.getMessage());
				}
				println("Deleted cursor.\n");
			} else if(operation.equalsIgnoreCase("h")) {
				tripCurrent.resetCursorToHead();
				println("Cursor moved to head.\n");
			} else if(operation.equalsIgnoreCase("t")) {
				if(tripCurrent.getCursorStop() != null) {
					while(true) {
						try {
							tripCurrent.cursorForward();
						} catch (EndOfItineraryException e) {
							break;
						}
					}
					println("Cursor moved to tail.\n");
				} else {
					println("Cursor is not pointing to anything.");
				}
			} else if(operation.equalsIgnoreCase("e")) {
				TripStop cursorStop = tripCurrent.getCursorStop();
				if(cursorStop == null) {
					println("Cursor is not pointing to anything.");
				} else {
					String location = getStringInput("Edit Location, or press"
					  + " enter without typing anything to keep: ");
					String activity = getStringInput("Edit Activity, or press"
					  + " enter without typing anything to keep: ");
					int distance = getDistanceInput("Edit Distance, or press"
					  + " -1 without typing anything to keep: ", true);
					if(!location.equals("")) {
						cursorStop.setLocation(location);
					}
					if(!activity.equals("")) {
						cursorStop.setActivity(activity);
					}
					if(distance != -1) {
						cursorStop.setLocation(location);
					}
					println("Edits applied.\n");
				}
			} else if(operation.equalsIgnoreCase("s")) {
				if(tripCurrent == tripOne) {
					tripCurrent = tripTwo;
				} else {
					tripCurrent = tripOne;
				}
				println("Itinerary switched.\n");
			} else if(operation.equalsIgnoreCase("o")) {
				TripStop cursorStop = null;
				if(tripCurrent == tripOne) {
					cursorStop = tripTwo.getCursorStop();
				} else {
					cursorStop = tripOne.getCursorStop();
				}
				if(cursorStop == null) {
					println("Cursor of other itinerary is not pointing to "
					  + "anything.\n");
				} else {
					TripStop clonedStop = (TripStop) cursorStop.clone();
					tripCurrent.insertBeforeCursor(clonedStop);
					println("Cloned cursor from other itinerary and "
					  + "inserted before cursor.\n");
				}
			} else if(operation.equalsIgnoreCase("r")) {
				if(tripOne == tripCurrent) {
					tripOne = (Itinerary) tripTwo.clone();
					tripCurrent = tripOne;
				} else {
					tripTwo = (Itinerary) tripOne.clone();
					tripCurrent = tripTwo;
				}
				println("Other itinerary cloned and set "
				  + "as current itinerary.\n");
			} else if(operation.equalsIgnoreCase("p")) {
				if(tripCurrent.getCursorStop() == null) {
					println("Nothing to print.\n");
				} else {
					println(tripCurrent.toString());
				}
			} else if(operation.equalsIgnoreCase("c")) {
				tripCurrent.resetCursorToHead();
				while(true) {
					try {
						tripCurrent.removeCursor();
					} catch (EndOfListException e) {
						break;
					}
				}
				println("Itinerary cleared.\n");
			} else if(operation.equalsIgnoreCase("q")) {
				exit = true;
			} else {
				println("Unknown operation \"" + operation + "\"\n");
			}
		}
		scanner.close();
	}
	
	/**
	 * Takes in user input as a String with a prompt.
	 * 
	 * @param prompt
	 *    The prompt to be displayed to the user.
	 * 
	 * @return
	 *    User input as a String.
	 */
	private static String getStringInput(String prompt) {
		print(prompt);
		return getStringInput();
	}
	
	/**
	 * Takes in user input as a String.
	 * 
	 * @return
	 *    User input as a String.
	 */
	private static String getStringInput() {
		return scanner.nextLine();
	}
	
	/**
	 * Takes in user input as an int with a prompt.
	 * 
	 * @param prompt
	 *    The prompt to be displayed to the user.
	 * 
	 * @return
	 *    User input as an int.
	 * 
	 * <dt>Precondition:
	 *    <dd>User input must be an int.
	 * 
	 * @throws InvalidInputTypeException
	 *     User input was not an int.
	 */
	private static int getIntInput(String prompt) throws InvalidInputTypeException {
		print(prompt);
		return getIntInput();
	}
	
	/**
	 * Takes in user input as an int.
	 * 
	 * @return
	 *    User input as an int.
	 * 
	 * <dt>Precondition:
	 *    <dd>User input must be an int.
	 * 
	 * @throws InvalidInputTypeException
	 *     User input was not an int.
	 */
	private static int getIntInput() throws InvalidInputTypeException {
		try {
			// Get user input as a String then convert to int.
			int intInput = Integer.valueOf(getStringInput());
			return intInput;
		} catch(NumberFormatException e) {
			throw new InvalidInputTypeException("Input "
			  + "type must be an integer.");
		}
	}
	
	/**
	 * Takes in user input as a double.
	 * 
	 * @return
	 *    User input as a double.
	 * 
	 * <dt>Precondition:
	 *    <dd>User input must be a double.
	 * 
	 * @throws InvalidInputTypeException
	 *     User input was not a double.
	 */
	@SuppressWarnings("unused")
	private static double getDoubleInput() throws InvalidInputTypeException {
		try {
			// Get user input as a String then convert to double.
			double doubleInput = Double.valueOf(getStringInput());
			return doubleInput;
		} catch(NumberFormatException e) {
			throw new InvalidInputTypeException("Input "
			  + "type must be a double.");
		}
	}
	
	/**
	 * Prints a message to console with a new line
	 * character via standard output.
	 * 
	 * @param message
	 *    Message to print.
	 */
	private static void println(String message) {
		System.out.println(message);
	}
	
	/**
	 * Prints a message to console via standard output.
	 * 
	 * @param message
	 *    Message to print.
	 */
	private static void print(String message) {
		System.out.print(message);
	}
	
	/**
	 * Prints the menu.
	 */
	private static void printMenu() {
		println("Menu:");
		println("F-Cursor forward");
		println("B-Cursor backward");
		println("I-Insert before cursor");
		println("A-Append to tail");
		println("D-Delete and move cursor forward");
		println("H-Cursor to Head");
		println("T-Cursor to Tail");
		println("E-Edit cursor");
		println("S-Switch itinerary");
		println("O-Insert cursor from other itinerary "
		  + "after cursor from this itinerary");
		println("R-Replace this itinerary with a copy "
		  + "of the other itinerary");
		println("P-Print current itinerary, including summary");
		println("C-Clear current itinerary");
		print("Q-Quit\n\n");
	}
	
	/**
	 * Returns a distance provided by the user.
	 * 
	 * @param prompt
	 *    The prompt to be displayed to the user.
	 * 
	 * @param allowNegativeOne
	 *    Allow the distance to equal negative one (for editing trip stops).
	 * @return
	 *    The distance provided by the user.
	 */
	private static int getDistanceInput(String prompt,
	  boolean allowNegativeOne) {
		int distance = 0;
		// Keep looping until proper distance input is provided.
		while(true) {
			try {
				distance = getIntInput(prompt);
				if((distance == -1 && allowNegativeOne) || distance >= 0) {
					break;
				} else {
					if(allowNegativeOne) {
						println("distance must be >= 0 or equal to -1."
						  + " Try again.");
					} else {
						println("distance must be >= 0. Try again.");
					}
				}
			} catch (InvalidInputTypeException e) {
				println("distance must be an int. Try again.");
			}
		}
		return distance;
	}
	
	/**
	 * Creates a new trip stop based on user input.
	 * 
	 * @return
	 *    The new trip stop.
	 */
	private static TripStop createNewStop() {
		String location = getStringInput("Enter location: ");
		String activity = getStringInput("Enter activity: ");
		int distance = getDistanceInput("Enter distance: ", false);
		// Create new trip stop.
		TripStop newStop = new TripStop(location, activity, distance);
		return newStop;
	}
	
}