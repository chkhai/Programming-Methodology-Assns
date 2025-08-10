/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * MidpointFindingKarel subclass leaves a beeper on cell which
 * is located in the middle of the first street. If the measure of 
 * the first row is an even number, this subclass places the beeper on 
 * one of the two middle cells. 
*/

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {
	
	public void run() {
		while(leftIsClear()) {
			twoUpOneForward();
		}
		turnRight();
		while(frontIsClear()) {
			move();
		}
		putBeeper();
	}
	
	/*
	 * Gets Karel to move one cell forward and two cells up. This 
	 * method helps us to reach the middle of top row of the world. 
	*/
	private void twoUpOneForward() {
		move();
		turnLeft();
		safeMove();
		safeMove();
		turnRight();
	}

	/*
	 * Guarantees that Karel won't crush by checking whether
	 * front is clear or not.
	*/
	private void safeMove() {
		if(frontIsClear()) {
			move();
		}
	}
}
