package org.mohsinmalik324.decisiontreeclassifier;

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
	
	private String[] keywords = null;
	private TreeNode left;
	private TreeNode right;
	
	/**
	 * Returns the keywords list.
	 * 
	 * @return The keywords list.
	 */
	public String[] getKeywords() {
		return keywords;
	}
	
	/**
	 * Sets the keywords.
	 * 
	 * @param keywords The new keywords.
	 */
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
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
	 * Checks if this node contains the keyword.
	 * 
	 * @param keyword The keyword to check for.
	 * @return If the keyword is in this node.
	 */
	public boolean containsKeyword(String keyword) {
		for(String key : keywords) {
			if(key.equalsIgnoreCase(keyword)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the String representation of this node.
	 * 
	 * @return The String representation of this node.
	 */
	public String toString() {
		String toString = "";
		
		if(isLeaf()) {
			if(keywords == null || keywords.length == 0) {
				toString = "Node is a leaf. No message.";
			} else {
				String message = keywords[0];
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