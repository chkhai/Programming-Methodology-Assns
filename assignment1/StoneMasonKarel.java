/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass solves "repair the quad"
 * problem. Karel is supposed to repair arches which were damaged
 * because of earthquake. Karel has to put bricks(beepers) in empty spaces
 * on lines such as 1,5,9,13 etc.
 * Pre: Karel is at 1x1, the arches are damaged.
 * Post: Karel is at the end of the street, all the arches are fixed.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	
	public void run() {
		while(frontIsClear()) {
			repairColumnAndGoBack();
			for(int i=0; i<4; i++) {
				move();
			}
		}
		repairColumnAndGoBack();
	}

	/*
	 * Gets Karel to place the bricks on empty cells and after
	 * completing the line Karel returns to previous postion, where 
	 * he was until this method.
	 * Pre: Karel is facing east, the column where Karel is located isn't fixed.
	 * Post: Karel is on the same cell, facing east, the arch is fixed.
	*/
	private void repairColumnAndGoBack() {
		turnLeft();
		while(frontIsClear()) {
			if(noBeepersPresent()) {
				putBeeper();
			}
			move();
		}
		if(noBeepersPresent()) {
			putBeeper();
		}
		turnAround();
		while(frontIsClear()) {
			move();
		}
		turnLeft();
	}
}
