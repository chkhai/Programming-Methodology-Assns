/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	/**
	 * This method displays a message string near the bottom of the canvas.
	 * Every time this method is called, the previously displayed message (if
	 * any) is replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		if (message != null) {
			remove(message);
		}
		message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		double x = getWidth() / 2 - message.getWidth() / 2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		add(message, x, y);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the
	 * bottom of the screen) and then the given profile is displayed. The
	 * profile display includes the name of the user from the profile, the
	 * corresponding image (or an indication that an image does not exist), the
	 * status of the user, and a list of the user's friends in the social
	 * network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if(profile != null){
			showMessage("Displaying " + profile.getName());
			displayName(profile);
			displayStatus(profile);
			displayFriends(profile);
			displayPictue(profile);
		}
	}

	/*
	 * This method is called in displayProfile method. In this method we display
	 * the friends of selected profile.
	 */
	private void displayFriends(FacePamphletProfile profile) {
		double x = getWidth() / 2;
		double y = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN;;
		GLabel friendLabel = new GLabel("Friends:");
		friendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendLabel, x, y);
		Iterator<String> it = profile.getFriends();
		while(it.hasNext()){
			y += friendLabel.getAscent();
			GLabel friend = new GLabel(it.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, x, y);
		}
		
		
	}
	
	/*
	 * This method is called in displayProfile method. In this method we display 
	 * profile's status. If one doesn't have a status, then we draw that there is no
	 * current status.
	 */
	private void displayStatus(FacePamphletProfile profile) {
		GLabel status = null;
		double x = LEFT_MARGIN;;
		double y = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN;
		if(profile.getStatus() == null || profile.getStatus().equals("")){
			GLabel noStatus = new GLabel("No Current Status.");
			noStatus.setFont(PROFILE_STATUS_FONT);
			add(noStatus, x, y);
		}else{
			status = new GLabel(profile.getName() +" is " + profile.getStatus());
			status.setFont(PROFILE_STATUS_FONT);
			add(status, x, y);
		}
		
	}

	/*
	 * This method is called in displayProfile method. First of all we check that
	 * the image of the profile really exists. If it doesn't exist, we just draw
	 * blank rectangle and add text "No image" inside it. If profile does have an image
	 * we draw the image instead of rectangle.
	 */
	private void displayPictue(FacePamphletProfile profile) {
		if(profile.getImage() != null){
			GImage image = profile.getImage();
			double x = LEFT_MARGIN;
			double y = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN;
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image, x, y);
		}else{
			drawRectangle();
		}
	}

	/*
	 * This method is called when user doesn't have an image. So we draw
	 * rectangle and "No Image" text.
	 */
	private void drawRectangle() {
		add(new GRect(IMAGE_WIDTH, IMAGE_HEIGHT), LEFT_MARGIN, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN);
		GLabel noImage = new GLabel("No Image");
		noImage.setFont(PROFILE_IMAGE_FONT);
		double x = LEFT_MARGIN + IMAGE_WIDTH / 2 - noImage.getWidth() / 2;
		double y = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + IMAGE_HEIGHT / 2 + noImage.getAscent() / 2;
		add(noImage, x, y);
	}
	
	/*
	 * This method is called in displayProfile method. We display current user's 
	 * profile name in top left corner of the window.
	 */
	private void displayName(FacePamphletProfile profile) {
		name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.BLUE);
		add(name, LEFT_MARGIN, TOP_MARGIN + name.getAscent());

	}

	private GLabel message;
	private GLabel name;
}
