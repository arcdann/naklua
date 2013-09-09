package com.daniloff.adanagramlite.proc;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

	public static List<String> wordToLetters(String word) {

		List<String> retList = new ArrayList<String>();
		for (int i = 0; i < word.length(); i++) {
			retList.add(word.substring(i, i + 1));
		}
		return retList;
	}

	public static String lettersToWord(List<String> listOfLetters) {

		String retString = "";
		for (String s : listOfLetters) {
			retString = retString + s;
		}
		return retString;
	}

}
