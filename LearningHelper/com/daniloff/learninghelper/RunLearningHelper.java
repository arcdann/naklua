package com.daniloff.learninghelper;

import java.util.LinkedList;
import java.util.List;

public class RunLearningHelper {

	static List<String> wordsCouples;
	static List<String> unlearnedWords = new LinkedList<String>();

	public static void main(String[] args) {
		wordsCouples = FileUtils.receiveWordsCouples("config/words.txt");

		Quiz quiz = new Quiz();
		quiz.startQuiz(wordsCouples);

	}

	public static List<String> getUnlearnedWords() {
		return unlearnedWords;
	}

	public static List<String> getWordsCouples() {
		return wordsCouples;
	}

}
