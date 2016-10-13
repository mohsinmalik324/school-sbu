package org.mohsinmalik324.downloadscheduler;

/**
 * <code>InvalidInputTypeException</code> is an exception thrown when user
 * input does not match the type desired.
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
public class InvalidInputTypeException extends Exception {

	/**
	 * Version ID of this exception.
	 */
	private static final long serialVersionUID = 2490824033179545387L;
	
	/**
	 * Returns an instance of <code>InvalidInputTypeException</code>. Contains
	 * a default message.
	 */
	public InvalidInputTypeException() {
		this("Invalid input type.");
	}
	
	/**
	 * Returns an instance of <code>InvalidInputTypeException</code>. Contains
	 * a custom message.
	 * 
	 * @param message
	 *    A custom message for the exception.
	 */
	public InvalidInputTypeException(String message) {
		super(message);
	}
	
}