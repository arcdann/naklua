package com.daniloff.chipspuzzle;

import java.util.ArrayList;
import java.util.List;

public class AnswerChecker {

	private BoxActivity box;
	private List<String> rightAnswer=new ArrayList<String>();

	public void start() {
		int answerSize = 16;
		for (int i = 1; i <=answerSize; i++) {
			rightAnswer.add(String.valueOf(i));
		}
//		System.out.println(rightAnswer);
	}

	public boolean isRightAnswer(List<String> answer) {
		for (int i = 0; i < answer.size(); i++) {
			if (!answer.get(i).equals(rightAnswer.get(i))) {
				return false;
			}
		}
		return true;
	}

	public BoxActivity getBox() {
		return box;
	}

	public void setBox(BoxActivity box) {
		this.box = box;
	}
}
