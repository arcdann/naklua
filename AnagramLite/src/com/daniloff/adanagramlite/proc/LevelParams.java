package com.daniloff.adanagramlite.proc;

public class LevelParams {
	private final int stepsCount;
	private final int resource;
	private final int wordLength;
	private final int wordPrice;
	private final int hintCount;
	private final int hintPrice;
	private final int attemptCount;
	private final int attemptPrice;

	public LevelParams(int stepsCount, int wordLength, int resource, int wordPrice, int hintCount, int hintPrice,
			int attemptCount, int attemptPrice) {
		this.stepsCount = stepsCount;
		this.resource = resource;
		this.wordLength = wordLength;
		this.wordPrice = wordPrice;
		this.hintCount = hintCount;
		this.hintPrice = hintPrice;
		this.attemptCount = attemptCount;
		this.attemptPrice = attemptPrice;
	}

	public int getStepsCount() {
		return stepsCount;
	}

	public int getResourcel() {
		return resource;
	}

	public int getWordLength() {
		return wordLength;
	}

	public int getWordPrice() {
		return wordPrice;
	}

	public int getHintCount() {
		return hintCount;
	}

	public int getHintPrice() {
		return hintPrice;
	}

	public int getAttemptCount() {
		return attemptCount;
	}

	public int getAttemptPrice() {
		return attemptPrice;
	}

}
