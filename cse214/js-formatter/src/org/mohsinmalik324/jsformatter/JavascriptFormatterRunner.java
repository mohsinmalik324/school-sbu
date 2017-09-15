package org.mohsinmalik324.jsformatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The main driver class.
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
public class JavascriptFormatterRunner {
	
	/**
	 * Method ran when the program starts.
	 * 
	 * @param args
	 *    Arguments passes to the program.
	 */
	public static void main(String[] args) {
		// Get file input.
		String input = "";
		Scanner scanner = new Scanner(System.in);
		System.out.print("File name: ");
		String fileName = scanner.nextLine();
		scanner.close();
		File file = new File(fileName);
		// Check if file exists.
		if(file.exists()) {
			// Initialize main formatter object.
			JavascriptFormatter formatter = new JavascriptFormatter();
			// Read contents of file into a String.
			BufferedReader reader = null;
			try {
				InputStream in = new FileInputStream(file);
				reader = new BufferedReader(new InputStreamReader(in));
				input = "";
				String line = reader.readLine();
				while(line != null) {
					input += line;
					line = reader.readLine();
				}
				// Output formatted code.
				System.out.print(formatter.format(input));
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
		} else {
			System.err.println(fileName + " does not exist.");
		}
	}
	
}