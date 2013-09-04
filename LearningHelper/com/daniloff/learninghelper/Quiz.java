package com.daniloff.learninghelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class Quiz {

	public void startQuiz(List<String> wordsCouples) {
		for (int count = 0; count < 4; count++) {

			for (String wordCouple : wordsCouples) {
				showTask(wordCouple);
			}
			Collections.shuffle(RunLearningHelper.getWordsCouples());
		}

	}

	private void showTask(String wordCouple) {

		String answer = "";

		int separatorIndex = wordCouple.indexOf('#');
		String phrase = wordCouple.substring(0, separatorIndex);
		String meaninig = wordCouple.substring(separatorIndex + 1);

		// System.out.println(phrase);
		System.out.print(meaninig + " = ");

		BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			answer = bfReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		checkUpAnswer(answer, phrase, wordCouple);

	}

	private void checkUpAnswer(String answer, String phrase, String wordCouple) {
		if (answer.equals(phrase)) {
			System.out.println("Correct");
		} else {
			System.out.println("Wrong");
			System.out.println("Correct phrase: " + phrase);
		}
		System.out.println("=============================");
		RunLearningHelper.getUnlearnedWords().add(wordCouple);
	}
}
