package com.daniloff.balda;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordsBench {

	MainActivity sheet;
	String[][] letters;
	Cell[][] cells;
	private final int X = 8;
	private final int Y = 6;
	private Random rnd = new Random();
	private List<String> playedWords;
	private List<String>wordLetters;
	
	private boolean putLetter;
	private boolean wordDeclare;

	public void startGame() {
		cells = createMatrix(X, Y);
		String initString = "укенгшвапролдсмитб";
		createTask(initString);
		
		putLetter=true;
		wordDeclare=false;

		// sheet.showTask();
		// startRound();
	}

	public void startRound() {
		for(int y=0;y<Y;y++){
			for(int x=0;x<X;x++){
				cells[x][y].setChosen(false);
				cells[x][y].setJustChosen(false);
				cells[x][y].setRequired(false);
			}
		}
	
		putLetter=true;
		wordDeclare=false;
		
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
			cells[x][y].setLetter(initStr.substring(i, i + 1));
			cells[x][y].setFilled(true);
			cells[x][y].setFillable(false);
			setFillabilityAround(x, y);
		}

	}

	public void setFillabilityAround(int xFilled, int yFilled) {
		int x, y;
		x = xFilled - 1;
		if (x >= 0 && !cells[x][yFilled].isFilled())
			cells[x][yFilled].setFillable(true);
		x = xFilled + 1;
		if (x < X && !cells[x][yFilled].isFilled())
			cells[x][yFilled].setFillable(true);
		y = yFilled - 1;
		if (y >= 0 && !cells[xFilled][y].isFilled())
			cells[xFilled][y].setFillable(true);
		y = yFilled + 1;
		if (y < Y && !cells[xFilled][y].isFilled())
			cells[xFilled][y].setFillable(true);
	}
	
	public void createWord(){
		setPutLetter(false);
		setWordDeclare(true);
		wordLetters=new ArrayList<String>();
	}

	public void setSheet(MainActivity sheet) {
		this.sheet = sheet;
	}

	public boolean isPutLetter() {
		return putLetter;
	}

	public void setPutLetter(boolean putLetter) {
		this.putLetter = putLetter;
	}

	public boolean isWordDeclare() {
		return wordDeclare;
	}

	public void setWordDeclare(boolean wordDeclare) {
		this.wordDeclare = wordDeclare;
	}

	public List<String> getWordLetters() {
		return wordLetters;
	}
}
