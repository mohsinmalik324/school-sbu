package org.mohsinmalik324.roomlookup;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a campus.
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
public class Campus extends HashMap<String, Building> implements Serializable {

	/**
	 * Version ID.
	 */
	private static final long serialVersionUID = 501251973530131709L;
	
	/**
	 * Add a building to this campus.
	 * @param buildingName The building's name.
	 * @param building The building to add.
	 * @throws IllegalArgumentException buildingName is null or buildingName
	 * is already in use.
	 */
	public void addBuilding(String buildingName, Building building)
	  throws IllegalArgumentException {
		// Check for illegal arguments.
		if(buildingName == null) {
			throw new IllegalArgumentException("buildingName can't be null.");
		}
		if(containsKey(buildingName)) {
			throw new IllegalArgumentException("This building already"
			  + " exists.");
		}
		put(buildingName, building);
	}
	
	/**
	 * Returns a building from the name provided.
	 * @param buildingName The name of the building.
	 * @return A building if the name provided exists, otherwise null.
	 */
	public Building getBuilding(String buildingName) {
		// Check if building exists.
		if(containsKey(buildingName)) {
			return get(buildingName);
		}
		return null;
	}
	
	/**
	 * Removes a building.
	 * @param buildingName The name of the building.
	 * @throws IllegalArgumentException buildingName is null or buildingName
	 * does not exist.
	 */
	public void removeBuilding(String buildingName)
	  throws IllegalArgumentException {
		// Check for illegal arguments.
		if(buildingName == null) {
			throw new IllegalArgumentException("buildingName can't be null.");
		}
		if(!containsKey(buildingName)) {
			throw new IllegalArgumentException("This building doesn't exist.");
		}
		remove(buildingName);
	}
	
}