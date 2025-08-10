
/*
 * File: FindRange.java
 * Name: Luka Chkhaidze
 * Section Leader: Giorgi Tkeshelashvili
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.ConsoleProgram;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEL = 0;

	public void run() {
		println("This program finds smallest and largest numbers. ");
		int firstInteger = readInt("? ");
		if(firstInteger == SENTINEL){
			println("The first number can't be " + SENTINEL);
		}
		int max = firstInteger;
		int min = firstInteger;
		while(true){
			int otherInteger = readInt("? ");
			if (otherInteger == SENTINEL){
				break;
			}
			max = compareMaxAndN(max, otherInteger);
			min = compareMinAndN(min, otherInteger);
		}
		println("largest: " + max);
		println("smallest: " + min);
	}

	/*
	 * This method helps us compare two integers. After comparing them, it keeps
	 * the track of the smallest one.
	 */
	private int compareMinAndN(int min, int otherInteger) {
		if (otherInteger < min) {
			min = otherInteger;
		}
		return min;
	}

	/*
	 * This method helps us compare two integers. After comparing them, it keeps
	 * the track of the biggest one.
	 */
	private int compareMaxAndN(int max, int otherInteger) {
		if (otherInteger > max) {
			max = otherInteger;
		}
		return max;
	}
}