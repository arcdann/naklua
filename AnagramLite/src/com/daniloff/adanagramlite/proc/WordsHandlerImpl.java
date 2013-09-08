package com.daniloff.adanagramlite.proc;

import java.util.Queue;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.daniloff.adanagramlite.AnagramView;
import com.daniloff.adanagramlite.GlobalInvoke;
import com.daniloff.adanagramlite.R;

public class WordsHandlerImpl implements WordsHandler {

	private final String LOG_TAG = "autor";
	private final int MAX_LEVEL = 10;
	private static final String SETTINGS_FILENAME = "gamestate";

	private Context context;
	private Queue<String> wordsForLevel;
	private String word;
	private String wordShuffled;
	private AnagramView image;
	private Random rnd = new Random();

	private int level = 1;
	private int hintRemain;
	private int step = 1;
	private int attempt = 1;
	private int score;
	private int record;
	private int stepCost;
	private LevelParams params;
	private boolean godMode;
	private boolean resumed;
	private String lang;

	@Override
	public void startLevel() {

		record = GlobalInvoke.paramsHandler.loadParamInt("PARAM_NAME_RECORD");
		if (resumed) {
			lang = loadParams("PARAM_NAME_LANG");
			level = GlobalInvoke.paramsHandler.loadParamInt("PARAM_NAME_LEVEL");
			if (level == 0)
				level = 1;
			step = GlobalInvoke.paramsHandler.loadParamInt("PARAM_NAME_STEP");
			score = GlobalInvoke.paramsHandler.loadParamInt("PARAM_NAME_SCORE");
		}

		if (lang.equals("ru")) {
			params = AnagramConstants.LEVEL_PARAMS_RU.get(level);
		} else {
			params = AnagramConstants.LEVEL_PARAMS_EN.get(level);
		}

		GlobalInvoke.paramsHandler.saveParamInt("PARAM_NAME_LEVEL", level);
		wordsForLevel = FileUtils.receiveWords(context, params);

		image.updateTextView(R.id.info_level, "level: " + level);
		updateScoreInfo();

		supplyTask();
	}

	private void newTask() {
		GlobalInvoke.paramsHandler.saveParamInt("PARAM_NAME_STEP", step);
		GlobalInvoke.paramsHandler.saveParamInt("PARAM_NAME_SCORE", score);
		attempt = 1;
		stepCost = 0;
		supplyTask();
	}

	private void supplyTask() {
		word = wordsForLevel.poll();
		shuffleChars();
		Log.i(LOG_TAG, word + " => " + wordShuffled);
		image.showTask(wordShuffled);
		hintRemain = params.getHintLimit();
		image.updateTextView(R.id.button_hint, "Hint (" + hintRemain + ")");
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

	@Override
	public void hint(int i) {
		hintRemain--;
		image.updateTextView(R.id.button_hint, "Hint (" + hintRemain + ")");
		if (hintRemain == 0) {
			image.setEnable(R.id.button_hint, false);
		}
		score = score - params.getHintPrice();
		updateScoreInfo();

		countStepCost(params.getHintPrice());
		char c = word.charAt(i - 1);
		image.appendChar(c);
	}

	private void toggleGodMode() {
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

	@Override
	public void analyzeAnswer(String answer) {
		if (answer.equals(word)) {
			onCorrectAnswer();
		} else {
			if (answer.substring(0, 3).equalsIgnoreCase("$$$")) {
				toggleGodMode();
			} else {
				onMistake();
			}
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
			GlobalInvoke.paramsHandler.saveParamInt("PARAM_NAME_RECORD", record);
		}
		updateScoreInfo();
		newTask();
	}

	private void updateLevel() {
		if (level < MAX_LEVEL) {
			level++;
			step = 1;
			image.toast("you passed to level " + level);
			GlobalInvoke.paramsHandler.saveParamInt("PARAM_NAME_LEVEL", level);
			startLevel();
		} else {
			GlobalInvoke.paramsHandler.saveParamBoolean("PARAM_NAME_RESUMING", false);
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

	@Override
	public void nextWord() {
		penalty(params.getWordPrice());
		image.toast("The word: " + word);
		newTask();
	}

	private void countStepCost(int cost) {
		stepCost = stepCost + cost;
	}

	private void penalty(int price) {
		int penaltyScore;
		if (stepCost > price) {
			penaltyScore = stepCost;
		} else {
			penaltyScore = price - stepCost;
		}
		score = score - penaltyScore;
		updateScoreInfo();
	}

	@Override
	public void inputWholeWord() {
		image.updateTextView(R.id.txt_answer, word);
	}

	private String loadParams(String paramName) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
		return settings.getString(paramName, "");
	}

	@Override
	public void setView(AnagramView image) {
		this.image = image;
	}

	@Override
	public int getStep() {
		return step;
	}

	@Override
	public int getAttempt() {
		return attempt;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getRecord() {
		return record;
	}

	@Override
	public LevelParams getParams() {
		return params;
	}

	@Override
	public void setContext(Context baseContext) {
		context = baseContext;
	}

	@Override
	public boolean isGodMode() {
		return godMode;
	}

	@Override
	public void setLang(String lang) {
		this.lang = lang;
		GlobalInvoke.paramsHandler.saveParamString("PARAM_NAME_LANG", lang);
	}

	@Override
	public void setResumed(boolean resumed) {
		this.resumed = resumed;
	}

}