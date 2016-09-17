package org.mohsinmalik324.tripplanner;

/**
 * <code>TripStopNode</code> is a wrapper for a TripStop, allowing the
 * integration of linked-lists.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class TripStopNode {
	
	private TripStop data;
	private TripStop next;
	private TripStop prev;
	
	/**
	 * Returns an instance of <code>TripStopNode</code>.
	 * 
	 * @param initData
	 *    The data to be wrapped in this node.
	 * 
	 * <dt>Precondition:
	 *    <dd><code>initData</code> should not be null.
	 * 
	 * <dt>Postcondition:
	 *    <dd>This node has been initialized to wrap around
	 *    <code>initData</code>. <code>next</code> and <code>prev</code>
	 *    are null.
	 * 
	 * @throws IllegalArgumentException
	 *    <code>initData</code> was null.
	 */
	public TripStopNode(TripStop initData) throws IllegalArgumentException {
		setData(initData);
		setNext(null);
		setPrevious(null);
	}
	
	/**
	 * Returns the data this node is wrapping.
	 * 
	 * @return
	 *    The data this node is wrapping.
	 */
	public TripStop getData() {
		return data;
	}
	
	/**
	 * Sets the data to be wrapped by this node.
	 * 
	 * @param newData
	 *    The new data.
	 * 
	 * <dt>Precondition:
	 *    <dd><code>newData</code> is not null.
	 * 
	 * @throws IllegalArgumentException
	 *    <code>newData</code> is null.
	 */
	public void setData(TripStop newData) throws IllegalArgumentException {
		if(newData == null) {
			throw new IllegalArgumentException("The new data can't be null.");
		}
		data = newData;
	}
	
	/**
	 * Returns the next node.
	 * 
	 * @return
	 *    The next node.
	 */
	public TripStop getNext() {
		return next;
	}
	
	/**
	 * Sets the next node.
	 * 
	 * @param newNext
	 *    The new next node.
	 */
	public void setNext(TripStop newNext) {
		next = newNext;
	}
	
	/**
	 * Returns the previous node.
	 * 
	 * @return
	 *    The previous node.
	 */
	public TripStop getPrevious() {
		return prev;
	}
	
	/**
	 * Sets the Previous node.
	 * 
	 * @param newPrev
	 *    The new previous node.
	 */
	public void setPrevious(TripStop newPrev) {
		prev = newPrev;
	}
	
}