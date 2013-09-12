package com.daniloff.wordcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button showButton;
	private Button nextButton;
	private List<Task> taskList;
	private Task currentTask;
	private boolean reverted;
	private Random rnd = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		showButton = (Button) findViewById(R.id.button_show);
		// showButton.setOnClickListener(this);

		OnTouchListener listener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					revertCard();
					break;
				case MotionEvent.ACTION_UP:
					revertCard();
					break;
				default:
					break;
				}
				return true;
			}
		};
		showButton.setOnTouchListener(listener);

		nextButton = (Button) findViewById(R.id.button_next);
		nextButton.setOnClickListener(this);

		prepareTaskList();
		showTask();
	}

	private void prepareTaskList() {

		String fileAsString = FileUtils.readFile(getApplicationContext(), R.raw.words);
		String[] sharpSeparatedWords = fileAsString.split("\\n");

		List<Task> retList = new ArrayList<Task>();

		for (String wordsCouple : sharpSeparatedWords) {
			int separatorIndex = wordsCouple.indexOf('#');
			String phrase = wordsCouple.substring(0, separatorIndex);
			String meaning = wordsCouple.substring(separatorIndex + 1);

			Task task = new Task(phrase, meaning);
			retList.add(task);
		}
		taskList = retList;
	}

	private void showTask() {
		reverted = false;
		currentTask = taskList.get(rnd.nextInt(taskList.size()));
		showInitialView();
	}

	private void revertCard() {
		if (reverted) {
			showInitialView();
			reverted = false;
		} else {
			showRevertedView();
			reverted = true;
		}
	}

	private void showRevertedView() {
		showButton.setBackgroundColor(getResources().getColor(R.color.sand));
		showButton.setTextColor(getResources().getColor(R.color.brown));

		showButton.setText(currentTask.getMeaning());
	}

	private void showInitialView() {
		showButton.setBackgroundColor(getResources().getColor(R.color.azure));
		showButton.setTextColor(getResources().getColor(R.color.blue));

		showButton.setText(currentTask.getPhrase());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_next:
			showTask();
			break;
		case R.id.button_show:
			revertCard();
			break;
		}
	}
}
