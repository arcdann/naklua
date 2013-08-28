package com.daniloff.adanagramlite;

public interface AnagramView {
	
	void showTask(String word);

	void appendChar(char c);
	
	void toast(String message);

	void updateTextView(int viewID, String text);

	void updateScoreColors(int score, int record);

	void updateMode(boolean godMode);

	public abstract int loadParams(String paramName);

	public abstract void saveParams(String paramName, int paramValue);

	public abstract void moveToFinishView();
	
}
