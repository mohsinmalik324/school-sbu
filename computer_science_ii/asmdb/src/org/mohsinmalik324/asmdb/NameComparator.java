package org.mohsinmalik324.asmdb;

import java.util.Comparator;

/**
 * Comparator which sorts Actor objects based on name.
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
public class NameComparator implements Comparator<Actor> {

	@Override
	public int compare(Actor left, Actor right) {
		return left.getName().compareTo(right.getName());
	}
	
}