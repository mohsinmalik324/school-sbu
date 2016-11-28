package org.mohsinmalik324.asmdb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Manages the movies and actors.
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
public class MovieManager {
	
	private List<Movie> movies;
	private List<Actor> actors;
	
	/**
	 * Returns an instance of MovieManager.
	 */
	public MovieManager() {
		movies = new ArrayList<>();
		actors = new ArrayList<>();
	}
	
	/**
	 * Returns an instance of MovieManager using the URL provided.
	 * @param url The URL which will be used to fill this MovieManager.
	 */
	public MovieManager(String url) {
		this();
		// TODO
	}
	
	/**
	 * Returns a list of movies.
	 * @return A list of movies.
	 */
	public List<Movie> getMovies() {
		return movies;
	}
	
	/**
	 * Returns a list of actors.
	 * @return A list of actors.
	 */
	public List<Actor> getActors() {
		return actors;
	}
	
	/**
	 * Sorts the list of movies based on the given comparator and returns it.
	 * @param comp The comparator to use.
	 * @return The sorted list.
	 */
	public List<Movie> getSortedMovies(Comparator<Movie> comp) {
		movies.sort(comp);
		return movies;
	}
	
	/**
	 * Sorts the list of actors based on the given comparator and returns it.
	 * @param comp The comparator to use.
	 * @return The sorted list.
	 */
	public List<Actor> getSortedActors(Comparator<Actor> comp) {
		actors.sort(comp);
		return actors;
	}
	
}