package com.daniloff.wordcards;

public class Task {

	private final String phrase;
	private final String meaning;

	public Task(String wordsCouples) {
		int separatorIndex = wordsCouples.indexOf('#');
		phrase = wordsCouples.substring(0, separatorIndex);
		meaning = wordsCouples.substring(separatorIndex + 1);
	}

	public String getPhrase() {
		return phrase;
	}

	public String getMeaning() {
		return meaning;
	}

}
