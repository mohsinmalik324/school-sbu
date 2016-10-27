package org.mohsinmalik324.decisiontreeclassifier;

import java.util.ArrayList;
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
	
	private List<String> keywords = new ArrayList<>();
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
	
	/**
	 * Sets the left child of this node.
	 * 
	 * @param left The new left child.
	 */
	public void setLeft(TreeNode left) {
		this.left = left;
	}
	
	/**
	 * Sets the right child of this node.
	 * 
	 * @param right The new right child.
	 */
	public void setRight(TreeNode right) {
		this.right = right;
	}
	
	/**
	 * Returns the left child of this node.
	 * 
	 * @return The left child of this node.
	 */
	public TreeNode getLeft() {
		return left;
	}
	
	/**
	 * Returns the right child of this node.
	 * 
	 * @return The right child of this node.
	 */
	public TreeNode getRight() {
		return right;
	}
	
	/**
	 * Returns the String representation of this node.
	 * 
	 * @return The String representation of this node.
	 */
	public String toString() {
		String toString = "";
		
		if(isLeaf()) {
			if(keywords.isEmpty()) {
				toString = "Node is a leaf. No message.";
			} else {
				String message = keywords.get(0);
				toString = "Node is a leaf. Message is: '" + message + "'";
			}
		} else {
			toString = "Node is not a leaf. Key(s): ";
			for(String key : keywords) {
				toString += "'" + key + "' ";
			}
		}
		
		return toString;
	}
	
}