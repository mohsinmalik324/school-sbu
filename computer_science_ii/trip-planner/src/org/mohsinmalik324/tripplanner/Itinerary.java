package org.mohsinmalik324.tripplanner;

/**
 * <code>Itinerary</code> 
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class Itinerary {
	
	private TripStopNode head;
	private TripStopNode tail;
	private TripStopNode cursor;
	
	/**
	 * 
	 */
	public Itinerary() {
		head = null;
		tail = null;
		cursor = null;
	}
	
	/**
	 * Sets a new head.
	 * 
	 * @param head
	 *    The new head.
	 */
	public void setHead(TripStopNode head) {
		this.head = head;
	}
	
	/**
	 * Sets a new tail.
	 * 
	 * @param tail
	 *    The new tail.
	 */
	public void setTail(TripStopNode tail) {
		this.tail = tail;
	}

	/**
	 * Sets a new cursor.
	 * 
	 * @param cursor
	 *    The new cursor.
	 */
	public void setCursor(TripStopNode cursor) {
		this.cursor = cursor;
	}
	
	/**
	 * Returns the head.
	 * 
	 * @return
	 *    The head.
	 */
	public TripStopNode getHead() {
		return head;
	}
	
	/**
	 * Returns the tail.
	 * 
	 * @return
	 *    The tail.
	 */
	public TripStopNode getTail() {
		return tail;
	}
	
	/**
	 * Returns the cursor.
	 * 
	 * @return
	 *    The cursor.
	 */
	public TripStopNode getCursor() {
		return cursor;
	}
	
}