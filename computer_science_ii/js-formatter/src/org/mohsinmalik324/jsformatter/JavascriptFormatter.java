package org.mohsinmalik324.jsformatter;

import java.util.EmptyStackException;

/**
 * 
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 2
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class JavascriptFormatter {
	
	private JSStack stack;
	private int indentLevel;
	
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
		for(int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if(c != '}') {
				formattedCode += c;
			}
			switch(c) {
				case ';':
					if(!stack.isEmpty() && stack.peek() != BlockType.FOR) {
						formattedCode += '\n';
						formattedCode += getTabs();
					} else if(stack.isEmpty()) {
						formattedCode += '\n';
					}
					break;
				case '{':
					stack.push(BlockType.BRACE);
					indentLevel++;
					formattedCode += '\n';
					formattedCode += getTabs();
					break;
				case '(':
					stack.push(BlockType.PAREN);
					break;
				case 'f':
					if(input.charAt(i + 1) == 'o' &&
					  input.charAt(i + 2) == 'r' &&
					  input.charAt(i + 3) == '(') {
						stack.push(BlockType.FOR);
						formattedCode += "or(";
						i += 3;
					}
					break;
				case '}':
					boolean extraBrace = false;
					boolean missingParen = false;
					if(formattedCode.charAt(formattedCode.length() - 1) == '\t') {
						formattedCode = formattedCode.substring(0, formattedCode.length() - 1);
					}
					formattedCode += '}';
					try {
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