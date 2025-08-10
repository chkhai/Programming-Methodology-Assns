/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * The CheckerboardKarel class solves "draw a checkerboard"
 * problem using beepers. Karel has to place beepers on cells, 
 * which are supposed to be black.
 * Pre: Karel is at 1x1, there are no beepers placed.
 * Post: Karel has drawn checkerboard using beepers.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	
	public void run() {
		turnLeft();
		putBeeper();
		completeLine();
		goBack();
		turnRight();
		while(leftIsClear()) {
			completeLine();
			goBack();
			ascend();
		}
		completeLine();
	}
	
	/*
	 * Moves Karel up by one cell. The direction, which Karel's
	 * facing, doesn't change.
	*/
	private void ascend() {
		turnLeft();
		move();
		turnRight();
	}

	/*
	 * get Karel to return at the start of the row after
	 * coloring the line.
	 * Pre: Karel is at the end of the row, facing east.
	 * Post: Karel is at the start of the row, facing east.
	*/
	private void goBack() {
		turnAround();
		while(frontIsClear()) {
			move();
		}
		turnAround();
	}

	/*
	 * Gets Karel to color the row. Karel is placing beepers by
	 * missing one cell.
	 * Pre: Karel is at the start of the row. Row is not colored.
	 * Post: Karel is at the end of the row. Row is colored.
	*/
	private void completeLine() {
		while(frontIsClear()){
			if(beepersPresent()) {
				move();
				if(frontIsClear()) {
					move();
					putBeeper();
				}
			}else{
				move();
				putBeeper();
				if(frontIsClear()) {
				move();	
				}
			}
		}
	}
}
