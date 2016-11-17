package org.mohsinmalik324.roomlookup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
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
	@FXML
	private Label buildingsText;
	@FXML
	private Button removeBuilding;
	
	// Rooms Tab
	@FXML
	private Tab roomsTab;
	@FXML
	private ChoiceBox<String> choiceBox;
	@FXML
	private ListView<String> rooms;
	@FXML
	private Button removeRoom;
	@FXML
	private TextField addRoomName;
	@FXML
	private Button addRoomButton;
	@FXML
	private Label selectBuildingText;
	
	public void addBuilding(ActionEvent event) {
		String buildingName = addBuildingName.getText();
		if(buildingName == null || buildingName.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Adding Building", "Input a "
			  + "name for the building in the text field first.");
			return;
		}
		Building building = RoomLookup.getCampus().getBuilding(buildingName);
		if(building != null) {
			alert(AlertType.ERROR, "Error", "Error Adding Building", "The "
			  + "building '" + buildingName + "' already exists.");
			return;
		}
		
		RoomLookup.getCampus().addBuilding(buildingName, new Building());
		if(buildings.getItems().isEmpty()) {
			roomsTab.setDisable(false);
		}
		buildings.getItems().add(buildingName);
		choiceBox.getItems().add(buildingName);
		addBuildingName.setText("");
		//alert(AlertType.CONFIRMATION, "Success", "Building Added", "The "
		//  + "building '" + buildingName + "' has been added.");
	}
	
	public void removeBuilding(ActionEvent event) {
		String buildingName = buildings.getSelectionModel().getSelectedItem();
		if(buildingName == null || buildingName.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Removing Building", "Select "
			  + "a building to remove from the list first.");
			return;
		}
		try {
			RoomLookup.getCampus().removeBuilding(buildingName);
		} catch(IllegalArgumentException e) {
			alert(AlertType.ERROR, "Error", "Error Removing Building", "The "
			  + "building '" + buildingName + "' already doesn't exist.");
			return;
		}
		buildings.getItems().remove(buildingName);
		choiceBox.getItems().remove(buildingName);
		if(buildings.getItems().isEmpty()) {
			roomsTab.setDisable(true);
		}
	}
	
	public void addRoom(ActionEvent event) {
		String roomName = addRoomName.getText();
		if(roomName == null || roomName.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Adding Room", "Input a name"
			  + " for the room in the text field first.");
			return;
		}
		String buildingName = choiceBox.getSelectionModel().getSelectedItem();
		if(buildingName == null || buildingName.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Adding Room",
			  "Select a building in the choice box first.");
			return;
		}
		Building building = RoomLookup.getCampus().getBuilding(buildingName);
		if(building == null) {
			buildings.getItems().remove(buildingName);
			choiceBox.getItems().remove(buildingName);
			return;
		}
		// TODO
	}
	
	public static void alert(AlertType alertType, String title,
	  String header, String error) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(error);
		alert.showAndWait();
	}
	
}