
/*
 * File: PythagoreanTheorem.java
 * Name: Luka Chkhaidze
 * Section Leader: Giorgi Tkeshelashvili
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.ConsoleProgram;

public class PythagoreanTheorem extends ConsoleProgram {

	public void run() {
		int a = readInt("Enter integer: ");
		int b = readInt("Enter integer: ");
		double c = calculatePythagoreanTheorem(a, b);
		println("c=" + c);
	}

	/*
	 * This method helps us compute pythagoreanTheorem. Firstly it calculates
	 * the sum of a^2 and b^2 and then it takes square root of it.
	 */
	private double calculatePythagoreanTheorem(int a, int b) {
		double y = Math.sqrt(a * a + b * b);
		return y;
	}
}
