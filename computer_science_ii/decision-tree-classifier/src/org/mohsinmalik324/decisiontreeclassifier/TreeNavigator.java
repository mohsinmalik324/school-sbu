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
	public static TreeNavigator buildTree(String treeFile) throws IllegalArgumentException {
		if(treeFile == null) {
			throw new IllegalArgumentException("Invalid file name.");
		}
		File file = new File(treeFile);
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
				tree.processLine(line);
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
		String[] parts = line.split(";");
		String[] nodePosition = parts[0].split("-");
		String[] keys = parts[1].split(",");
		TreeNode node = new TreeNode();
		for(String key : keys) {
			node.getKeywords().add(key);
		}
		int maxDepth = nodePosition.length - 1;
		
		// Check if inserting root node.
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
		String classification = "Could not process this request."
				+ " Try something else.";
		
		String[] words = text.split(" ");
		cursor = root;
		
		while(cursor != null) {
			if(cursor.isLeaf()) {
				classification = cursor.getKeywords().get(0);
				break;
			}
			boolean setRight = false;
			for(String key : cursor.getKeywords()) {
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
	 * @return If the sentence contains the word.
	 */
	private static boolean doesSentenceContainWord(String[] sentence, String word) {
		for(String sentenceWord : sentence) {
			if(sentenceWord.equalsIgnoreCase(word)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the current path of the cursor.
	 * 
	 * @return The current path of the cursor.
	 */
	public String getPath() {
		String path = "";
		
		
		
		return path;
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
	 * Moves the cursor to the left child.
	 * 
	 * <dt>Postcondition:
	 *    <dd>Cursor contents are printed.
	 */
	public void cursorLeft() {
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
		cursor = cursor.getRight();
		DecisionTreeClassifier.println("Cursor moved to the right child. " +
		  cursor.toString());
	}
	
	/**
	 * Sets the keywords for the current cursor.
	 * 
	 * @param text The keywords.
	 */
	public void editCursor(String text) {
		
	}
	
}