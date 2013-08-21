package com.daniloff.adanagramlite.proc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.daniloff.adanagramlite.AnagramView;
import com.daniloff.adanagramlite.R;

public class WordsHandler {

	private List<String> list;
	private Set<Integer> set;
	private String word;
	private String wordShuffled;
	private AnagramView image;
	private Random rnd = new Random();

	private int level = 1;

	private int stepsLimit;
	private int attemptLimit;
	private int wordLength;
	private int step = 1;
	private int attempt = 1;
	private int score;
	private int record;
	private int stepCost;
	private LevelParams params;
	private int hintLimit;
	private int hintPrice;
	private int attemptPrice;
	private int wordPrice;
	private int resource;// ////////////////////////////////////////

	public void start(String resString) {
		params = AnagramConstants.LEVEL_PARAMS.get(level);
		applyParams();
		list = parseWords(resString);
		set = new HashSet<Integer>();
		supplyTask(list, rnd);
	}

	private void applyParams() {
		stepsLimit = params.getStepsLimit();
		resource = params.getResource();
		wordLength = params.getWordLength();
		wordPrice = params.getWordPrice();
		attemptLimit = params.getAttemptLimit();
		attemptPrice = params.getAttemptPrice();
		hintLimit = (params.getHintLimit());
		hintPrice = params.getHintPrice();

	}

	private List<String> parseWords(String resString) {
		List<String> list = new ArrayList<String>();
		String[] words = resString.split("%");
		for (String word : words) {
			if (word.length() == wordLength) {
				list.add(word);
			}
		}
		return list;
	}

	public void newTask() {
		attempt = 1;
		stepCost = 0;
		supplyTask(list, rnd);
	}

	public void supplyTask(List<String> list, Random rnd) {
		int s = set.size();
		int s1;
		int index;
		do {
			index = rnd.nextInt(list.size());
			set.add(index);
			s1 = set.size();
		} while (s == s1);
		word = list.get(index);

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
		score = score - hintPrice;
		image.updateTextView(R.id.view_score, "score: " + score);
		countStepCost(hintPrice);
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
		if (step > stepsLimit) {

			level++;
			step = 1;
			image.toast("you passed to level " + level);
			// update level and step info
			// start(
			// resString);//////////////////////////////////////////////////////////////////////////////
		}
		image.updateTextView(R.id.info_step, "step: " + step+"/"+stepsLimit);

		score = score + wordPrice;
		if (score > record) {
			record = score;
			image.updateScoreColors(score, record);
		}

		newTask();
	}

	private void onMistake() {
		attempt++;
		image.updateTextView(R.id.info_attempt, "attempt: " + attempt + "/" + attemptLimit);
		if (attempt <= attemptLimit) {
			score = score - attemptPrice;
			image.updateTextView(R.id.view_score, "score: " + score);

			countStepCost(attemptPrice);
			image.toast("Try again");
		} else {
			penalty(wordPrice);
			image.toast("The word: " + word);
			newTask();
		}
	}

	public void nextWord() {
		penalty(wordPrice);
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

	public void setStep(int step) {
		this.step = step;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public int getScore() {
		return score;
	}

	public int getRecord() {
		return record;
	}

	public int getHintLimit() {
		return hintLimit;
	}

}
