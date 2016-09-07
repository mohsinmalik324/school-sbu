package org.mohsinmalik324.cse214hw1;

/**
 * The <code>FullListException</code> class represents an exception where the
 * list of a <code>Menu</code> object is full.
 * 
 * @author Mohsin Malik
 *    <dd>email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class FullListException extends Exception {

	private static final long serialVersionUID = 5156219367199525400L;
	
	public FullListException() {
		
	}
	
	public FullListException(String message) {
		super(message);
	}
	
}