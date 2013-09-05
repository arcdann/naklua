package com.daniloff.learninghelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearningHelper {

	public static void main(String[] args) {
		List<Task> taskList = prepareTaskList();
		for (int i = 0; i < 4; i++) {
			runLoop(taskList);
			System.out.println();
		}
		System.out.println("End of exercise");
	}

	private static void runLoop(List<Task> taskList) {
		List<Task> currentTaskList = new ArrayList<>(taskList);
		Collections.shuffle(currentTaskList);
		while (!currentTaskList.isEmpty()) {
			Task currentTask = currentTaskList.remove(0);
			System.out.print(currentTask.getMeaning() + " : ");

			String answer = "";
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in));
			try {
				answer = bfReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (answer.equalsIgnoreCase(currentTask.getPhrase())) {
				System.out.println("Correct");
			} else {
				System.out.println("Wrong. The phrase is to be: " + currentTask.getPhrase());
				currentTaskList.add(currentTask);
			}
			System.out.println();
		}
	}

	private static List<Task> prepareTaskList() {
		String FileAsString = FileUtils.readFile("config/words.txt");
		String[] sharpSeparatedWords = FileAsString.split("\\n");

		List<Task> taskList = new ArrayList<>();

		for (String wordsCouple : sharpSeparatedWords) {
			Task task = new Task(wordsCouple);
			taskList.add(task);
		}
		return taskList;
	}

}
