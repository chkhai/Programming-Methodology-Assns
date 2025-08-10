/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * This subclass solves "collect newspaper" problem. 
 * Karel is supposed to leave the house, collect the newspaper
 * and go back inside on the initial position.
 * Pre: Karel is at 3x4, facing east, newspaper(beeper) is at 6x3.
 * Post: Karel has collected newspaper and went back home. Karel's at 
 * 3x4, facing east.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	public void run() {
		moveToNewspaper();
		pickAndRotate();
		goBack();
	}

	/*
	 * This method gets Karel back inside to its initial 
	 * position.  
	 * Pre: Karel is at 6x3, facing west.
	 * Post: Karel is at 3x4, facing east.
	 */	
	private void goBack() {
		move();
		turnRight();
		move();
		turnLeft();
		while(frontIsClear()) {
			move();
		}
		turnAround();
	}

	/*
	 * Gets Karel to collect newspaper and rotate.
	 * Pre: Karel is at 6x3, facing east. Beeper is at 6x3. 
	 * Post: Karel has picked up beeper and is facing east.
	*/
	private void pickAndRotate() {
		pickBeeper();
		turnAround();
	}

	/*
	 * Gets Karel to leave the house and arrive on the cell
	 * where newspaper(beeper) is presented.
	 * Pre: Karel is at 3x4, facing east.
	 * Post: Karel is at 6x3, facing east. 
	*/
	private void moveToNewspaper() {
		while(frontIsClear()) {
			move();
		}
		turnRight();
		move();
		turnLeft();
		move();
	}
}
