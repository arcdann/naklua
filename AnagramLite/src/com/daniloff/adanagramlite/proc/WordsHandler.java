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

	private final int STEPS = 20;
	private final int ATTEMPTS = 3;
	private int step = 1;
	private int attempt = 1;
	private int score;
	private int record;

	public void start(String resString) {
		list = parseWords(resString);
		set = new HashSet<Integer>();
		supplyTask(list, rnd);
	}

	private List<String> parseWords(String resString) {
		List<String> list = new ArrayList<String>();
		String[] words = resString.split("%");
		for (String word : words) {
			if (!word.isEmpty()) {
				list.add(word);
			}
		}
		return list;
	}

	public void newTask() {
		attempt = 1;
		supplyTask(list, rnd);
	}

	public int getAttemptRemainCount() {
		return ATTEMPTS;
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
		if(step>STEPS)image.toast("You have passed this level");
		score = score + 5;
		if (score > record)
			record = score;
		newTask();
	}

	private void onMistake() {
		attempt++;
		image.updateTextView(R.id.info_attempt, "attempt: "+attempt);
		if (attempt <= ATTEMPTS) {
			image.toast("Try again");
		} else {
			penalty();
			image.toast("The word: " + word);
			newTask();
		}
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

//	public int getStepRemainCount() {
//		return STEPS;
//	}

	public int getScore() {
		return score;
	}

	public int getRecord() {
		return record;
	}

	public void penalty() {
		score = score - 5;

	}

	public void nextWord() {
		penalty();
		image.toast("The word: " + word);
		newTask();
	}

}
