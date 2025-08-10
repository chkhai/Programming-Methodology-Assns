
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	private int count = 8;
	GLabel display;
	private String incorrectGuesses = "";

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		setSize(377, 467);
		removeAll();
		GLine scaffoldVert = new GLine(getWidth() / 2 - BEAM_LENGTH, SCAFFOLD_Y_OFFSET, getWidth() / 2 - BEAM_LENGTH,
				SCAFFOLD_Y_OFFSET + SCAFFOLD_HEIGHT);
		GLine scaffoldBeam = new GLine(getWidth() / 2, SCAFFOLD_Y_OFFSET, getWidth() / 2 - BEAM_LENGTH,
				SCAFFOLD_Y_OFFSET);
		GLine scaffoldRope = new GLine(getWidth() / 2, SCAFFOLD_Y_OFFSET, getWidth() / 2,
				ROPE_LENGTH + SCAFFOLD_Y_OFFSET);
		add(scaffoldBeam);
		add(scaffoldRope);
		add(scaffoldVert);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		if (display != null) {
			remove(display);
		}
		double m = getWidth() / 4;
		double k = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH + 30;
		display = new GLabel(word);
		display.setFont("Helvatica-20");
		add(display, m, k);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		count--;
		if (count == 7) {
			double x = getWidth() / 2 - HEAD_RADIUS;
			double y = SCAFFOLD_Y_OFFSET + ROPE_LENGTH;
			GOval head = new GOval(x, y, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
			add(head);
		} else if (count == 6) {
			double x1 = getWidth() / 2;
			double y1 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS;
			double x2 = x1;
			double y2 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + 2 * HEAD_RADIUS;
			GLine body = new GLine(x1, y1, x2, y2);
			add(body);
		} else if (count == 5) {
			double x1 = getWidth() / 2;
			double y1 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
			double x2 = getWidth() / 2 - UPPER_ARM_LENGTH;
			double y2 = y1;
			GLine LeftUpperArm = new GLine(x1, y1, x2, y2);
			add(LeftUpperArm);
			double x3 = x2;
			double y3 = y2;
			double x4 = x2;
			double y4 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH;
			GLine LeftLowerArm = new GLine(x3, y3, x4, y4);
			add(LeftLowerArm);
		} else if (count == 4) {
			double x1 = getWidth() / 2;
			double y1 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
			double x2 = getWidth() / 2 + UPPER_ARM_LENGTH;
			double y2 = y1;
			GLine RightUpperArm = new GLine(x1, y1, x2, y2);
			add(RightUpperArm);
			double x3 = x2;
			double y3 = y2;
			double x4 = x2;
			double y4 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH;
			GLine RightLowerArm = new GLine(x3, y3, x4, y4);
			add(RightLowerArm);
		} else if (count == 3) {
			double x1 = getWidth() / 2;
			double y1 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH;
			double x2 = getWidth() / 2 - HIP_WIDTH;
			double y2 = y1;
			GLine leftHip = new GLine(x1, y1, x2, y2);
			add(leftHip);
			double x3 = x2;
			double y3 = y2;
			double x4 = x2;
			double y4 = y2 + LEG_LENGTH;
			GLine leftLeg = new GLine(x3, y3, x4, y4);
			add(leftLeg);
		} else if (count == 2) {
			double x1 = getWidth() / 2;
			double y1 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH;
			double x2 = getWidth() / 2 + HIP_WIDTH;
			double y2 = y1;
			GLine rightHip = new GLine(x1, y1, x2, y2);
			add(rightHip);
			double x3 = x2;
			double y3 = y2;
			double x4 = x2;
			double y4 = y2 + LEG_LENGTH;
			GLine rightLeg = new GLine(x3, y3, x4, y4);
			add(rightLeg);
		} else if (count == 1) {
			double x1 = getWidth() / 2 - HIP_WIDTH;
			double y1 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
			double x2 = x1 - FOOT_LENGTH;
			double y2 = y1;
			GLine leftFoot = new GLine(x1, y1, x2, y2);
			add(leftFoot);
		} else if (count == 0) {
			double x1 = getWidth() / 2 + HIP_WIDTH;
			double y1 = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
			double x2 = x1 + FOOT_LENGTH;
			double y2 = y1;
			GLine rightFoot = new GLine(x1, y1, x2, y2);
			add(rightFoot);
		}
		incorrectGuesses = updateIncorrectGuesses(incorrectGuesses, letter);
		double m = getWidth() / 4;
		double k = SCAFFOLD_Y_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH + 50;
		GLabel label = new GLabel(incorrectGuesses);
		label.setFont("Helvatica-20");
		add(label, m, k);
	}

	private String updateIncorrectGuesses(String incorrectGuesses, char letter) {
		incorrectGuesses = incorrectGuesses + letter;
		return incorrectGuesses;
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int SCAFFOLD_Y_OFFSET = 10;
}
