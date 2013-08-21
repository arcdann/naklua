package com.daniloff.adanagramlite.proc;

public class LevelParams {
	private final int stepsLimit;
	private final int resource;
	private final int wordLength;
	private final int wordPrice;
	private final int hintLimit;
	private final int hintPrice;
	private final int attemptLimit;
	private final int attemptPrice;

	public LevelParams(int stepsLimit, int wordLength, int resource, int wordPrice, int hintLimit, int hintPrice,
			int attemptLimit, int attemptPrice) {
		this.stepsLimit = stepsLimit;
		this.resource = resource;
		this.wordLength = wordLength;
		this.wordPrice = wordPrice;
		this.hintLimit = hintLimit;
		this.hintPrice = hintPrice;
		this.attemptLimit = attemptLimit;
		this.attemptPrice = attemptPrice;
	}

	public int getStepsLimit() {
		return stepsLimit;
	}

	public int getResource() {
		return resource;
	}

	public int getWordLength() {
		return wordLength;
	}

	public int getWordPrice() {
		return wordPrice;
	}

	public int getHintLimit() {
		return hintLimit;
	}

	public int getHintPrice() {
		return hintPrice;
	}

	public int getAttemptLimit() {
		return attemptLimit;
	}

	public int getAttemptPrice() {
		return attemptPrice;
	}

}
