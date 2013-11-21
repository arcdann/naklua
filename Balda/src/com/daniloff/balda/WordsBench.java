package com.daniloff.balda;

import java.util.List;
import java.util.Random;

public class WordsBench {
	
	MainActivity sheet;
	String[][] letters;
	Cell[][]matrix;
	private final int X=5;
	private final int Y=5;
	private Random rnd=new Random();
	private List<String>playedWords;


	public void start() {
		matrix=createMatrix(X,Y);
		String initString="укенгшвапролдсмитьб";
		createTask(initString);
		
		
	}


	private Cell[][] createMatrix(int X,int Y) {
		Cell[][]retArray=new Cell[X][Y];
		for (int y=0;y<Y;y++){
			for(int x=0;x<X;x++){
				retArray[x][y]=new Cell();
			}
		}
		return retArray;
	}
	
	private void createTask(String initStr) {
		int y=Y/2;
		for(int x=0;x<X;x++){
//			matrix[x][y]=initStr.substring(x, x+1);
			int i=rnd.nextInt(initStr.length());
			matrix[x][y].setLetter(initStr.substring(i, i+1));
		}
		
	}

	public void setSheet(MainActivity sheet) {
		this.sheet = sheet;
	}
}
