package org.mohsinmalik324.roomlookup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main driver class.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 6
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class RoomLookup extends Application {
	
	private static Campus campus = new Campus();
	
	/**
	 * Main method.
	 * @param args Program arguments.
	 */
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Room Lookup");
		stage.show();
	}

	public static Campus getCampus() {
		return campus;
	}
	
	public static void setCampus(Campus campus) {
		RoomLookup.campus = campus;
	}
	
}