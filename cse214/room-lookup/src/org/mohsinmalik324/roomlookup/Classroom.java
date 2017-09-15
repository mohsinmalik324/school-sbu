package org.mohsinmalik324.roomlookup;

import java.io.Serializable;

/**
 * Represents a classroom.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 6
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class Classroom implements Serializable {

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 8744375825301969618L;
	private boolean hasWhiteboard;
	private boolean hasChalkboard;
	private int numSeats;
	private String[] AVEquipmentList;
	
	/**
	 * Returns a new instance of Classroom.
	 * @param hasWhiteboard If the classroom has a whiteboard.
	 * @param hasChalkboard If the classroom has a chalkboard.
	 * @param numSeats The number of seats.
	 * @param AVEquipmentList The AV equipment list.
	 */
	public Classroom(boolean hasWhiteboard, boolean hasChalkboard, int numSeats, String[] AVEquipmentList) {
		this.hasWhiteboard = hasWhiteboard;
		this.hasChalkboard = hasChalkboard;
		this.numSeats = numSeats;
		this.AVEquipmentList = AVEquipmentList;
	}
	
	/**
	 * Returns true if the room has a white board, otherwise false.
	 * @return true if the room has a white board, otherwise false.
	 */
	public boolean hasWhiteboard() {
		return hasWhiteboard;
	}
	
	/**
	 * Sets if the room has a white board.
	 * @param hasWhiteboard The new white board status of this room.
	 */
	public void setHasWhiteboard(boolean hasWhiteboard) {
		this.hasWhiteboard = hasWhiteboard;
	}

	/**
	 * Returns true if the room has a chalk board, otherwise false.
	 * @return true if the room has a chalk board, otherwise false.
	 */
	public boolean hasChalkboard() {
		return hasChalkboard;
	}

	/**
	 * Sets if the room has a chalk board.
	 * @param hasChalkboard The new chalk board status of this room.
	 */
	public void setHasChalkboard(boolean hasChalkboard) {
		this.hasChalkboard = hasChalkboard;
	}

	/**
	 * Returns the number of seats in this room.
	 * @return The number of seats in this room.
	 */
	public int getNumSeats() {
		return numSeats;
	}

	/**
	 * Sets the number of seats in this room.
	 * @param numSeats The new number of seats.
	 */
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	/**
	 * Returns the equipment list of this room.
	 * @return The equipment list of this room.
	 */
	public String[] getAVEquipmentList() {
		return AVEquipmentList;
	}

	/**
	 * Sets what equipment is available in this room.
	 * @param aVEquipmentList The new equipment list for this room.
	 */
	public void setAVEquipmentList(String[] aVEquipmentList) {
		AVEquipmentList = aVEquipmentList;
	}
	
}