package com.daniloff.adanagramlite.proc;

import java.util.Queue;
import java.util.Random;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.daniloff.adanagramlite.AnagramView;
import com.daniloff.adanagramlite.GlobalInvoke;
import com.daniloff.adanagramlite.R;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class WordsHandlerImpl implements WordsHandler {

	private final String LOG_TAG = "autor";
	private final int MAX_LEVEL = 10;

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
	private boolean magicMode;
	private boolean resumed;
	private String lang;
	private CountDownTimer delayTimer;
	private int delayKey;

	@Override
	public void startLevel() {

		record = GlobalInvoke.paramsHandler.loadParamInt("PARAM_NAME_RECORD");
		if (resumed) {
			lang = GlobalInvoke.paramsHandler.loadParamString("PARAM_NAME_LANG");
			level = GlobalInvoke.paramsHandler.loadParamInt("PARAM_NAME_LEVEL");
			if (level == 0)
				level = 1;
			step = GlobalInvoke.paramsHandler.loadParamInt("PARAM_NAME_STEP");
			score = GlobalInvoke.paramsHandler.loadParamInt("PARAM_NAME_SCORE");
			resumed = false;
		}

		if (lang.equals("ru")) {
			params = AnagramConstants.LEVEL_PARAMS_RU.get(level);
		} else {
			params = AnagramConstants.LEVEL_PARAMS_EN.get(level);
		}

		GlobalInvoke.paramsHandler.saveParamInt("PARAM_NAME_LEVEL", level);
		wordsForLevel = FileUtils.receiveWords(context, params);

		image.updateTextView(R.id.info_level, context.getString(R.string.level) + ": " + level);
		image.defineButtonsSize(params.getWordLength());
		updateScoreInfo();
		
		createDelayTimer();

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
		wordShuffled = shuffleChars(word);
		Log.i(LOG_TAG, word + " => " + wordShuffled);
		image.applyNewTaskParams();
		image.showTask(wordShuffled);
	}

	private String shuffleChars(String str) {
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			int index = rnd.nextInt(chars.length);
			if ((index != i || index == chars.length - 1) && chars[index] != '%') {
				sb.append(chars[index]);
				chars[index] = '%';
			} else
				i--;
		}
		return sb.toString();
	}

	@Override
	public void hint(String letters) {

		if (magicMode) {
			image.updateTextView(R.id.magicWord, word);
		} else {
			int firstWrongLetterIndex = -1;
			for (int i = 0; i < letters.length(); i++) {
				if (letters.charAt(i) != word.charAt(i)) {
					firstWrongLetterIndex = i;
					break;
				}
			}

			score = score - params.getHintPrice();
			updateScoreInfo();

			image.simulateAnswerButtonPress(firstWrongLetterIndex);
			boolean[] taskButtonsVisibility = image.getTaskButtonVisibility();

			int hintedLetterIndex = firstWrongLetterIndex;
			if (hintedLetterIndex < 0) {
				hintedLetterIndex = letters.length();
			}

			char hintedLetter = word.charAt(hintedLetterIndex);

			int pressableTaskButtonIndex = 0;
			for (int i = 0; i < wordShuffled.length(); i++) {
				if (hintedLetter == wordShuffled.charAt(i) && taskButtonsVisibility[i]) {
					pressableTaskButtonIndex = i;
				}
			}
			image.simulateTaskButtonPress(pressableTaskButtonIndex);
		}
	}

	@Override
	public void toggleMagicMode() {
		if (!magicMode) {
			magicMode = true;
		} else {
			magicMode = false;
		}
		image.updateMode(magicMode);
	}

	private void updateScoreInfo() {
		image.updateTextView(R.id.view_score, context.getString(R.string.score_info) + ": " + score);
		image.updateScoreColors(score, record);
	}

	@Override
	public void analyzeAnswer(String answer) {

		if (answer.equals(word)) {
			onCorrectAnswer();
		} else {
			onMistake();
		}
	}

	private void onCorrectAnswer() {
		image.showResult();

		score = score + params.getWordPrice();
		if (score > record) {
			record = score;
			GlobalInvoke.paramsHandler.saveParamInt("PARAM_NAME_RECORD", record);
		}
		updateScoreInfo();
	}

	@Override
	public void nextStep() {
		step++;
		if (step > params.getStepsLimit()) {
			updateLevel();
		} else {
			newTask();
		}
	}

	private void updateLevel() {
		if (level < MAX_LEVEL) {
			level++;
			step = 1;

			AdBuddiz.getInstance().showAd();

			image.toast(context.getString(R.string.info_next_level) + " " + level);
			GlobalInvoke.paramsHandler.saveParamInt("PARAM_NAME_LEVEL", level);
			startLevel();
		} else {
			GlobalInvoke.paramsHandler.saveParamBoolean("PARAM_NAME_RESUMING", false);
			image.moveToFinishView();
		}
	}

	private void onMistake() {
		attempt++;
		image.updateTextView(R.id.info_attempt,
				context.getString(R.string.attempt) + ": " + attempt + "/" + params.getAttemptLimit());
		if (attempt <= params.getAttemptLimit()) {
			score = score - params.getAttemptPrice();
			updateScoreInfo();
			countStepCost(params.getAttemptPrice());
			image.outlineByColor('r');
			delayExec(1);
		} else {
			penalty(params.getWordPrice());
			image.outlineByColor('r');
			delayExec(2);
		}
	}

	@Override
	public void nextWord() {
		penalty(params.getWordPrice());
		
		image.showCorrectAnswer(word);
		delayExec(3);
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
	
	private void delayExec(int key){
		delayKey=key;
		delayTimer.start();
	}
	
	private void continueExec(){
		if(delayKey==1){
			image.showTask(wordShuffled);
			delayKey=0;
		}
		
		if(delayKey==3){
			delayKey=0;
			newTask();
		}
		
		if(delayKey==2){
			image.showCorrectAnswer(word);
			delayExec(3);
		}
	}
	
	private void createDelayTimer() {
		delayTimer=new CountDownTimer(1000,1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				
			}
			
			@Override
			public void onFinish() {
				continueExec();
			}
		};
		
	}

	@Override
	public void inputWholeWord() {
		image.updateTextView(R.id.txt_answer, word);
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
	public boolean isMagicMode() {
		return magicMode;
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
