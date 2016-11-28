package org.mohsinmalik324.asmdb;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a movie.
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
public class Movie {
	
	private String title;
	private int year;
	private List<Actor> actors;
	
	/**
	 * Returns an instance of Movie.
	 * @param title The title of the movie.
	 * @param year The year the movie was created.
	 */
	public Movie(String title, int year) {
		this.title = title;
		this.year = year;
		actors = new ArrayList<>();
	}

	/**
	 * Returns the title of the movie.
	 * @return The title of the movie.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the movie.
	 * @param title The new title of the movie.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the year the movie was created.
	 * @return The year the movie was created.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year the movie was created.
	 * @param year The new year the movie was created.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Returns a list of the actors involved in the movie.
	 * @return A list of the actors involved in the movie.
	 */
	public List<Actor> getActors() {
		return actors;
	}

	/**
	 * Sets the list of actors involved in the movie.
	 * @param actors The new list of actors involved in the movie.
	 */
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	
}