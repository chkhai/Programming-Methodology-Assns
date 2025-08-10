
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	private RandomGenerator rgen = RandomGenerator.getInstance();

	private int tries = 8;

	private String base;

	private HangmanCanvas canvas;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
		canvas.reset();
	}

	public void run() {
		HangmanLexicon lexicon = new HangmanLexicon();
		int num = rgen.nextInt(0, lexicon.getWordCount());
		String secretWord = lexicon.getWord(num);
		base = countTheNumOfSymbols(secretWord);
		println("Welcome To Hangman!");
		while (!playerHasWon(secretWord) && !playerHasLost()) {
			playTheGame(secretWord);
		}
		gameIsOver(secretWord);
	}
	
	/*
	 * When while loops stops in run method, player has won a game or lost.
	 * So after that we call gameIsOver method.
	 */
	private void gameIsOver(String secretWord) {
		if (playerHasLost()) {
			youLose(secretWord);
		} else {
			youWin(secretWord);
		}
	}

	/*
	 * If player wins, we call this method, which helps us inform player
	 * that they have won.
	 */
	private void youWin(String secretWord) {
		println("You win. Congrats!");
		println("The word was " + secretWord);
	}
	
	/*
	 * If player loses, we call this method, which helps us inform player
	 * that they've lost.
	 */
	private void youLose(String secretWord) {
		println("You're completely hung.");
		println("The word was " + secretWord);

	}
	
	/*
	 * This method determines whether player has lost or not. If the number 
	 * of tries left is zero, then player's lost.
	 */
	private boolean playerHasLost() {
		if (tries == 0) {
			return true;
		}
		return false;
	}

	/*
	 * This method helps us play the game. Firstly, we print instructions and
	 * information about the player's game process. Then we check if players
	 * guess is right.
	 */
	private void playTheGame(String secretWord) {
		println("Your word looks like this " + base);
		println("You have " + tries + " guesses left.");
		String guess = readLine("Your guess: ");
		guess = guess.toUpperCase();
		checkTheInput(guess);
		base = checkTheGuess(guess, secretWord);
	}

	/*
	 * This method helps us check the input. If input's length is bigger than
	 * one, or if the input isn't a letter, we print appropriate message and
	 * tell the player to change the guess.
	 */
	private void checkTheInput(String guess) {
		while (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
			println("You must input a letter!");
			guess = readLine("Your guess: ");
		}
	}

	/*
	 * This method helps us determine whether if players guess was right or
	 * wrong. If the guess was right base word will be changed and it will also
	 * appear on canvas. If not, tries decreases by one and another part of the body
	 * will be added.
	 */
	private String checkTheGuess(String guess, String secretWord) {
		int index = secretWord.indexOf(guess.charAt(0));
		if (index != -1) {
			println("The guess is correct.");
			for (int i = 0; i < secretWord.length(); i++) {
				if (guess.charAt(0) == secretWord.charAt(i)) {
					base = base.substring(0, i) + guess + base.substring(i + 1);
					canvas.displayWord(base);
				}
			}
		} else {
			println("There are no " + guess.charAt(0) + "'s in the word.");
			tries--;
			canvas.noteIncorrectGuess(guess.charAt(0));
		}

		return base;
	}

	/*
	 * This method counts the length of secret word and returns base, which is
	 * made of hyphens.
	 */
	private String countTheNumOfSymbols(String secretWord) {
		String hyphen = "-";
		String base = "";
		for (int i = 0; i < secretWord.length(); i++) {
			base += hyphen;
		}
		return base;
	}

	/*
	 * This method returns boolean whether player has won the game or not.
	 */
	private boolean playerHasWon(String secretWord) {
		if (base.equals(secretWord)) {
			return true;
		}
		return false;
	}
}
