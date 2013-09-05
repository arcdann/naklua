package com.daniloff.learninghelper;

import java.util.ArrayList;
import java.util.List;

public class LearningHelper {

	public static void main(String[] args) {
		List<Task>taskList=prepareTaskList();
		for(int i=0;i<2;i++){
			runLoop(taskList);
		}

	}

	private static void runLoop(List<Task> taskList) {
		while  (!taskList.isEmpty()){
		Task currentTask=taskList.remove(0);
			System.out.println(currentTask.getMeaning());
			System.out.println(currentTask.getPhrase());
			System.out.println();
			
		}
		
	}

	private static List<Task> prepareTaskList() {
		String FileAsString = FileUtils.readFile("config/words.txt");
		String[] sharpSeparatedWords = FileAsString.split("\\n");
		
		List<Task> taskList = new ArrayList<>();
		
		for (String wordsCouple : sharpSeparatedWords) {
			Task task=new Task(wordsCouple);
			taskList.add(task);
		}
		return taskList;
	}

}
