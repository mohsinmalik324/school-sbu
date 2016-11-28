package org.mohsinmalik324.asmdb;

/**
 * Represents an actor.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 7
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class Actor {
	
	private String name;
	private int count;
	
	/**
	 * Returns an instance of Actor.
	 * @param name The name of the actor.
	 */
	public Actor(String name) {
		this.name = name;
		count = 0;
	}

	/**
	 * Returns the name of the actor.
	 * @return The name of the actor.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the actor.
	 * @param name The new name of the actor.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the number of movies the actor has played in.
	 * @return The number of movies the actor has played in.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the number of movies the actor has played in.
	 * @param count The new number of movies the actor has played in.
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
}