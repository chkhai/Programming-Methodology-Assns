
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Arrays;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		while (nPlayers > 4) {
			nPlayers = dialog.readInt("Number of players can't be higher"
					+ " than four. Enter number of players!");
		}
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playersSelectedCategories = new boolean[nPlayers + 1][N_CATEGORIES + 1];
		playerScoresInEachCategory = new int[nPlayers + 1][N_CATEGORIES + 1];
		totalScores = new int[nPlayers + 1];
		playGame();
	}

	private void playGame() {
		for (int i = 0; i < 13; i++) {
			for (int j = 1; j < nPlayers + 1; j++) {
				display.printMessage(playerNames[j - 1] + "'s turn!");
				makeMove(j);
			}
		}
		sumUpTheScores();
		detectTheWinner();
	}

	/*
	 * After playing 13 rounds and calculating scores, this method helps us
	 * detect the winner by checking the highest element of totalScores array.
	 * After detecting winner we print appropriate message to inform players who
	 * won. If some of the players have same maximum points in the end of the
	 * game, then game has ended in a draw and we inform players.
	 */
	private void detectTheWinner() {
		int max = totalScores[0];
		for (int i = 0; i < totalScores.length; i++) {
			if (totalScores[i] > max) {
				max = totalScores[i];
			}
		}
		for (int j = 0; j < totalScores.length; j++) {
			if (totalScores[j] == max) {
				indexOfMaxTotal = j;
			}
		}
		for (int k = totalScores.length - 1; k >= 0; k--) {
			if (totalScores[k] == max && k != indexOfMaxTotal) {
				gameEndedDraw();
			} else {
				someoneWon();
			}
		}
	}

	/*
	 * This method is called when the game is over and someone has won.
	 */
	private void someoneWon() {
		display.printMessage("Congratulations, " + playerNames[indexOfMaxTotal - 1]
				+ ", you're the winner with a total score of " + totalScores[indexOfMaxTotal] + "!");
	}

	/*
	 * This method is called when the game is over and it ended in a draw.
	 */
	private void gameEndedDraw() {
		display.printMessage("The game ended in a draw!");

	}

	/*
	 * This method helps us sum up each players' upper and lower score.
	 */
	private void sumUpTheScores() {
		for (int i = 1; i < nPlayers + 1; i++) {
			sumLowerAndUpperScore(i);
		}
	}

	/*
	 * In this method we calculate upper and lower scores. We also add 35 bonus
	 * points if upper score is higher than 63. After calculating scores, we
	 * update score card.
	 */
	private void sumLowerAndUpperScore(int i) {
		int upperScore = 0;
		int lowerScore = 0;
		int upperBonus = 0;
		for (int j = ONES; j < SIXES + 1; j++) {
			upperScore += playerScoresInEachCategory[i][j];
		}
		for (int j = THREE_OF_A_KIND; j < CHANCE + 1; j++) {
			lowerScore += playerScoresInEachCategory[i][j];
		}
		if (upperScore > 63) {
			display.updateScorecard(UPPER_BONUS, i, 35);
			playerScoresInEachCategory[i][UPPER_BONUS] = 35;
			upperBonus = 35;
			
		} else {
			display.updateScorecard(UPPER_BONUS, i, 0);
		}
		display.updateScorecard(UPPER_SCORE, i, upperScore);
		display.updateScorecard(LOWER_SCORE, i, lowerScore);
		totalScores[i] = lowerScore + upperScore + upperBonus;
		display.updateScorecard(TOTAL, i, totalScores[i]);

	}

	/*
	 * This method is called thirteen times for each player. First of all, we
	 * wait for player to roll the dice. Then we generate random numbers for
	 * dice, we display it on canvas. Player also gets to re-roll his dice, and
	 * after re-rolling it two times, he has to pick a category. When player's
	 * picking category, we have to make sure that his preferred category hasn't
	 * been chosen yet. If player chooses already chosen category, we tell him
	 * to pick another.
	 */
	private void makeMove(int j) {
		display.waitForPlayerToClickRoll(j);
		generateDice();
		display.displayDice(dice);
		reroll();
		display.printMessage("Select a category for this roll!");
		category = display.waitForPlayerToSelectCategory();
		while (true) {
			if (!playersSelectedCategories[j][category]) {
				countTheScore(category, j);
				break;
			} else {
				display.printMessage("That category is already selected, please choose another!");
				category = display.waitForPlayerToSelectCategory();
				if (!playersSelectedCategories[j][category]) {
					countTheScore(category, j);
					break;
				}
			}
		}
	}

	/*
	 * This method helps us count the score after player has picked category. If
	 * player picks category between ones and sixes, we sum same dice as
	 * categories to calculate the score. For CHANCE, THREE_OF_A_KIND and
	 * FOUR_OF_A_KIND, we just sum up all dice. And for other categories, we
	 * already know what the score is going to be if player picked it right.
	 * However, if player's dice do not match the combination of the category,
	 * player gets 0 score. Finally, we update score card and players' points in
	 * each category.
	 */
	private void countTheScore(int category, int j) {
		int score = 0;
		if (checkCategory(dice, category)) {
			playersSelectedCategories[j][category] = true;
			if (category <= SIXES && category >= ONES) {
				for (int i = 0; i < N_DICE; i++) {
					if (dice[i] == category) {
						score += category;
					}
				}
			} else if (category == CHANCE || category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND) {
				for (int i = 0; i < N_DICE; i++) {
					score += dice[i];
				}
			} else if (category == FULL_HOUSE) {
				score = 25;
			} else if (category == SMALL_STRAIGHT) {
				score = 30;
			} else if (category == LARGE_STRAIGHT) {
				score = 40;
			} else if (category == YAHTZEE) {
				score = 50;
			}
		} else {
			score = 0;
		}

		playerScoresInEachCategory[j][category] = score;
		display.updateScorecard(category, j, score);
		int total = backUpTotalScore(score, j);
		display.updateScorecard(TOTAL, j, total);
	}

	/*
	 * This method keeps the track of each player's total score.
	 */
	private int backUpTotalScore(int score, int j) {
		totalScores[j] += score;
		return totalScores[j];
	}

	/*
	 * This method re-rolls dice, we print message and wait for player to select
	 * the dice he wishes to re-roll. After that we generate new random numbers
	 * from 1 to 6.
	 */
	private void reroll() {
		for (int i = 0; i < 2; i++) {
			display.printMessage("Select the dice you wish to re-roll and click" + " \"Roll Again\"!");
			display.waitForPlayerToSelectDice();
			for (int k = 0; k < N_DICE; k++) {
				if (display.isDieSelected(k)) {
					dice[k] = rgen.nextInt(1, 6);
				}
			}
			display.displayDice(dice);
		}
	}

	/*
	 * This method generates 5 random numbers from 1 to 6.
	 */
	private void generateDice() {
		for (int i = 0; i < N_DICE; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
	}

	/*
	 * This method is the main part of the game. We check if player's picked
	 * category matches his dice and we return true or false. If category is
	 * from one to sixes or chance this method always returns true. And for
	 * other categories we created array to keep the track of numbers. If
	 * category is YAHTZEE every number should be the same. If category is LARGE
	 * STRAIGHT, we have two options: 1,2,3,4,5 or 2,3,4,5,6. For SMALL STRAIGHT
	 * there is three options: 1,2,3,4; 2,3,4,5 or 3,4,5,6. If category is THREE
	 * OF A KIND one of the array list size must be equal or bigger than three.
	 * Similarly for FOUR OF A KIND, size must be equal or bigger than four. If
	 * category is FULL HOUSE, dice must consist of three same numbers and two
	 * another. We check every FULL HOUSE combination an if it dice matches any
	 * of them, we return truth.
	 */
	private boolean checkCategory(int[] dice, int category) {
		boolean rightCategory = false;
		if (category <= SIXES && category >= ONES || category == CHANCE) {
			rightCategory = true;
		} else {
			int[] numbersList = new int[N_DICE + 2];
			for (int i = 0; i < N_DICE; i++) {
				if (dice[i] == ONES) {
					numbersList[1] += 1;
				} else if (dice[i] == TWOS) {
					numbersList[2] += 1;
				} else if (dice[i] == THREES) {
					numbersList[3] += 1;
				} else if (dice[i] == FOURS) {
					numbersList[4] += 1;
				} else if (dice[i] == FIVES) {
					numbersList[5] += 1;
				} else if (dice[i] == SIXES) {
					numbersList[6] += 1;
				}
			}
			if (category == YAHTZEE) {
				if (numbersList[1] == 5 || numbersList[2] == 5 || numbersList[3] == 5 || numbersList[4] == 5
						|| numbersList[5] == 5 || numbersList[6] == 5) {
					rightCategory = true;
				}
			} else if (category == LARGE_STRAIGHT) {
				if (numbersList[1] == 1 && numbersList[2] == 1 && numbersList[3] == 1 && numbersList[4] == 1
						&& numbersList[5] == 1) {
					rightCategory = true;
				} else if (numbersList[2] == 1 && numbersList[3] == 1 && numbersList[4] == 1 && numbersList[5] == 1
						&& numbersList[6] == 1) {
					rightCategory = true;
				}
			} else if (category == THREE_OF_A_KIND) {
				if (numbersList[1] >= 3 || numbersList[2] >= 3 || numbersList[3] >= 3 || numbersList[4] >= 3
						|| numbersList[5] >= 3 || numbersList[6] >= 3) {
					rightCategory = true;
				}
			} else if (category == FOUR_OF_A_KIND) {
				if (numbersList[1] >= 4 || numbersList[2] >= 4 || numbersList[3] >= 4 || numbersList[4] >= 4
						|| numbersList[5] >= 4 || numbersList[6] >= 4) {
					rightCategory = true;
				}
			} else if (category == SMALL_STRAIGHT) {
				if (numbersList[1] >= 1 && numbersList[2] >= 1 && numbersList[3] >= 1 && numbersList[4] >= 1) {
					rightCategory = true;
				} else if (numbersList[2] >= 1 && numbersList[3] >= 1 && numbersList[4] >= 1 && numbersList[5] >= 1) {
					rightCategory = true;
				} else if (numbersList[3] >= 1 && numbersList[4] >= 1 && numbersList[5] >= 1 && numbersList[6] >= 1) {
					rightCategory = true;
				}
			} else if (category == FULL_HOUSE) {
				if (numbersList[1] == 3) {
					if (numbersList[2] == 2 || numbersList[3] == 2 || numbersList[4] == 2 || numbersList[5] == 2
							|| numbersList[6] == 2) {
						rightCategory = true;
					}
				} else if (numbersList[2] == 3) {
					if (numbersList[1] == 2 || numbersList[3] == 2 || numbersList[4] == 2 || numbersList[5] == 2
							|| numbersList[6] == 2) {
						rightCategory = true;
					}
				} else if (numbersList[3] == 3) {
					if (numbersList[1] == 2 || numbersList[2] == 2 || numbersList[4] == 2 || numbersList[5] == 2
							|| numbersList[6] == 2) {
						rightCategory = true;
					}
				} else if (numbersList[4] == 3) {
					if (numbersList[1] == 2 || numbersList[2] == 2 || numbersList[3] == 2 || numbersList[5] == 2
							|| numbersList[6] == 2) {
						rightCategory = true;
					}
				} else if (numbersList[5] == 3) {
					if (numbersList[1] == 2 || numbersList[2] == 2 || numbersList[3] == 2 || numbersList[4] == 2
							|| numbersList[6] == 2) {
						rightCategory = true;
					}
				} else if (numbersList[6] == 3) {
					if (numbersList[1] == 2 || numbersList[2] == 2 || numbersList[3] == 2 || numbersList[4] == 2
							|| numbersList[5] == 2) {
						rightCategory = true;
					}
				}
			}
		}
		return rightCategory;
	}

	/* Private instance variables */
	int[] dice = new int[N_DICE];
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int category;
	private boolean[][] playersSelectedCategories;
	private int[][] playerScoresInEachCategory;
	private int[] totalScores;
	private int indexOfMaxTotal;
}
