package com.daniloff.barcodescanner;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;

public class StringUtils {
	
	public static List<String> receiveWords(Context context, int resource) {
		String resourceString = FileUtils.readFile(context, resource);
		List<String> resString = new LinkedList<String>();
		String[] stringArray = resourceString.split("\\n");
		for (String str : stringArray) {
				resString.add(str);
		}
		return resString;
	}
	
}
