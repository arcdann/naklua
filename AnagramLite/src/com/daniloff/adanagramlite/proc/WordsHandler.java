package com.daniloff.adanagramlite.proc;

import android.content.Context;

import com.daniloff.adanagramlite.AnagramView;

public interface WordsHandler {

	void startLevel();

	void hint(String askHint, boolean[] taskButtonsVisibility);

	void analyzeAnswer(String answer);

	void nextWord();

	void inputWholeWord();

	// ///////////////////////
	void setView(AnagramView image);

	void setContext(Context baseContext);

	// /////////////////////
	void setLang(String lang);// enum

	void setResumed(boolean resumed);

	// //////////////////////
	LevelParams getParams();

	boolean isMagicMode();

	int getStep();

	int getAttempt();

	int getScore();

	int getRecord();

	void toggleMagicMode();

}