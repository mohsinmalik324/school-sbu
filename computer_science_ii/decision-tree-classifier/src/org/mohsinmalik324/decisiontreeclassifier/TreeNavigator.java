package org.mohsinmalik324.decisiontreeclassifier;

/**
 * Represents a tree.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 5
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class TreeNavigator {
	
	private TreeNode root;
	private TreeNode cursor;
	
	public TreeNode getRoot() {
		return root;
	}
	
	public TreeNode getCursor() {
		return cursor;
	}
	
	/**
	 * Reads in a text file describing a TreeNavigator.
	 * 
	 * @param treeFile The file name of the tree.
	 * 
	 * @return The created tree.
	 * 
	 * <dt>Preconditions:
	 *    <dd>treeFile is not null.
	 *    <dd>File exists, is readable, and is valid.
	 *    
	 * @throws IllegalArgumentException treeFile is null.
	 */
	public static TreeNavigator buildTree(String treeFile) throws IllegalArgumentException {
		return null;
	}
	
	/**
	 * Classifies the text with the given tree and returns the classification
	 * as a String.
	 * 
	 * @param text The given text.
	 * 
	 * @return The classification as a String.
	 */
	public String classify(String text) {
		return "";
	}
	
}