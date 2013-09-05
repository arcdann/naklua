package com.daniloff.learninghelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Runner {

	public static void main(String[] args) {
		List<Task> wordsCouples = prepareWordsList();
		for (int count = 0; count < 4; count++) {
			runOnce(wordsCouples);
		}
	}

	private static List<Task> prepareWordsList() {

		List<Task> retVal = new ArrayList<>();

		String fileAsString = FileUtils.readFile("config/words.txt");
		String[] sharpSeparatedWords = fileAsString.split("\\n");

		for (String current : sharpSeparatedWords) {
			retVal.add(new Task(current));
		}

		return retVal;
	}

	private static void runOnce(List<Task> wordsCouples) {
		Collections.shuffle(wordsCouples);
		for (Task wordCouple : wordsCouples) {
			showTask(wordCouple);
		}
	}

	private static void showTask(Task task) {

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
