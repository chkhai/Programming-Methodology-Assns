
/*
 * File: Hailstone.java
 * Name: Luka Chkhaidze
 * Section Leader: Giorgi Tkeshelashvili
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.ConsoleProgram;

public class Hailstone extends ConsoleProgram {

	public void run() {
		int n = readInt("Enter integer: ");
		int count = 0;
		count = FromNtoOne(n, count);
		print("The process took " + count + " to reach 1.");
	}

	/*
	 * This method helps us to reach one from n. If n is odd, we multiply it by
	 * three and add one. The new n is 3*n+1. If n is even, we divide it by two
	 * and new n is n/2. In both cases count increases by one. When n becomes
	 * one, the method stops.
	 * 
	 */
	private int FromNtoOne(int n, int count) {
		while (true) {
			if (n % 2 == 0) {
				println(n + " is even so I take half: " + n / 2);
				n = n / 2;
				count++;
			} else {
				println(n + " is odd, so I make 3n+1:" + (3 * n + 1));
				n = 3 * n + 1;
				count++;
			}
			if (n == 1) {
				return count;
			}
		}
	}
}
