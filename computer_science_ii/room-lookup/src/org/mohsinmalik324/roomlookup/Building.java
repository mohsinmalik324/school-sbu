package org.mohsinmalik324.roomlookup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a building.
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
public class Building extends HashMap<Integer, Classroom>
  implements Serializable {
	
	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 3134912367852647309L;

	/**
	 * Adds a classroom into this building.
	 * @param roomNumber The room number (or key) for this classroom.
	 * @param classroom The classroom to add.
	 * @throws IllegalArgumentException classroom is null, roomNumber already
	 * exists, or roomNumber is not positive.
	 */
	public void addClassroom(int roomNumber, Classroom classroom)
	  throws IllegalArgumentException {
		// Check for illegal arguments.
		if(classroom == null) {
			throw new IllegalArgumentException("classroom is null.");
		}
		if(roomNumber < 1) {
			throw new IllegalArgumentException("The room number must"
			  + " be positive.");
		}
		if(containsKey(roomNumber)) {
			throw new IllegalArgumentException("This room number already exists.");
		}
		put(roomNumber, classroom);
	}
	
	/**
	 * Returns a classroom given a room number.
	 * @param roomNumber The room number for the classroom.
	 * @return A classroom given a valid room number, otherwise null.
	 */
	public Classroom getClassroom(int roomNumber) {
		// Check if the room number exists.
		if(containsKey(roomNumber)) {
			return get(roomNumber);
		}
		return null;
	}
	
	/**
	 * Removes a classroom given a room number.
	 * @param roomNumber The room number for the classroom.
	 * @throws IllegalArgumentException roomNumber is not positive or
	 * roomNumber does not exist.
	 */
	public void removeClassroom(int roomNumber)
	  throws IllegalArgumentException {
		// Check for illegal arguments.
		if(roomNumber < 1) {
			throw new IllegalArgumentException("The room number must "
			  + "be positive.");
		}
		if(!containsKey(roomNumber)) {
			throw new IllegalArgumentException("This room number "
			  + "doesn't exist.");
		}
		remove(roomNumber);
	}
	
	/**
	 * Gets the total seats for this building.
	 * @return The total seats for this building.
	 */
	public int getTotalSeats() {
		int seats = 0;
		for(Integer roomNumber : keySet()) {
			seats += get(roomNumber).getNumSeats();
		}
		return seats;
	}
	
	/**
	 * Gets the percent of rooms with a whiteboard.
	 * @return The percent of rooms with a whiteboard.
	 */
	public double percentWhiteboard() {
		if(size() == 0) {
			return 0D;
		}
		double percent = 0D;
		int withWhiteboard = 0;
		for(Integer roomNumber : keySet()) {
			if(get(roomNumber).hasWhiteboard()) {
				withWhiteboard++;
			}
		}
		percent = (double) ((double) withWhiteboard / (double) size());
		return percent * 100;
	}
	
	/**
	 * Gets the percent of rooms with a chalkboard.
	 * @return The percent of rooms with a chalkboard.
	 */
	public double percentChalkboard() {
		if(size() == 0) {
			return 0D;
		}
		double percent = 0D;
		int withChalkboard = 0;
		for(Integer roomNumber : keySet()) {
			if(get(roomNumber).hasChalkboard()) {
				withChalkboard++;
			}
		}
		percent = (double) ((double) withChalkboard / (double) size());
		return percent * 100;
	}
	
	/**
	 * Gets all the AV Equipment.
	 * @return All the AV Equipment.
	 */
	public List<String> getAVEquipment() {
		List<String> av = new ArrayList<>();
		for(Integer roomNumber : keySet()) {
			Classroom classroom = get(roomNumber);
			for(String avEquipment : classroom.getAVEquipmentList()) {
				if(!av.contains(avEquipment)) {
					av.add(avEquipment);
				}
			}
		}
		return av;
	}
	
}