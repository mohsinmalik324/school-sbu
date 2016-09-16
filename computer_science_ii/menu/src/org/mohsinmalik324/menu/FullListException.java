package org.mohsinmalik324.menu;

/**
 * The <code>FullListException</code> class represents an exception where the
 * list of a <code>Menu</code> object is full.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class FullListException extends Exception {
	
	/**
	 * Version ID of this exception.
	 */
	private static final long serialVersionUID = 2556454590171773739L;

	/**
	 * Constructor which passes a default message.
	 * 
	 * <dt>Postcondition:
	 *    <dd>The object is created and contains the default message.
	 */
	public FullListException() {
		super("List is full.");
	}
	
	/**
	 * Constructor which passes the provided String.
	 * 
	 * @param message
	 *    The message the object will contain.
	 *    
	 * <dt>Postcondition:
	 *    <dd>The object is created and contains the provided message.
	 */
	public FullListException(String message) {
		super(message);
	}
	
}