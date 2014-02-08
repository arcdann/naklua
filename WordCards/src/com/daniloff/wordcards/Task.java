package com.daniloff.wordcards;

public class Task {

	private final String phrase;
	private final String meaning;

	public Task(String wordsCouple) {
		String taskLines = splitToLines(wordsCouple);
		int separatorIndex = taskLines.indexOf('#');
		phrase = taskLines.substring(0, separatorIndex);
		meaning = taskLines.substring(separatorIndex + 1);
	}

	private String splitToLines(String wordsCouple) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < wordsCouple.length(); i++) {
			char c = wordsCouple.charAt(i);
			if (c == ';' || c == '=') {
				sb.append('\n');
				if (c == '=') {
					sb.append('\n');
				}
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public String getPhrase() {
		return phrase;
	}

	public String getMeaning() {
		return meaning;
	}

}
