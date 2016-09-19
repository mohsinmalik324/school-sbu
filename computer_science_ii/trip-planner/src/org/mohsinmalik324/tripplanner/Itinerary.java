package org.mohsinmalik324.tripplanner;

/**
 * <code>Itinerary</code> represents a doubly-linked list of
 * <code>TripStops</code>.
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
	 * Returns an instance of <code>Itinerary</code>.
	 */
	public Itinerary() {
		head = null;
		tail = null;
		cursor = null;
	}
	
	/**
	 * Returns the total number of stops.
	 * 
	 * @return
	 *    The total number of stops.
	 */
	public int getStopsCount() {
		return 0;
	}
	
	/**
	 * Returns the total distance of the trip.
	 * 
	 * @return
	 *    The total distance of the trip.
	 */
	public int getTotalDistance() {
		return 0;
	}
	
	/**
	 * Returns the <code>TripStop</code> that the cursor node is wrapping.
	 * 
	 * @return
	 *    The <code>TripStop</code> that the cursor node is wrapping.
	 */
	public TripStop getCursorStop() {
		return cursor.getData();
	}
	
	/**
	 * Sets the cursor to point to the start of the list.
	 * 
	 * <dt>Postconditions:
	 *    <dd>If <code>head</code> is not null, cursor now points to the start
	 *    of the list.
	 *    <dd>If <code>head</code> is null, cursor is now null and the list is
	 *    empty.
	 */
	public void resetCursorToHead() {
		cursor = head;
	}
	
	/**
	 * Moves the cursor to the next stop in the list.
	 * 
	 * @throws EndOfItineraryException 
	 *    The cursor is at the end of the list.
	 */
	public void cursorForward() throws EndOfItineraryException {
		if(cursor == tail) {
			throw new EndOfItineraryException();
		}
		cursor = cursor.getNext();
	}
	
	/**
	 * Moves the cursor to the previous stop in the list.
	 * 
	 * @throws EndOfItineraryException
	 *    The cursor is at the beginning of the list.
	 */
	public void cursorBackward() throws EndOfItineraryException {
		if(cursor == head) {
			throw new EndOfItineraryException();
		}
		cursor = cursor.getPrevious();
	}
	
	/**
	 * Wraps a <code>TripStop</code> object in a node and inserts said node
	 * before the cursor.
	 * 
	 * @param newStop
	 *    The new stop to be wrapped and inserted into the list.
	 *    
	 * <dt>Precondition:
	 *    <dd><code>newStop</code> is not null.
	 * 
	 * <dt>Postconditions:
	 *    <dd>The new stop has been wrapped in a node.
	 *    <dd>If the cursor was not null, the node was inserted before the
	 *    cursor.
	 *    <dd>If the cursor was null, the node is now the beginning of the
	 *    list (as is the tail).
	 *    <dd>The cursor now points to the new node.
	 * 
	 * @throws IllegalArgumentException
	 *    <code>newStop</code> is null.
	 */
	public void insertBeforeCursor(TripStop newStop) throws IllegalArgumentException {
		
	}
	
	/**
	 * Wraps a <code>TripStop</code> object in a node and inserts said node
	 * at the end of the list.
	 * 
	 * @param newStop
	 * 
	 * <dt>Precondition:
	 *    <dd><code>newStop</code> is not null.
	 * 
	 * <dt>Postconditions:
	 *    <dd>The new stop has been wrapped in a node.
	 *    <dd>If the tail was not null, the node was inserted after the
	 *    tail.
	 *    <dd>If the cursor was null, the node is now the beginning of the
	 *    list (as is the tail).
	 *    <dd>The tail now points to the new node.
	 * 
	 * @throws IllegalArgumentException
	 *    <code>newStop</code> is null.
	 */
	public void appendToTail(TripStop newStop) throws IllegalArgumentException {
		
	}
	
	/**
	 * Removes the node referenced by the cursor from the list and returns it.
	 * 
	 * @return
	 *    The removed node.
	 * 
	 * <dt>Precondition:
	 *    <dd>The cursor is not null.
	 * 
	 * <dt>Postconditions:
	 *    <dd>The node referenced by the cursor has been removed from the
	 *    list.
	 *    <dd>All other nodes in the list exist in the same order as before.
	 *    <dd>The cursor now references the previous node.
	 *    <dd>If the cursor was originally the head of the list, the cursor
	 *    will now point to the new head.
	 * 
	 * @throws EndOfListException
	 *    <code>cursor</code> is null.
	 */
	public TripStop removeCursor() throws EndOfListException {
		return null;
	}
	
}