package org.mohsinmalik324.roomlookup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * The controller class for JavaFX integration.
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
public class MainController {
	
	// Buildings Tab
	@FXML
	private TextField addBuildingName;
	@FXML
	private ListView<String> buildings;
	
	public void addBuilding(ActionEvent event) {
		
	}
	
}