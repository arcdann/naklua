package com.daniloff.learninghelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Runner {

	public static void main(String[] args) {

		String fileAsString = FileUtils.readFile("config/words.txt");
		String[] sharpSeparatedWords = fileAsString.split("\\n");
		List<String> wordsCouples = Arrays.asList(sharpSeparatedWords);

		for (int count = 0; count < 4; count++) {
			Collections.shuffle(wordsCouples);
			for (String wordCouple : wordsCouples) {
				showTask(wordCouple);
			}
		}
	}

	private static void showTask(String wordCouple) {

		Task task = new Task(wordCouple);
		System.out.print(task.getMeaninig() + " = ");

		String answer = "";
		BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			answer = bfReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		checkUpAnswer(answer, task);
	}

	private static void checkUpAnswer(String answer, Task task) {
		if (answer.equals(task.getPhrase())) {
			System.out.println("Correct");
		} else {
			System.out.println("Wrong");
			System.out.println("Correct phrase: " + task.getPhrase());
		}
		System.out.println();
	}

}
