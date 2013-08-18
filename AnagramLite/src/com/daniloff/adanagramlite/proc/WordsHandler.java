package com.daniloff.adanagramlite.proc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.daniloff.adanagramlite.AnagramView;
import com.daniloff.adanagramlite.MainActivity;

public class WordsHandler {

	private List<String> list;
	private Set<Integer> set;
	private String word;
	private String wordShuffled;
	private AnagramView image;
	private Random rnd = new Random();

	public void start(String resString) {
		list = parseWords(resString);
		set = new HashSet<Integer>();
		supplyTask(list, rnd);
	}

	private List<String> parseWords(String resString) {
		List<String> list = new ArrayList<String>();
		String[] words = resString.split("%");
		for (String word : words) {
			list.add(word);
		}
		return list;
	}

	public void newTask() {
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
		char c=word.charAt(i-1);
		image.appendChar(c);
	}

	public void checkAnswer(String answer) {
	if(answer.equals(word)){
//		image.t
	}
		
	}

}
