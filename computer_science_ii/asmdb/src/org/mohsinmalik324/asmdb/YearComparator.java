package org.mohsinmalik324.asmdb;

import java.util.Comparator;

/**
 * Comparator which sorts Movie objects based on year.
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
public class YearComparator implements Comparator<Movie> {

	@Override
	public int compare(Movie left, Movie right) {
		if(left.getYear() > right.getYear()) {
			return 1;
		} else if(left.getYear() < right.getYear()) {
			return -1;
		}
		return 0;
	}
	
}