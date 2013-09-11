package com.daniloff.wordcards;

public class Task {

	private final String phrase;
	private final String meaning;

	public Task(String phrase, String meaning) {
		this.phrase = phrase;
		this.meaning = meaning;
	}

	public String getPhrase() {
		return phrase;
	}

	public String getMeaning() {
		return meaning;
	}

}
