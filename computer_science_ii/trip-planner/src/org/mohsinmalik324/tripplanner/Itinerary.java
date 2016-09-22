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
	private int stops = 0;
	private int distance = 0;
	
	/**
	 * Returns an instance of <code>Itinerary</code>.
	 */
	public Itinerary() {
		head = null;
		tail = null;
		cursor = null;
		stops = 0;
		distance = 0;
	}
	
	/**
	 * Returns the total number of stops.
	 * 
	 * @return
	 *    The total number of stops.
	 */
	public int getStopsCount() {
		return stops;
	}
	
	/**
	 * Returns the total distance of the trip.
	 * 
	 * @return
	 *    The total distance of the trip.
	 */
	public int getTotalDistance() {
		return distance;
	}
	
	/**
	 * Returns the <code>TripStop</code> that the cursor node is wrapping.
	 * 
	 * @return
	 *    The <code>TripStop</code> that the cursor node is wrapping.
	 *    If <code>cursor</code> is null, this returns null.
	 */
	public TripStop getCursorStop() {
		if(cursor == null) {
			return null;
		} else {
			return cursor.getData();
		}
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
		if(newStop == null) {
			throw new IllegalArgumentException("The new stop is null.");
		}
		TripStopNode newNode = new TripStopNode(newStop);
		if(cursor == null) {
			head = newNode;
			tail = newNode;
		} else {
			newNode.setPrevious(cursor.getPrevious());
			newNode.setNext(cursor);
			if(newNode.getPrevious() != null) {
				newNode.getPrevious().setNext(newNode);
			} else {
				head = newNode;
			}
		}
		cursor = newNode;
		stops++;
		distance += newNode.getData().getDistance();
	}
	
	/**
	 * Wraps a <code>TripStop</code> object in a node and inserts said node
	 * at the end of the list.
	 * 
	 * @param newStop
	 *    The new stop to be wrapped and appended into the list.
	 * 
	 * <dt>Precondition:
	 *    <dd><code>newStop</code> is not null.
	 * 
	 * <dt>Postconditions:
	 *    <dd>The new stop has been wrapped in a node.
	 *    <dd>If the tail was not null, the node was inserted after the
	 *    tail.
	 *    <dd>If the tail was null, the node is now the beginning of the
	 *    list (as is the tail).
	 *    <dd>The tail now points to the new node.
	 * 
	 * @throws IllegalArgumentException
	 *    <code>newStop</code> is null.
	 */
	public void appendToTail(TripStop newStop) throws IllegalArgumentException {
		if(newStop == null) {
			throw new IllegalArgumentException("The new stop is null.");
		}
		TripStopNode newNode = new TripStopNode(newStop);
		if(tail == null) {
			head = newNode;
			tail = newNode;
			cursor = newNode;
		} else {
			newNode.setNext(null);
			newNode.setPrevious(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
		stops++;
		distance += newNode.getData().getDistance();
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
		if(cursor == null) {
			throw new EndOfListException();
		}
		TripStop toReturn = cursor.getData();
		if(head == cursor && tail == cursor) {
			head = null;
			tail = null;
			cursor = null;
		} else if(head == cursor) {
			TripStopNode newHead = cursor.getNext();
			newHead.setPrevious(null);
			head = newHead;
			cursor = newHead;
		} else if(tail == cursor) {
			TripStopNode newTail = cursor.getPrevious();
			newTail.setNext(null);
			tail = newTail;
			cursor = newTail;
		} else {
			TripStopNode prev = cursor.getPrevious();
			TripStopNode next = cursor.getNext();
			prev.setNext(next);
			next.setPrevious(prev);
			cursor = prev;
		}
		stops--;
		distance -= toReturn.getDistance();
		return toReturn;
	}
	
	/**
	 * Returns a table of the nodes in this Itinerary.
	 * 
	 * @return
	 *    A table of the nodes in this Itinerary.
	 */
	public String toString() {
		TripStopNode tmp = head;
		String toString = "";
		while(tmp != null) {
			String prefix = " ";
			if(cursor == tmp) {
				prefix = ">";
			}
			toString += prefix + tmp.getData().toString();
			tmp = tmp.getNext();
		}
		return toString;
	}
	
	/**
	 * Returns a deep clone of this itinerary object.
	 * 
	 * @return
	 *    A deep clone of this itinerary object.
	 */
	public Object clone() {
		Itinerary cloneTrip = new Itinerary();
		if(cursor != null) {
			TripStopNode tmp = head;
			// Loop through list and append copied node into new trip.
			while(tmp != null) {
				cloneTrip.appendToTail((TripStop) tmp.getData().clone());
				if(tmp == cursor) {
					cloneTrip.cursor = cloneTrip.tail;
				}
				tmp = tmp.getNext();
			}
		}
		return cloneTrip;
	}
	
}