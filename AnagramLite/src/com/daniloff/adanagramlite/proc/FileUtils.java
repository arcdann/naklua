package com.daniloff.adanagramlite.proc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.content.Context;

import com.daniloff.adanagramlite.R;

public class FileUtils {
	@SuppressLint("DefaultLocale")
	public static String readFile(Context context) {
		InputStream inputStream = context.getResources().openRawResource(R.raw.ch04);
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

}
