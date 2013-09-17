package com.daniloff.adanagramlite;

public interface AnagramView {

	void showTask(String word);

	void toast(String message);

	void updateTextView(int viewID, String text);

	void updateScoreColors(int score, int record);

	void updateMode(boolean magicMode);

	public abstract void moveToFinishView();

	void simulateAnswerButtonPress(int firstWrongLetterIndex);

	void simulateTaskButtonPress(int pressableTaskButtonIndex);

	void closeMagicTextView();

	void showAd();

	boolean[] getTaskButtonVisibility();

}
