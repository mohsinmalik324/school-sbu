package org.mohsinmalik324.roomlookup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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
	private TextField findRoomNumber;
	@FXML
	private TextField searchAV;
	@FXML
	private Button searchChalkboard;
	@FXML
	private Button searchWhiteboard;
	@FXML
	private ChoiceBox<String> roomsEdit;
	@FXML
	private TextField editSeats;
	@FXML
	private TextField editAV;
	@FXML
	private CheckBox updateWhiteboard;
	@FXML
	private CheckBox updateChalkboard;
	
	private boolean firstTime = true;
	
	public void addBuilding(ActionEvent event) {
		firstTimeRun();
		String buildingName = addBuildingName.getText();
		if(buildingName == null || buildingName.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Adding Building",
			  "The 'Building Name' field can't be empty.");
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
		if(choiceBox.getSelectionModel().getSelectedItem().
		  equalsIgnoreCase(buildingName)) {
			rooms.getItems().clear();
		}
		buildings.getItems().remove(buildingName);
		choiceBox.getItems().remove(buildingName);
		if(buildings.getItems().isEmpty()) {
			roomsTab.setDisable(true);
		}
	}
	
	public void buildingViewSummary(ActionEvent event) {
		String buildingName = buildings.getSelectionModel().getSelectedItem();
		if(buildingName == null || buildingName.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error",
			  "Error Viewing Summary For Building",
			  "Select a building from the list first.");
			return;
		}
		Building building = RoomLookup.getCampus().getBuilding(buildingName);
		if(building == null) {
			buildings.getItems().remove(buildingName);
			return;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String percentWhiteboard = building.percentWhiteboard() == 0D ? "0"
		  : df.format(building.percentWhiteboard());
		String percentChalkboard = building.percentChalkboard() == 0D ? "0"
		  : df.format(building.percentChalkboard());
		alert(AlertType.INFORMATION, "Building Summary",
		  "'" + buildingName + "' Summary",
		  "Seats: " + building.getTotalSeats() + "\n"
		  + "Percent with Whiteboard: " + percentWhiteboard + "%\n"
		  + "Percent with Chalkboard: " + percentChalkboard + "%\n"
		  + "AV Equipment: " + listToString(building.getAVEquipment()));
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
			  "The 'Room Number' field must contain a positive number.");
			return;
		}
		if(roomNumber < 1) {
			alert(AlertType.ERROR, "Error", "Error Adding Room",
			  "The 'Room Number' field must contain a positive number.");
			return;
		}
		Classroom classroom = building.getClassroom(roomNumber);
		if(classroom != null) {
			alert(AlertType.ERROR, "Error", "Error Adding Room",
			  "A room with room number '" + roomNumber + "' already exists.");
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
		rooms.getItems().add("Room #" + roomNumber);
		rooms.getItems().sort(new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				int int1 = 0;
				int int2 = 0;
				try {
					int1 = Integer.valueOf(str1.replace("Room #", ""));
					int2 = Integer.valueOf(str2.replace("Room #", ""));
				} catch(NumberFormatException e) {
					return 0;
				}
				if(int1 < int2) {
					return -1;
				}
				if(int1 > int2) {
					return 1;
				}
				return 0;
			}
		});
		
		roomsEdit.getItems().add("Room #" + roomNumber);
		roomsEdit.getItems().sort(new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				int int1 = 0;
				int int2 = 0;
				try {
					int1 = Integer.valueOf(str1.replace("Room #", ""));
					int2 = Integer.valueOf(str2.replace("Room #", ""));
				} catch(NumberFormatException e) {
					return 0;
				}
				if(int1 < int2) {
					return -1;
				}
				if(int1 > int2) {
					return 1;
				}
				return 0;
			}
		});
		
		addRoomNumber.setText("");
		addRoomSeats.setText("");
		addRoomAV.setText("");
		addRoomChalkboard.setSelected(false);
		addRoomWhiteboard.setSelected(false);
	}
	
	public void removeRoom(ActionEvent event) {
		String buildingName = choiceBox.getSelectionModel().getSelectedItem();
		if(buildingName == null || buildingName.equalsIgnoreCase("")) {
			rooms.getItems().clear();
			alert(AlertType.ERROR, "Error", "Error Removing Room",
			  "Select a building first.");
			return;
		}
		Building building = RoomLookup.getCampus().getBuilding(buildingName);
		if(building == null) {
			buildings.getItems().remove(buildingName);
			choiceBox.getItems().remove(buildingName);
			return;
		}
		String roomNumberString = rooms.getSelectionModel().getSelectedItem();
		if(roomNumberString == null || roomNumberString.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Removing Room", "Select "
			  + "a room to remove from the list first.");
			return;
		}
		rooms.getItems().remove(roomNumberString);
		roomsEdit.getItems().remove(roomNumberString);
		int roomNumber = 0;
		try {
			roomNumber = Integer.valueOf(roomNumberString.replace("Room #", ""));
		} catch(NumberFormatException e) {
			return;
		}
		try {
			building.removeClassroom(roomNumber);
		} catch(IllegalArgumentException e) {
			alert(AlertType.ERROR, "Error", "Error Removing Room",
			  "That room already doesn't exist.");
			return;
		}
	}
	
	public void load(ActionEvent event) {
		firstTimeRun();
		try {
			FileInputStream file = new FileInputStream("storage.obj");
			ObjectInputStream in = new ObjectInputStream(file);
			Campus campus = (Campus) in.readObject();
			RoomLookup.setCampus(campus);
			in.close();
			for(String buildingName : campus.keySet()) {
				Building building = campus.getBuilding(buildingName);
				System.out.println(buildingName + " " + building.keySet().size());
				buildings.getItems().add(buildingName);
				choiceBox.getItems().add(buildingName);
			}
			if(!campus.keySet().isEmpty()) {
				roomsTab.setDisable(false);
			}
		} catch (FileNotFoundException e) {
			alert(AlertType.ERROR, "Error", "Error Loading Save Data",
			  "No save data was found.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void save(ActionEvent event) {
		try {
			FileOutputStream file = new FileOutputStream("storage.obj");
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(RoomLookup.getCampus());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editRoom(ActionEvent event) {
		String buildingName = choiceBox.getSelectionModel().getSelectedItem();
		if(buildingName == null || buildingName.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Editing Room",
			  "Select a building from the dropdown first.");
			return;
		}
		Building building = RoomLookup.getCampus().getBuilding(buildingName);
		if(building == null) {
			buildings.getItems().remove(buildingName);
			choiceBox.getItems().remove(buildingName);
			return;
		}
		String roomNumberString = roomsEdit.getSelectionModel().
		  getSelectedItem();
		if(roomNumberString == null || roomNumberString.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Editing Room",
			  "Select a room to edit from the drop-down first.");
			return;
		}
		int roomNumber = 0;
		try {
			roomNumber = Integer.valueOf(roomNumberString.replace(
			  "Room #", ""));
		} catch(NumberFormatException e) {
			rooms.getItems().remove(roomNumberString);
			return;
		}
		Classroom classroom = building.getClassroom(roomNumber);
		if(classroom == null) {
			rooms.getItems().remove(roomNumberString);
			roomsEdit.getItems().remove(roomNumberString);
			return;
		}
		String seatsString = editSeats.getText();
		if(seatsString != null && !seatsString.equalsIgnoreCase("")) {
			int seats = 0;
			try {
				seats = Integer.valueOf(seatsString);
			} catch(NumberFormatException e) {
				alert(AlertType.ERROR, "Error", "Error Editing Room",
				  "Number of seats must be a non-negative number.");
				return;
			}
			classroom.setNumSeats(seats);
		}
		String avString = editAV.getText();
		if(avString != null && !avString.equalsIgnoreCase("")) {
			classroom.setAVEquipmentList(avString.split(","));
		}
		classroom.setHasWhiteboard(updateWhiteboard.isSelected());
		classroom.setHasChalkboard(updateChalkboard.isSelected());
		editSeats.setText("");
		editAV.setText("");
		updateWhiteboard.setSelected(false);
		updateChalkboard.setSelected(false);
	}
	
	public void roomInfo(ActionEvent event) {
		String buildingName = choiceBox.getSelectionModel().getSelectedItem();
		if(buildingName == null || buildingName.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Viewing Room Info",
			  "Select a building from the dropdown first.");
			return;
		}
		Building building = RoomLookup.getCampus().getBuilding(buildingName);
		if(building == null) {
			buildings.getItems().remove(buildingName);
			choiceBox.getItems().remove(buildingName);
			return;
		}
		String roomNumberString = rooms.getSelectionModel().
		  getSelectedItem();
		if(roomNumberString == null || roomNumberString.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Viewing Room Info",
			  "Select a room to view from the list first.");
			return;
		}
		int roomNumber = 0;
		try {
			roomNumber = Integer.valueOf(roomNumberString.replace(
			  "Room #", ""));
		} catch(NumberFormatException e) {
			rooms.getItems().remove(roomNumberString);
			return;
		}
		Classroom classroom = building.getClassroom(roomNumber);
		if(classroom == null) {
			rooms.getItems().remove(roomNumberString);
			roomsEdit.getItems().remove(roomNumberString);
			return;
		}
		String message = "Seats: " + classroom.getNumSeats() + "\n" +
		  (classroom.hasWhiteboard() ? "Has Whiteboard" : "No Whiteboard")
		  + "\n" + (classroom.hasChalkboard() ? "Has Chalkboard" :
		  "No Chalkboard") + "\nAV Equipment: " + arrayToString(
		  classroom.getAVEquipmentList());
		alert(AlertType.INFORMATION, "Viewing Room Info",
		  buildingName + " Room #" + roomNumber, message);
	}
	
	public void findRoom(ActionEvent event) {
		String buildingName = choiceBox.getSelectionModel().getSelectedItem();
		if(buildingName == null || buildingName.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Finding Room",
			  "Select a building first.");
			return;
		}
		Building building = RoomLookup.getCampus().getBuilding(buildingName);
		if(building == null) {
			choiceBox.getItems().remove(buildingName);
			buildings.getItems().remove(buildingName);
			return;
		}
		String roomNumberString = findRoomNumber.getText();
		if(roomNumberString == null || roomNumberString.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Finding Room",
			  "Input a room number first.");
			return;
		}
		int roomNumber = 0;
		try {
			roomNumber = Integer.valueOf(roomNumberString);
		} catch(NumberFormatException e) {
			alert(AlertType.ERROR, "Error", "Error Finding Room",
			  "The room number must be a positive number.");
			return;
		}
		if(roomNumber < 1) {
			alert(AlertType.ERROR, "Error", "Error Finding Room",
			  "The room number must be positive.");
			return;
		}
		if(building.containsKey(roomNumber)) {
			Classroom classroom = building.get(roomNumber);
			String message = "Seats: " + classroom.getNumSeats() + "\n" +
			  (classroom.hasWhiteboard() ? "Has Whiteboard" : "No Whiteboard")
			  + "\n" + (classroom.hasChalkboard() ? "Has Chalkboard" :
			  "No Chalkboard") + "\nAV Equipment: " + arrayToString(
			  classroom.getAVEquipmentList());
			alert(AlertType.INFORMATION, "Success",
			  "Found Room #" + roomNumber, message);
		} else {
			alert(AlertType.ERROR, "Error", "Error Finding Room",
			  "Room #" + roomNumber + " not found.");
		}
		findRoomNumber.setText("");
	}
	
	public void searchAV(ActionEvent event) {
		String av = searchAV.getText();
		if(av == null || av.equalsIgnoreCase("")) {
			alert(AlertType.ERROR, "Error", "Error Searching For Room",
			  "The 'AV Equipment' field can't be empty.");
			return;
		}
		searchAV.setText("");
		if(buildings.getItems().isEmpty()) {
			alert(AlertType.INFORMATION, "Room Search", "Room Not Found",
			  "The room was not found.\nNote that you have not added any"
			  + " buildings yet.");
			return;
		}
		List<String> rooms = new ArrayList<>();
		for(String buildingName : RoomLookup.getCampus().keySet()) {
			Building building = RoomLookup.getCampus().get(buildingName);
			for(Integer roomNumber : building.keySet()) {
				Classroom classroom = building.get(roomNumber);
				for(String equipment : classroom.getAVEquipmentList()) {
					if(equipment.equalsIgnoreCase(av)) {
						rooms.add(buildingName + " " + roomNumber.toString());
					}
				}
			}
		}
		if(rooms.isEmpty()) {
			alert(AlertType.INFORMATION, "Room Search", "Rooms Found", "None");
		} else {
			alert(AlertType.INFORMATION, "Room Search", "Rooms Found",
			  listToString(rooms));
		}
	}
	
	public void searchWhiteboard(ActionEvent event) {
		if(buildings.getItems().isEmpty()) {
			alert(AlertType.INFORMATION, "Room Search", "Room Not Found",
			  "The room was not found.\nNote that you have not added any"
			  + " buildings yet.");
			return;
		}
		List<String> rooms = new ArrayList<>();
		for(String buildingName : RoomLookup.getCampus().keySet()) {
			Building building = RoomLookup.getCampus().getBuilding(buildingName);
			for(Integer roomNumber : building.keySet()) {
				Classroom classroom = building.get(roomNumber);
				if(classroom.hasWhiteboard()) {
					rooms.add(buildingName + " " + roomNumber);
				}
			}
		}
		if(rooms.isEmpty()) {
			alert(AlertType.INFORMATION, "Room Search", "Rooms Found", "None");
		} else {
			alert(AlertType.INFORMATION, "Room Search", "Rooms Found",
			  listToString(rooms));
		}
	}
	
	public void searchChalkboard(ActionEvent event) {
		if(buildings.getItems().isEmpty()) {
			alert(AlertType.INFORMATION, "Room Search", "Room Not Found",
			  "The room was not found.\nNote that you have not added any"
			  + " buildings yet.");
			return;
		}
		List<String> rooms = new ArrayList<>();
		for(String buildingName : RoomLookup.getCampus().keySet()) {
			Building building = RoomLookup.getCampus().getBuilding(buildingName);
			for(Integer roomNumber : building.keySet()) {
				Classroom classroom = building.get(roomNumber);
				if(classroom.hasChalkboard()) {
					rooms.add(buildingName + " " + roomNumber);
				}
			}
		}
		if(rooms.isEmpty()) {
			alert(AlertType.INFORMATION, "Room Search", "Rooms Found", "None");
		} else {
			alert(AlertType.INFORMATION, "Room Search", "Rooms Found",
			  listToString(rooms));
		}
	}
	
	public static void alert(AlertType alertType, String title,
	  String header, String error) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(error);
		alert.showAndWait();
	}
	
	private static String listToString(List<String> list) {
		if(list.isEmpty()) {
			return "None";
		}
		String listString = "";
		for(int i = 0; i < list.size(); i++) {
			if(i != list.size() - 1) {
				listString += list.get(i) + ", ";
			} else {
				listString += list.get(i);
			}
		}
		return listString;
	}
	
	private static String arrayToString(String[] array) {
		if(array == null || array.length == 0) {
			return "None";
		}
		String listString = "";
		for(int i = 0; i < array.length; i++) {
			if(i != array.length - 1) {
				listString += array[i] + ", ";
			} else {
				listString += array[i];
			}
		}
		return listString;
	}
	
	private void firstTimeRun() {
		if(firstTime) {
			firstTime = false;
			choiceBox.getSelectionModel().selectedItemProperty().addListener(
			  new ChangeListener<String>() {
				@Override
				public void changed(
				  ObservableValue<? extends String> observable,
				  String oldValue, String newValue) {
					if(newValue != null) {
						Building building = RoomLookup.getCampus().
						  getBuilding(newValue);
						if(building != null) {
							rooms.getItems().clear();
							for(Integer roomNumber : building.keySet()) {
								rooms.getItems().add("Room #" + roomNumber);
							}
						}
					} else {
						rooms.getItems().clear();
					}
				}
			});
		}
	}
	
}