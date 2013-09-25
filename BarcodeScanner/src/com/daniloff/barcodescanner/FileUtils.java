package com.daniloff.barcodescanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class FileUtils {

	public static String readFile(Context context, int resource) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream inputStream = context.getResources().openRawResource(resource);
			InputStreamReader inputreader = new InputStreamReader(inputStream);
			BufferedReader buffreader = new BufferedReader(inputreader);
			
			
			
			
	//		BufferedReader buffreader = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
			try {
				String s;
				while ((s = buffreader.readLine()) != null) {
					sb.append(s);
					sb.append("\n");
				}
			} finally {
				buffreader.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}
}