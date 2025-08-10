
/*
 * File: Breakout.java
 * -------------------
 * Name: Luka Chkhaidze
 * Section Leader: Giorgi Tkeshelashvili
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** The time of pause */
	private static final int PAUSE_TIME = 10;

	/** The total number of bricks */
	private int numBricks = NBRICKS_PER_ROW * NBRICK_ROWS;

	private RandomGenerator rgen = RandomGenerator.getInstance();

	/** The horizontal speed of ball */
	private double vx;

	/** The vertical speed of ball */
	private double vy;

	private GOval ball;

	private GRect paddle;

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		addMouseListeners();
		gameInitialization();
		playTheGame();
	}

	/*
	 * With this method we detect the location of the mouse and we relocate
	 * paddle there. But we also have to consider that paddle can't go outside
	 * the window.
	 */
	public void mouseMoved(MouseEvent e) {
		double x = e.getX();
		double y = getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET;
		if (x < 0) {
			x = 0;
		}
		if (x >= WIDTH - PADDLE_WIDTH) {
			x = WIDTH - PADDLE_WIDTH;
		}
		paddle.setLocation(x, y);
	}

	/*
	 * In this method we play the game. First of all we wait for players click,
	 * after that we give speed to the ball and then we will call the animation
	 * method.
	 */
	private void playTheGame() {
		waitForClick();
		int tries = 0;
		vy = 3;
		vx = rgen.nextDouble(1, 3);
		if (rgen.nextBoolean()) {
			vx = -vx;
		}
		ballAnimation(tries);
	}

	/*
	 * This method helps us move the ball. We check that if the ball touches any
	 * of the walls, except the bottom one, we change its direction. If ball
	 * hits bottom wall, we relocate it in the middle of the window and wait for
	 * player's click to resume the game, unless they used all of they tries. If
	 * they hit the bottom wall for three times, they lose the game. In this
	 * method we also have "collisionCheck" method, which is the huge part of
	 * the game.
	 */
	private void ballAnimation(int tries) {
		while (true) {
			if (ball.getY() <= 0) {
				vy = -vy;
			}
			if (ball.getX() + vx + 2 * BALL_RADIUS >= WIDTH || ball.getX() <= 0) {
				vx = -vx;
			}
			if (ball.getY() + vy + 2 * BALL_RADIUS >= getHeight()) {
				resetBall();
				tries++;
				if (tries == NTURNS && numBricks != 0) {
					youLose();
				}
				waitForClick();
			}
			ball.move(vx, vy);
			pause(PAUSE_TIME);
			collisionCheck();
		}

	}

	/*
	 * This method will be called if the player loses the game. After player
	 * hits the bottom wall for three times, we remove all objects and we inform
	 * player that they have lost the game, by creating appropriate label.
	 */
	private void youLose() {
		removeAll();
		GLabel lose = new GLabel("You Lose!");
		lose.setFont("Helvatica-30");
		lose.setColor(Color.blue);
		add(lose, WIDTH / 2 - lose.getWidth() / 2, getHeight() / 2 - lose.getAscent() / 2);
	}

	/*
	 * This method is called if the ball hits the bottom wall. It resets the
	 * ball in its initial position, in the middle of the window, and if player
	 * still has lives, game continues.
	 */
	private void resetBall() {
		ball.setLocation(WIDTH / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
	}

	/*
	 * After "getCollidingObject" method returned us collider, we remove
	 * collider and change direction of the ball if the collider is brick. And
	 * if the collider is the paddle, we just change the direction. We still
	 * have to check that collider is an actual object.
	 */
	private void collisionCheck() {
		GObject collider = getCollidingObject();
		if (collider != null && collider != paddle) {
			remove(collider);
			numBricks--;
			if (numBricks == 0) {
				youWin();
			}
			vy = -vy;
		} else if (collider != null && collider == paddle) {
			vy = -vy;
			ball.move(0, -4);
		}
	}

	/*
	 * This method will be called if the player wins the game. After number of
	 * bricks are 0 and the player still has lives, player has won. So we remove
	 * all objects and print that player has won.
	 */
	private void youWin() {
		removeAll();
		GLabel win = new GLabel("You Win!");
		win.setFont("Helvatica-30");
		win.setColor(Color.red);
		add(win, WIDTH / 2 - win.getWidth() / 2, getHeight() / 2 - win.getAscent() / 2);

	}

	/*
	 * This method returns us colliding object. We check the four coordinates of
	 * the ball, and if any of them is brick or paddle we return collider. For
	 * first 3 points, we only return them if any of them is an actual object.
	 * If all of them are null, we don't return them. However, we return fourth
	 * point collider, whether its an actual object or not.
	 */
	private GObject getCollidingObject() {
		GObject collider = getElementAt(ball.getX(), ball.getY());
		if (collider != null) {
			return collider;
		}
		collider = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		if (collider != null) {
			return collider;
		}
		collider = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		if (collider != null) {
			return collider;
		}
		collider = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		return collider;
	}

	/*
	 * This method helps us initialize game. In this method main components of
	 * the game are created, such as ball, bricks and paddle.
	 */
	private void gameInitialization() {
		createBricks();
		createBall();
		createPaddle();
	}

	/*
	 * This method creates paddle.
	 */
	private void createPaddle() {
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setLocation(WIDTH / 2 - PADDLE_WIDTH / 2, getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET);
		add(paddle);
	}

	/*
	 * This method creates ball and locates it in the middle of the window.
	 */
	private void createBall() {
		ball = new GOval(WIDTH / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}

	/*
	 * This method create 10 rows of bricks. For each row y coordinate is
	 * different. Initial y coordinate is "BRICK_Y_OFFSET". But after first row
	 * y increases by brick's height plus brick's separation.
	 */
	private void createBricks() {
		for (int i = 1; i < NBRICK_ROWS + 1; i++) {
			createRow(i);
		}
	}

	/*
	 * This method creates a row of bricks. Starting coordinates are x and y. X
	 * is increasing by brick's width plus separation. y stays the same since
	 * the row is same.
	 */
	private void createRow(int i) {
		double x = (WIDTH - NBRICKS_PER_ROW * BRICK_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2;
		double y = BRICK_Y_OFFSET + (i - 1) * (BRICK_HEIGHT + BRICK_SEP);
		for (int j = 1; j < NBRICKS_PER_ROW + 1; j++) {
			GRect BRICK = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			Color color = checkColor(i);
			BRICK.setFilled(true);
			BRICK.setColor(color);
			add(BRICK);
			x = x + BRICK_WIDTH + BRICK_SEP;
		}
	}

	/*
	 * This method helps us determine the color of the bricks. It returns the
	 * appropriate color based on i.
	 */
	private Color checkColor(int i) {
		if (i <= NBRICK_ROWS / 5) {
			return Color.red;
		} else if (i <= NBRICK_ROWS * 2 / 5) {
			return Color.orange;
		} else if (i <= NBRICK_ROWS * 3 / 5) {
			return Color.yellow;
		} else if (i <= NBRICK_ROWS * 4 / 5) {
			return Color.green;
		} else {
			return Color.cyan;
		}
	}

}