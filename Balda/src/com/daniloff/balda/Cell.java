package com.daniloff.balda;

public class Cell {
	
	private boolean filled;
	private boolean fillable;
	private String letter;
	
	public boolean isFilled() {
		return filled;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public boolean isFillable() {
		return fillable;
	}
	public void setFillable(boolean fillable) {
		this.fillable = fillable;
	}
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

}
