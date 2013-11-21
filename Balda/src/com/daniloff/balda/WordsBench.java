package com.daniloff.balda;

import java.util.List;
import java.util.Random;

public class WordsBench {

	MainActivity sheet;
	String[][] letters;
	Cell[][] matrix;
	private final int X = 5;
	private final int Y = 5;
	private Random rnd = new Random();
	private List<String> playedWords;

	public void startGame() {
		matrix = createMatrix(X, Y);
		String initString = "укенгшвапролдсмитьб";
		createTask(initString);

		// sheet.showTask();
		// startRound();
	}

	private void startRound() {
		// TODO Auto-generated method stub

	}

	private Cell[][] createMatrix(int X, int Y) {
		Cell[][] retArray = new Cell[X][Y];
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				retArray[x][y] = new Cell();
			}
		}
		return retArray;
	}

	private void createTask(String initStr) {
		int y = Y / 2;
		for (int x = 0; x < X; x++) {
			// matrix[x][y]=initStr.substring(x, x+1);
			int i = rnd.nextInt(initStr.length());
			matrix[x][y].setLetter(initStr.substring(i, i + 1));
			matrix[x][y].setFilled(true);
			matrix[x][y].setFillable(false);
			setFillabilityAround(x, y);
		}

	}

	public void setFillabilityAround(int xFilled, int yFilled) {
		int x, y;
		x = xFilled - 1;
		if (x >= 0 && !matrix[x][yFilled].isFilled())
			matrix[x][yFilled].setFillable(true);
		x = xFilled + 1;
		if (x < X && !matrix[x][yFilled].isFilled())
			matrix[x][yFilled].setFillable(true);
		y = yFilled - 1;
		if (y >= 0 && !matrix[xFilled][y].isFilled())
			matrix[xFilled][y].setFillable(true);
		y = yFilled + 1;
		if (y < Y && !matrix[xFilled][y].isFilled())
			matrix[xFilled][y].setFillable(true);
	}

	public void setSheet(MainActivity sheet) {
		this.sheet = sheet;
	}
}
