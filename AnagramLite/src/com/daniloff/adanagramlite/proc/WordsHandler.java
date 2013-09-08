package com.daniloff.adanagramlite.proc;

import android.content.Context;

import com.daniloff.adanagramlite.AnagramView;

public interface WordsHandler {

	public abstract void startLevel();

	public abstract void hint(int i);

	public abstract void analyzeAnswer(String answer);

	public abstract void nextWord();

	public abstract void inputWholeWord();

	public abstract void setView(AnagramView image);

	public abstract int getStep();

	public abstract int getAttempt();

	public abstract int getScore();

	public abstract int getRecord();

	public abstract LevelParams getParams();

	public abstract void setContext(Context baseContext);

	public abstract boolean isGodMode();

	public abstract void setLang(String lang);

	public abstract void setResumed(boolean resumed);

}