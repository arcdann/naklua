package com.daniloff.commenteraser;

public class StartCommentEraser {

	public static void main(String[] args) {
		String strSrc = FileUtils.read("config/script.txt");
		String strNew = strSrc.replaceAll("\\[.*\\]", "[]");
		FileUtils.writeFile("config/script_new.txt", strNew);
	}
}
