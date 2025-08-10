
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in
	 * the application, and taking care of any other initialization that needs
	 * to be performed.
	 */
	public void init() {
		createButtons();
		addActionListeners();
		database = new FacePamphletDatabase();
		canvas = new FacePamphletCanvas();
		add(canvas);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton && !name.getText().equals("")) {
			if (!database.containsProfile(name.getText())) {
				selectedProfile = new FacePamphletProfile(name.getText());
				database.addProfile(selectedProfile);
				canvas.displayProfile(selectedProfile);
				canvas.showMessage("New profile created!");
			} else {
				selectedProfile = database.getProfile(name.getText());
				canvas.displayProfile(selectedProfile);
				canvas.showMessage("Profile for " + name.getText() + " already exists!");
			}
			println(selectedProfile);
		} else if (e.getSource() == deleteButton && !name.getText().equals("")) {
			if (database.containsProfile(name.getText())) {
				database.deleteProfile(name.getText());
				selectedProfile = null;
				canvas.displayProfile(selectedProfile);
				canvas.showMessage("Profile of " + name.getText() + " has been deleted!");
			} else {
				selectedProfile = null;
				canvas.displayProfile(selectedProfile);
				canvas.showMessage("A profile with the name " + name.getText() + " doesn't exist!");
			}
		} else if (e.getSource() == lookupButton && !name.getText().equals("")) {
			if (database.containsProfile(name.getText())) {
				selectedProfile = database.getProfile(name.getText());
				canvas.displayProfile(selectedProfile);
				canvas.showMessage("Displaying " + selectedProfile.getName());
			} else {
				selectedProfile = null;
				canvas.displayProfile(selectedProfile);
				canvas.showMessage("A profile with the name " + name.getText() + " doesn't exist!");
			}
		} else if (e.getSource() == changeStatus || e.getSource() == statusField) {
			if (selectedProfile != null && !statusField.getText().equals("")) {
				selectedProfile.setStatus(statusField.getText());
				canvas.displayProfile(selectedProfile);
				canvas.showMessage("Status updated to " + selectedProfile.getStatus());
			} else {
				if (selectedProfile == null) {
					canvas.showMessage("Please select the profile to make changes.");
				} else {
					canvas.showMessage("Please re-enter status!");
				}
			}
		} else if (e.getSource() == changePicture || e.getSource() == pictureField) {
			if (selectedProfile != null) {
				if (!pictureField.getText().equals("")) {
					GImage image = selectedProfile.getImage();
					try {
						image = new GImage(pictureField.getText());
						selectedProfile.setImage(image);
						canvas.displayProfile(selectedProfile);
						canvas.showMessage(selectedProfile.getName() + "'s photo has been updated!");
					} catch (ErrorException ex) {
						canvas.showMessage("File can't be found!");
					}
				} else {
					canvas.showMessage("File can't be found!");
				}
			} else {
				canvas.showMessage("Please select the profile to make changes.");
			}
		} else if (e.getSource() == addFriend || e.getSource() == addFriendField) {
			if (selectedProfile != null) {
				if (database.containsProfile(addFriendField.getText())) {
					if (selectedProfile.addFriend(addFriendField.getText())) {
						database.getProfile(addFriendField.getText()).addFriend(selectedProfile.getName());
						canvas.displayProfile(selectedProfile);
						canvas.showMessage(addFriendField.getText() + " added as a friend.");
					} else {
						canvas.showMessage(addFriendField.getText() + " is already in your friend list!");
					}
				} else {
					canvas.showMessage(addFriendField.getText() + " doesn't have a profile!");
				}
			} else {
				canvas.showMessage("Please select the profile to make changes.");
			}
		}
		clearFields();
	}

	/*
	 * This method is used in actionPerformed method to clear all fields after
	 * action gets completed.
	 */
	private void clearFields() {
		name.setText("");
		statusField.setText("");
		pictureField.setText("");
		addFriendField.setText("");
	}

	/*
	 * This method is the initialization of the program. We create all the
	 * buttons and text fields we need and add action listeners to them.
	 */
	private void createButtons() {
		add(new JLabel("Name"), NORTH);
		name = new JTextField(TEXT_FIELD_SIZE);
		addButton = new JButton("Add");
		deleteButton = new JButton("Delete");
		lookupButton = new JButton("Lookup");
		statusField = new JTextField(TEXT_FIELD_SIZE);
		changeStatus = new JButton("Change Status");
		pictureField = new JTextField(TEXT_FIELD_SIZE);
		changePicture = new JButton("Change Picture");
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		addFriend = new JButton("Add Friend");
		add(name, NORTH);
		add(addButton, NORTH);
		add(deleteButton, NORTH);
		add(lookupButton, NORTH);
		add(statusField, WEST);
		add(changeStatus, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(pictureField, WEST);
		add(changePicture, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(addFriendField, WEST);
		add(addFriend, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		statusField.addActionListener(this);
		pictureField.addActionListener(this);
		addFriendField.addActionListener(this);
	}

	private JTextField name;
	private JTextField statusField;
	private JTextField pictureField;
	private JTextField addFriendField;
	private JButton addButton;
	private JButton deleteButton;
	private JButton lookupButton;
	private JButton changePicture;
	private JButton changeStatus;
	private JButton addFriend;
	private FacePamphletDatabase database;
	private FacePamphletProfile selectedProfile;
	private FacePamphletCanvas canvas;

}
