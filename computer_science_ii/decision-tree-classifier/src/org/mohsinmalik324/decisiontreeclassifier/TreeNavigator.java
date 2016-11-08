package org.mohsinmalik324.decisiontreeclassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	
	/**
	 * Returns the cursor node.
	 * 
	 * @return The cursor node.
	 * 
	 * <dt>Precondition:
	 *    <dd>Cursor is not null (if it is, null is returned).
	 *    
	 * <dt>Postcondition:
	 *    <dd>Cursor is returned to the caller.
	 */
	public TreeNode getCursor() {
		return cursor;
	}
	
	/**
	 * Returns the root node.
	 * 
	 * @return The root node.
	 */
	public TreeNode getRoot() {
		return root;
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
	 * @throws IllegalArgumentException treeFile is null, file does not exist,
	 * file is not readable, or file is not valid.
	 */
	public static TreeNavigator buildTree(String treeFile)
	  throws IllegalArgumentException {
		
		if(treeFile == null) {
			throw new IllegalArgumentException("Invalid file name.");
		}
		File file = new File(treeFile);
		// Check if file is valid.
		if(!file.exists() || !file.isFile() || !file.canRead()) {
			throw new IllegalArgumentException("Invalid file.");
		}
		TreeNavigator tree = new TreeNavigator();
		// Read contents of file into a String.
		BufferedReader reader = null;
		try {
			InputStream in = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			while(line != null) {
				// Process line in file into a tree node.
				tree.processLine(line);
				// Get next line of text in file.
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Close reader.
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return tree;
	}
	
	/**
	 * Processes the line from the text file into the tree.
	 * 
	 * @param line The line from the text file.
	 */
	private void processLine(String line) {
		// Split all parts of line data into arrays.
		String[] parts = line.split(";");
		String[] nodePosition = parts[0].split("-");
		String[] keys = parts[1].split(",");
		TreeNode node = new TreeNode();
		node.setKeywords(keys);
		int maxDepth = nodePosition.length - 1;
		
		// Check if inserting root node.
		// Otherwise insert node into tree.
		if(maxDepth == 0) {
			root = node;
		} else if(maxDepth > 0) {
			cursor = root;
			for(int i = 0; i < maxDepth; i++) {
				boolean left = nodePosition[i + 1].equals("0");
				
				if(left) {
					if(cursor.getLeft() == null) {
						cursor.setLeft(node);
						break;
					} else {
						cursor = cursor.getLeft();
					}
				} else {
					if(cursor.getRight() == null) {
						cursor.setRight(node);
						break;
					} else {
						cursor = cursor.getRight();
					}
				}
			}
		}
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
		// Defailt classification.
		String classification = "Could not process this request."
				+ " Try something else.";
		
		// Split the words into array.
		String[] words = text.split(" ");
		// Start at root.
		cursor = root;
		
		while(cursor != null) {
			// If cursor is leaf, we return the keyword.
			if(cursor.isLeaf()) {
				classification = cursor.getKeywords()[0];
				break;
			}
			// boolean to check if we need to go left or right.
			boolean setRight = false;
			for(String key : cursor.getKeywords()) {
				// Check if sentence contains the word.
				// If it does we go right, otherwise left.
				if(doesSentenceContainWord(words, key)) {
					cursor = cursor.getRight();
					setRight = true;
					break;
				}
			}
			if(!setRight) {
				cursor = cursor.getLeft();
			}
		}
		
		return classification;
	}
	
	/**
	 * Checks if a word is in a sentence.
	 * 
	 * @param sentence The sentence as an array of Strings.
	 * @param word The word to check for in the sentence.
	 * 
	 * @return true if the sentence contains the word, otherwise false.
	 */
	private static boolean doesSentenceContainWord
	  (String[] sentence, String word) {
		
		for(String sentenceWord : sentence) {
			if(sentenceWord.equalsIgnoreCase(word)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Creates the root if it is null.
	 * 
	 * <dt>Postconditions:
	 *    <dd>cursor points to new root.
	 */
	public void createRoot() {
		if(root == null) {
			root = new TreeNode();
			cursor = root;
		}
	}
	
	/**
	 * Returns the current path of the cursor.
	 * 
	 * @return The current path of the cursor.
	 */
	public String getPath() {
		// This method will use a helper method to return a raw version of the
		// path taken to get to the cursor node. This method will then clean up
		// the raw version to make it look pretty.
		if(cursor == null) {
			return null;
		}
		// Get raw path.
		String path = getPath(root, cursor);
		if(path == null) {
			return path;
		}
		// Split path by comma separator.
		String[] pathArray = path.split(",");
		// If path length is 1, return first element.
		if(pathArray.length == 1) {
			return pathArray[0];
		}
		TreeNode tmp = root;
		String actualPath = "";
		// Start from second to last until 0 OR tmp is null.
		for(int i = pathArray.length - 2; i >= 0 && tmp != null; i--) {
			// Get current path part.
			String pathPart = pathArray[i];
			// Get left and right child.
			TreeNode left = tmp.getLeft();
			TreeNode right = tmp.getRight();
			boolean isYes;
			// Check if the left child contains the keyword.
			// Otherwise, check if right child contains keyword.
			if(left != null && left.containsKeyword(pathPart)) {
				isYes = false;
			} else if(right != null && right.containsKeyword(pathPart)) {
				isYes = true;
			} else {
				return null;
			}
			String[] keys = tmp.getKeywords();
			if(isYes) {
				tmp = tmp.getRight();
			} else {
				actualPath += "NOT ";
				tmp = tmp.getLeft();
			}
			// Add keywords.
			for(String key : keys) {
				actualPath += key + ",";
			}
			// Remove last character.
			actualPath = actualPath.substring(0, actualPath.length() - 1);
			actualPath += " -> ";
		}
		actualPath += tmp.getKeywords()[0];
		return actualPath;
	}
	
	/**
	 * Recursive helper method for getPath().
	 * 
	 * @param root The root node of the tree/sub-tree.
	 * @param dest The destination node.
	 * 
	 * @return The path to the destination node.
	 */
	private String getPath(TreeNode root, TreeNode dest) {
		if(root == null) {
			return null;
		}
		String path = "";
		if(root == dest || (path = getPath(root.getLeft(), dest)) != null ||
		  (path = getPath(root.getRight(), dest)) != null) {
			
			String toAdd = "";
			if(root.getKeywords() != null && root.getKeywords().length >= 1) {
				toAdd = root.getKeywords()[0] + ",";
			}
			path += toAdd;
			return path;
		}
		return null;
	}
	
	public TreeNode getParent(TreeNode root, TreeNode node) {
		if(root.getLeft() == node || root.getRight() == node) {
			return root;
		}
		TreeNode parent = null;
		if(root.getLeft() != null) {
			parent = getParent(root.getLeft(), node);
		}
		if(parent == null && root.getRight() != null) {
			parent = getParent(root.getRight(), node);
		}
		return parent;
	}
	
	/**
	 * Resets the cursor to the root node.
	 * 
	 * <dt>Postconditions:
	 *    <dd>Cursor references the root node.
	 *    <dd>Cursor contents are printed.
	 */
	public void resetCursor() {
		cursor = root;
		DecisionTreeClassifier.println("Cursor reset to root. " +
		  (cursor == null ? "root is empty." : cursor.toString()));
	}
	
	/**
	 * Moves cursor to the parent of the cursor.
	 */
	public void cursorParent() {
		if(cursor == null) {
			DecisionTreeClassifier.println("");
			return;
		}
		if(cursor == root) {
			DecisionTreeClassifier.println("Cursor is root, therefore, "
			  + "it has no parent.");
			
			return;
		}
		cursor = getParent(root, cursor);
		DecisionTreeClassifier.println("Cursor moved to parent. "
		  + cursor.toString());
	}
	
	/**
	 * Moves the cursor to the left child.
	 * 
	 * <dt>Postcondition:
	 *    <dd>Cursor contents are printed.
	 */
	public void cursorLeft() {
		if(cursor == null) {
			DecisionTreeClassifier.println("Cursor is null.");
			return;
		}
		if(cursor.getLeft() == null) {
			DecisionTreeClassifier.println("Cursor's left child is null.");
			return;
		}
		cursor = cursor.getLeft();
		DecisionTreeClassifier.println("Cursor moved to the left child. " +
		  cursor.toString());
	}
	
	/**
	 * Moves the cursor to the right child.
	 * 
	 * <dt>Postcondition:
	 *    <dd>Cursor contents are printed.
	 */
	public void cursorRight() {
		if(cursor == null) {
			DecisionTreeClassifier.println("Cursor is null.");
			return;
		}
		if(cursor.getRight() == null) {
			DecisionTreeClassifier.println("Cursor's right child is null.");
			return;
		}
		cursor = cursor.getRight();
		DecisionTreeClassifier.println("Cursor moved to the right child. " +
		  cursor.toString());
	}
	
}