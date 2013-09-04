package com.daniloff.learninghelper;

public class Task {

	private String phrase;
	private String meaninig;

	public Task(String wordCouple) {
		int separatorIndex = wordCouple.indexOf('#');
		phrase = wordCouple.substring(0, separatorIndex);
		meaninig = wordCouple.substring(separatorIndex + 1);
	}

	public String getPhrase() {
		return phrase;
	}

	public String getMeaninig() {
		return meaninig;
	}

}
