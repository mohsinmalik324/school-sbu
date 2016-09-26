package org.mohsinmalik324.tripplanner;

/**
 * <code>EndOfListException</code> is an exception thrown when the end of
 * the list is reached.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 2
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class EndOfListException extends Exception {

	/**
	 * Version ID of this exception.
	 */
	private static final long serialVersionUID = -4698170061324557847L;
	
	/**
	 * Returns an instance of <code>EndOfListException</code>. Contains
	 * a default message.
	 */
	public EndOfListException() {
		this("End of the list.");
	}
	
	/**
	 * Returns an instance of <code>EndOfListException</code>. Contains
	 * a custom message.
	 * 
	 * @param message
	 *    A custom message for the exception.
	 */
	public EndOfListException(String message) {
		super(message);
	}
	
}