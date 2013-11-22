package com.daniloff.balda;

public class ConverterXYID {

	private final int ID_PREFIX = 499;

	public int convertToTablerowID(int y) {
		return ID_PREFIX * 1000 + 100 + y;
	}

	public int convertToID(int x, int y) {
		int trID = convertToTablerowID(y);
		return trID * 1000 + 100 + x;
	}
	
	public int convertToX(int id){
		return id % (id / 100);
	}

	public int convertToY(int id){
		return id % (id / 100000) / 1000;
	}
}
