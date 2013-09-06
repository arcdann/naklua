package com.daniloff.adanagramlite;

public interface AnagramView {

	void showTask(String word);

	void appendChar(char c);

	void toast(String message);

	void updateTextView(int viewID, String text);

	void updateScoreColors(int score, int record);

	void updateMode(boolean godMode);

	public abstract void moveToFinishView();

}
