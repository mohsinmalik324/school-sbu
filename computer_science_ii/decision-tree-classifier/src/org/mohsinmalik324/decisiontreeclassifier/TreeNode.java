package org.mohsinmalik324.decisiontreeclassifier;

import java.util.List;

/**
 * Represents the node of a tree.
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
public class TreeNode {
	
	private List<String> keywords;
	private TreeNode left;
	private TreeNode right;
	
	/**
	 * Returns the keywords list.
	 * 
	 * @return The keywords list.
	 */
	public List<String> getKeywords() {
		return keywords;
	}
	
	/**
	 * Returns if the node is a leaf.
	 * 
	 * @return If the node is a leaf.
	 * 
	 * <dt>Precondition:
	 *    <dd>This node is initialized.
	 * 
	 * <dt>Postcondition:
	 *    <dd>The tree remains unchanged.
	 */
	public boolean isLeaf() {
		return left == null && right == null;
	}
	
}