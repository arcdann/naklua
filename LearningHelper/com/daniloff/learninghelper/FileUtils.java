package com.daniloff.learninghelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileUtils {

	public static String readFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
			try {
				String s;
				while ((s = in.readLine()) != null) {
					sb.append(s);
					sb.append("\n");
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// System.out.println("File '"+fileName+"' have read");
		return sb.toString();
	}

	public static List<String> receiveWordsCouples(String fileName) {
		String resStr = FileUtils.readFile("config/words.txt");
		List<String> wordsCouples = new LinkedList<String>();
		String[] words = resStr.split("\\n");
		for (String wordsCouple : words) {
			wordsCouples.add(wordsCouple);
		}
		Collections.shuffle(wordsCouples);
		return wordsCouples;
	}

}
