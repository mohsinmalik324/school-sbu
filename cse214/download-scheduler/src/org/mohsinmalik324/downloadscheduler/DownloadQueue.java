package org.mohsinmalik324.downloadscheduler;

import java.util.LinkedList;

/**
 * The queue which keeps track of downloads.
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
public class DownloadQueue extends LinkedList<DownloadJob> {

	/**
	 * Version ID of this queue.
	 */
	private static final long serialVersionUID = 7003636788044224140L;
	
	/**
	 * Appends the item to the end of the list.
	 * 
	 * @param job The item to enqueue.
	 * 
	 * <dt>Postcondition:
	 *    <dd>The appended item is now the last item in the list.
	 */
	public void enqueue(DownloadJob job) {
		addLast(job);
	}
	
	/**
	 * Removes the first item in the list and returns it.
	 * 
	 * @return The first item in the list.
	 * 
	 * <dt>Postcondition:
	 *    <dd>First item in the list has been removed.
	 */
	public DownloadJob dequeue() throws EmptyQueueException {
		if(isEmpty()) {
			throw new EmptyQueueException();
		}
		return removeFirst();
	}
	
	/**
	 * Returns a String representation of this queue.
	 * 
	 * @return A String representation of this queue.
	 */
	public String toString() {
		if(isEmpty()) {
			return "empty";
		}
		String toString = "";
		for(DownloadJob job : this) {
			toString += "[#" + job.getId() + ":" + job.getDownloadSize() +
			  "Mb]";
		}
		return toString;
	}
	
}