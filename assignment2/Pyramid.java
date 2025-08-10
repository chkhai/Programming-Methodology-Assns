
/*
 * File: Pyramid.java
 * Name: Luka Chkhaidze
 * Section Leader: Giorgi Tkeshelashvili
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class Pyramid extends GraphicsProgram {

	/** Width of each bricks in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each bricks in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of each bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {
		for (int i = 1; i < BRICKS_IN_BASE + 1; i++) {
			makeHorizontalRowOfBricks(i);
		}
	}

	/*
	 * This method helps us build horizontal row of bricks. x and y are the
	 * coordinates of the first brick in the row. After creating the first
	 * brick, x coordinate increases by BRICK_WIDTH, y coordinate stays the same
	 * since its the same row. In one row we have to build BRICKS_IN_BASE-i+1
	 * bricks.
	 */
	private void makeHorizontalRowOfBricks(int i) {
		double x = getWidth() / 2 - BRICK_WIDTH * (BRICKS_IN_BASE - i) / 2;
		double y = getHeight() - i * BRICK_HEIGHT;
		for (int j = 0; j < BRICKS_IN_BASE + 1 - i; j++) {
			GRect rect = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			add(rect);
			x = x + BRICK_WIDTH;
		}
	}
}
