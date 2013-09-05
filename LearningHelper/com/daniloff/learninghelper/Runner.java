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
			System.out.println();
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
		
		List<Task> wordsCouplesCopy = new ArrayList<Task>(wordsCouples);
		Collections.shuffle(wordsCouplesCopy);
		
		while (!wordsCouplesCopy.isEmpty()) {
			Task current = wordsCouplesCopy.remove(0);
			System.out.print(current.getMeaninig() + " = ");

			String answer = "";
			BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in));
			try {
				answer = bfReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (answer.equals(current.getPhrase())) {
				System.out.println("Correct");
			} else {
				System.out.println("Shit! Correct phrase is: " + current.getPhrase());
				wordsCouplesCopy.add(current);
			}
		}
	}
}
