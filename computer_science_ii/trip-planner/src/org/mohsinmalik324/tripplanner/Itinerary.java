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
	
}