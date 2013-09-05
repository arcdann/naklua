package com.daniloff.learninghelper;

import java.util.ArrayList;
import java.util.List;

public class LearningHelper {

	public static void main(String[] args) {
		prepareTaskList();

	}

	private static void prepareTaskList() {
		String FileAsString = FileUtils.readFile("config/words.txt");
		String[] sharpSeparatedWords = FileAsString.split("//n");
		List<String> list = new ArrayList<>();
		for (String wordsCouples : sharpSeparatedWords) {
			list.add(wordsCouples);
		}

	}

}
