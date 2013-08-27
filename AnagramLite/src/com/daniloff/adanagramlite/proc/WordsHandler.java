package com.daniloff.adanagramlite.proc;

import java.util.Queue;
import java.util.Random;

import android.content.Context;
import android.util.Log;

import com.daniloff.adanagramlite.AnagramView;
import com.daniloff.adanagramlite.R;

public class WordsHandler {

	private Context context;
	private Queue<String> wordsForLevel;
	private String word;
	private String wordShuffled;
	private AnagramView image;
	private Random rnd = new Random();

	private int level = 1;

	private int step = 1;
	private int attempt = 1;
	private int score;
	private int record;
	private int stepCost;
	private LevelParams params;
	private boolean godMode;
	private final String LOG_TAG = "autor";
	private final int MAX_LEVEL = 10;

	public void start() {
		params = AnagramConstants.LEVEL_PARAMS.get(level);
		wordsForLevel = FileUtils.receiveWords(context, params);
		updateLevelInfo();
		record = image.loadRecord();
		supplyTask();
	}

	private void updateLevelInfo() {
		image.updateTextView(R.id.info_level, "level: " + level);
	}

	public void newTask() {
		attempt = 1;
		stepCost = 0;
		supplyTask();
	}

	private void supplyTask() {
		word = wordsForLevel.poll();
		shuffleChars();
		Log.i(LOG_TAG, word + " => " + wordShuffled);
		image.showTask(wordShuffled);
	}

	private void shuffleChars() {
		char[] chars = word.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			int index = rnd.nextInt(chars.length);
			if ((index != i || index == chars.length - 1) && chars[index] != '%') {
				sb.append(chars[index]);
				chars[index] = '%';
			} else
				i--;
		}
		wordShuffled = sb.toString();
	}

	public void hint(int i) {
		score = score - params.getHintPrice();
		updateScoreInfo();

		countStepCost(params.getHintPrice());
		char c = word.charAt(i - 1);
		image.appendChar(c);
	}

	public void toggleGodMode() {
		if (!godMode) {
			godMode = true;
		} else {
			godMode = false;
		}
		image.updateMode(godMode);
	}

	private void updateScoreInfo() {
		image.updateTextView(R.id.view_score, "score: " + score);
		image.updateScoreColors(score, record);
	}

	public void analyzeAnswer(String answer) {
		if (answer.equals(word)) {
			onCorrectAnswer();
		} else {
			onMistake();
		}
	}

	private void onCorrectAnswer() {
		image.toast("Correct!");
		step++;
		if (step > params.getStepsLimit()) {
			updateLevel();
		}
		score = score + params.getWordPrice();
		if (score > record) {
			record = score;
			image.saveRecord(record);
		}
		updateScoreInfo();
		newTask();
	}

	private void updateLevel() {
		if (level < MAX_LEVEL) {
			level++;
			step = 1;
			image.toast("you passed to level " + level);
			updateLevelInfo();
			start();
		} else {
			image.moveToFinishView();
		}
	}

	private void onMistake() {
		attempt++;
		image.updateTextView(R.id.info_attempt, "attempt: " + attempt + "/" + params.getAttemptLimit());
		if (attempt <= params.getAttemptLimit()) {
			score = score - params.getAttemptPrice();
			updateScoreInfo();
			countStepCost(params.getAttemptPrice());
			image.toast("Try again");
		} else {
			penalty(params.getWordPrice());
			image.toast("The word: " + word);
			newTask();
		}
	}

	public void nextWord() {
		penalty(params.getWordPrice());
		image.toast("The word: " + word);
		newTask();
	}

	private void countStepCost(int cost) {
		stepCost = stepCost + cost;
	}

	public void penalty(int price) {
		int penaltyScore;
		if (stepCost > price) {
			penaltyScore = stepCost;
		} else {
			penaltyScore = price - stepCost;
		}
		score = score - penaltyScore;
		updateScoreInfo();
	}

	public void inputWholeWord() {
		image.updateTextView(R.id.txt_answer, word);
	}

	public void setView(AnagramView image) {
		this.image = image;
	}

	public int getStep() {
		return step;
	}

	public int getAttempt() {
		return attempt;
	}

	public int getScore() {
		return score;
	}

	public int getRecord() {
		return record;
	}

	public int getLevel() {
		return level;
	}

	public LevelParams getParams() {
		return params;
	}

	public void setContext(Context baseContext) {
		context = baseContext;
	}

	public boolean isGodMode() {
		return godMode;
	}

	public void setGodMode(boolean godMode) {
		this.godMode = godMode;
	}

}
