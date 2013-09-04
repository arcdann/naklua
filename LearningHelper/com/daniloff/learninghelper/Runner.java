package com.daniloff.learninghelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Runner {

	static List<String> wordsCouples;
	static List<String> unlearnedWords = new LinkedList<String>();
	static String unlearnedWord;

	public static void main(String[] args) {

		String resStr = FileUtils.readFile("config/words.txt");
		String[] words = resStr.split("\\n");
		wordsCouples = Arrays.asList(words);

		for (int count = 0; count < 4; count++) {
			Collections.shuffle(wordsCouples);
			for (String wordCouple : wordsCouples) {
				unlearnedWord = wordCouple;
				showTask(wordCouple);
			}
		}
	}

	private static void showTask(String wordCouple) {

		Task task = new Task(wordCouple);
		// System.out.println(task.getPhrase());
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
		unlearnedWords.add(unlearnedWord);
	}

}
