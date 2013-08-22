package com.daniloff.adanagramlite.proc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.daniloff.adanagramlite.AnagramView;
import com.daniloff.adanagramlite.R;

public class WordsHandler {

	private Set<String> wordsForLevel;
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
//	private int resource;// ////////////////////////////////////////

	public void start(String resString) {
		params = AnagramConstants.LEVEL_PARAMS.get(level);
		wordsForLevel = parseWords(resString);
		supplyTask();
	}

	private Set<String> parseWords(String resString) {
		 wordsForLevel = new HashSet<String>();
		String[] words = resString.split("%");
		for (String word : words) {
			if (word.length() == params.getWordLength()) {
				wordsForLevel.add(word);
			}
		}
		return wordsForLevel;
	}

	public void newTask() {
		attempt = 1;
		stepCost = 0;
		supplyTask();
	}

	public void supplyTask() {
		int size = wordsForLevel.size();
		int item = rnd.nextInt(size); 
		Iterator<String> itr=wordsForLevel.iterator();
		for(int i=0;i<item-1;i++){
			itr.next();
		}
		word=itr.next();
		itr.remove();
		
		shuffleChars(word);
		System.out.println(word);
		image.showTask(wordShuffled);
	}

	private String shuffleChars(String word) {
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
		return wordShuffled;

	}

	public void setView(AnagramView image) {
		this.image = image;
	}

	public void hint(int i) {
		score = score - params.getHintPrice();
		image.updateTextView(R.id.view_score, "score: " + score);
		countStepCost(params.getHintPrice());
		char c = word.charAt(i - 1);
		image.appendChar(c);
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

			level++;
			step = 1;
			image.toast("you passed to level " + level);
			// update level and step info
			// start(
			// resString);//////////////////////////////////////////////////////////////////////////////
		}
		image.updateTextView(R.id.info_step, "step: " + step+"/"+params.getStepsLimit());

		score = score + params.getWordPrice();
		if (score > record) {
			record = score;
			image.updateScoreColors(score, record);
		}

		newTask();
	}

	private void onMistake() {
		attempt++;
		image.updateTextView(R.id.info_attempt, "attempt: " + attempt + "/" + params.getAttemptLimit());
		if (attempt <= params.getAttemptLimit()) {
			score = score - params.getAttemptPrice();
			image.updateTextView(R.id.view_score, "score: " + score);

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

	public void penalty(int penalty) {
		if (stepCost > penalty)
			penalty = stepCost;
		score = score - penalty;
		stepCost = 0;
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

}
