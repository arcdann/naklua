package com.daniloff.adanagramlite.proc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import android.annotation.SuppressLint;
import android.content.Context;

public class FileUtils {
	@SuppressLint("DefaultLocale")
	public static String readFile(Context context, int resource) {
		InputStream inputStream = context.getResources().openRawResource(resource);
		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		String line;
		StringBuilder text = new StringBuilder();

		try {
			while ((line = buffreader.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text.toString().toLowerCase();
	}

	public static Queue<String> receiveWords(Context context, LevelParams params) {
		String resourceString = FileUtils.readFile(context, params.getResource());
		LinkedList<String> wordsForLevel = new LinkedList<String>();
		String[] words = resourceString.split("%");
		for (String word : words) {
			if (word.length() == params.getWordLength()) {
				wordsForLevel.add(word);
			}
		}
		Collections.shuffle(wordsForLevel);
		return wordsForLevel;
	}

}
