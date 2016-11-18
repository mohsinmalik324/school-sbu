package org.mohsinmalik324.roomlookup;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
	private TextField addRoomNumber;
	@FXML
	private TextField addRoomSeats;
	@FXML
	private TextField addRoomAV;
	@FXML
	private CheckBox addRoomWhiteboard;
	@FXML
	private CheckBox addRoomChalkboard;
	@FXML
	private Button addRoomButton;
	@FXML
	private Label selectBuildingText;
	
	private boolean firstTime = true;
	
	public void addBuilding(ActionEvent event) {
		if(firstTime) {
			firstTime = false;
			choiceBox.getSelectionModel().selectedItemProperty().addListener(
			  new ChangeListener<String>() {
				@Override
				public void changed(
				  ObservableValue<? extends String> observable,
				  String oldValue, String newValue) {
					if(oldValue == null && newValue != null) {
						Building building = RoomLookup.getCampus().
						  getBuilding(newValue);
						if(building != null) {
							rooms.getItems().clear();
							for(Integer roomName : building.keySet()) {
								rooms.getItems().add("Room #" +
								  roomName.toString());
							}
						}
					}
				}
			});
		}
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
		String roomNumberString = addRoomNumber.getText();
		if(roomNumberString == null || roomNumberString.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Adding Room",
			  "The 'Room Number' field can't be empty.");
			return;
		}
		int roomNumber = 0;
		try {
			roomNumber = Integer.valueOf(roomNumberString);
		} catch(NumberFormatException e) {
			alert(AlertType.ERROR, "Error", "Error Adding Room",
			  "The 'Room Number' field must contain a non-negative number.");
			return;
		}
		if(roomNumber < 0) {
			alert(AlertType.ERROR, "Error", "Error Adding Room",
			  "The 'Room Number' field must contain a non-negative number.");
			return;
		}
		String seatsString = addRoomSeats.getText();
		if(seatsString == null || seatsString.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Adding Room",
			  "The 'Number of Seats' field can't be empty.");
			return;
		}
		int seats = 0;
		try {
			seats = Integer.valueOf(seatsString);
		} catch(NumberFormatException e) {
			alert(AlertType.ERROR, "Error", "Error Adding Room",
			  "The 'Number of Seats' field must contain a "
			  + "non-negative number.");
			return;
		}
		if(seats < 0) {
			alert(AlertType.ERROR, "Error", "Error Adding Room",
			  "The 'Number of Seats' field must contain a "
			  + "non-negative number.");
			return;
		}
		String avString = addRoomAV.getText();
		String[] av = null;
		if(avString != null && avString.length() > 0) {
			av = avString.split(",");
		}
		boolean whiteboard = addRoomWhiteboard.isSelected();
		boolean chalkboard = addRoomChalkboard.isSelected();
		building.addClassroom(roomNumber, new Classroom(
		  whiteboard, chalkboard, seats, av));
		// TODO
		alert(AlertType.INFORMATION, "Success", "", "");
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