package org.mohsinmalik324.tripplanner;

/**
 * <code>EndOfItineraryException</code> is an exception thrown when the end of
 * the itinerary is reached.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class EndOfItineraryException extends Exception {
	
	/**
	 * Version ID of this exception.
	 */
	private static final long serialVersionUID = 4725787398167069317L;

	/**
	 * Returns an instance of <code>EndOfItineraryException</code>. Contains
	 * a default message.
	 */
	public EndOfItineraryException() {
		this("End of the itinerary.");
	}
	
	/**
	 * Returns an instance of <code>EndOfItineraryException</code>. Contains
	 * a custom message.
	 * 
	 * @param message
	 *    A custom message for the exception.
	 */
	public EndOfItineraryException(String message) {
		super(message);
	}
	
}