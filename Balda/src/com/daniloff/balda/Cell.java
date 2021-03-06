package com.daniloff.balda;

public class Cell {
	
	private boolean filled;
	private boolean fillable;
	private boolean chosen;
	private boolean justChosen;
	private boolean required;
	
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
	public boolean isChosen() {
		return chosen;
	}
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}
	public boolean isJustChosen() {
		return justChosen;
	}
	public void setJustChosen(boolean justChosen) {
		this.justChosen = justChosen;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}

}
