package com.daniloff.balda;

import java.util.List;

public class Player {
	
	private String playerName;
	private int score;
	private List<String>words;
	private boolean active;
	
	public String getGamerName() {
		return playerName;
	}
	public void setGamerName(String gamerName) {
		this.playerName = gamerName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
