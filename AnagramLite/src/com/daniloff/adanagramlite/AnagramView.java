package com.daniloff.adanagramlite;

public interface AnagramView {

	void showTask(String word);

	void appendChar(char c);

	void toast(String message);

	void updateTextView(int viewID, String text);

	void setEnable(int buttonID, boolean b);

	void updateScoreColors(int score, int record);

	void updateMode(boolean godMode);

	public abstract void moveToFinishView();

	void simulateButtonPress(int firstWrongLetterIndex, int pressableTaskButtonIndex);

	void closeMagicTextView();
	
	void showAd();

}
