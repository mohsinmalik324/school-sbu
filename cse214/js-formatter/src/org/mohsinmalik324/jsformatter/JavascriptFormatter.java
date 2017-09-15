package org.mohsinmalik324.jsformatter;

import java.util.EmptyStackException;

/**
 * An instance of this class can format JavaScript code.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 3
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class JavascriptFormatter {
	
	private JSStack stack; // Stack which keeps track of the section of code.
	private int indentLevel; // How many tabs to use.
	
	/**
	 * Returns an instance of JavascriptFormatter.
	 */
	public JavascriptFormatter() {
		stack = new JSStack();
		indentLevel = 0;
	}
	
	/**
	 * Formats the given JavaScript code.
	 * 
	 * @param input
	 *    The unformatted JavaScript code.
	 * 
	 * @return
	 *    The formatted JavaScript code.
	 */
	public String format(String input) {
		String formattedCode = "";
		// Loop through the input character by character.
		for(int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if(c != '}' && c != ';') {
				formattedCode += c;
			}
			switch(c) {
				case ';':
					// Check if there is a missing parenthesis.
					if(!stack.isEmpty() && stack.peek() == BlockType.PAREN) {
						formattedCode += "\n// Missing parenthesis.";
						return formattedCode;
					}
					// Append semicolon then check is we are in a for loop.
					formattedCode += ';';
					if(!stack.isEmpty() && stack.peek() != BlockType.FOR) {
						formattedCode += '\n';
						formattedCode += getTabs();
					} else if(stack.isEmpty()) {
						formattedCode += '\n';
					}
					break;
				case '{':
					// Push BRACE to stack and increment tabs.
					stack.push(BlockType.BRACE);
					indentLevel++;
					formattedCode += '\n';
					formattedCode += getTabs();
					break;
				case '(':
					// Push PAREN to stack.
					stack.push(BlockType.PAREN);
					break;
				case 'f':
					// Check if the following input matches or(.
					if(input.charAt(i + 1) == 'o' &&
					  input.charAt(i + 2) == 'r' &&
					  input.charAt(i + 3) == '(') {
						// Push FOR to stack.
						stack.push(BlockType.FOR);
						// Append for loop syntax.
						formattedCode += "or("; 
						// Skip ahead 3.
						i += 3;
					}
					break;
				case '}':
					// Checks for extra curly brace or a missing parenthesis.
					boolean extraBrace = false;
					boolean missingParen = false;
					// If there is a tab, remove it.
					if(formattedCode.charAt(formattedCode.length() - 1)
					  == '\t') {
						formattedCode = formattedCode.substring(0,
						  formattedCode.length() - 1);
					}
					formattedCode += '}';
					try {
						// Check for a brace at top of stack.
						// If there isn't one, too many parentheses.
						BlockType top = stack.pop();
						if(!top.equals(BlockType.BRACE)) {
							missingParen = true;
						}
					} catch(EmptyStackException e) {
						extraBrace = true;
					}
					if(missingParen) {
						formattedCode += "\n// Missing parenthesis.";
						return formattedCode;
					}
					if(extraBrace) {
						formattedCode += "\n// Extra brace found.";
						return formattedCode;
					}
					indentLevel--;
					formattedCode += '\n';
					formattedCode += getTabs();
					break;
				case ')':
					// Check for an extra parenthesis.
					boolean extraParen = false;
					try {
						BlockType top = stack.pop();
						if(!top.equals(BlockType.PAREN) &&
						  !top.equals(BlockType.FOR)) {
							extraParen = true;
						}
					} catch(EmptyStackException e) {
						extraParen = true;
					}
					if(extraParen) {
						formattedCode += "\n// Extra parenthesis found.";
						return formattedCode;
					}
					break;
			}
		}
		// Check for missing closing brace.
		if(!stack.isEmpty()) {
			BlockType top = stack.pop();
			if(top.equals(BlockType.BRACE)) {
				formattedCode += "\n// Missing closing brace.";
			}
		}
		return formattedCode;
	}
	
	/**
	 * Returns the correct number of tabs for the current indent level.
	 * 
	 * @return
	 *    The correct number of tabs for the current indent level.
	 */
	private String getTabs() {
		String tabs = "";
		for(int i = 0; i < indentLevel; i++) {
			tabs += '\t';
		}
		return tabs;
	}
	
}