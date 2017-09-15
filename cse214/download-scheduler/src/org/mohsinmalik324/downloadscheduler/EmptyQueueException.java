package org.mohsinmalik324.downloadscheduler;

/**
 * <code>EmptyQueueException</code> is an exception thrown when a queue is
 * empty.
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
public class EmptyQueueException extends Exception {
	
	/**
	 * Version ID of this exception.
	 */
	private static final long serialVersionUID = 2490824033179545387L;
	
	/**
	 * Returns an instance of <code>EmptyQueueException</code>. Contains
	 * a default message.
	 */
	public EmptyQueueException() {
		this("Queue is empty.");
	}
	
	/**
	 * Returns an instance of <code>EmptyQueueException</code>. Contains
	 * a custom message.
	 * 
	 * @param message
	 *    A custom message for the exception.
	 */
	public EmptyQueueException(String message) {
		super(message);
	}
	
}