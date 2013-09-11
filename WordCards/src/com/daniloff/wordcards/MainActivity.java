package com.daniloff.wordcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button showButton;
	private Button nextButton;
	private List<Task> taskList;
	private Task currentTask;
	private boolean overturned;
	int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		showButton = (Button) findViewById(R.id.button_show);
		nextButton = (Button) findViewById(R.id.button_next);
		nextButton.setOnClickListener(this);
		showButton.setOnClickListener(this);

		taskList = prepareTaskList();
		showTask(taskList);
	}

	private List<Task> prepareTaskList() {

		String FileAsString = FileUtils.readFile(getApplicationContext(), R.raw.words);
		String[] sharpSeparatedWords = FileAsString.split("\\n");

		List<Task> retList = new ArrayList<Task>();

		for (String wordsCouple : sharpSeparatedWords) {
			Task task = new Task(wordsCouple);
			retList.add(task);
		}
		Collections.shuffle(retList);
		return retList;

	}

	private void showTask(List<Task> taskList) {

		showButton.setBackgroundColor(getResources().getColor(R.color.azure));
		showButton.setTextColor(getResources().getColor(R.color.blue));

		overturned = false;
		currentTask = taskList.remove(0);
		showButton.setText(currentTask.getPhrase());

		taskList.add(currentTask);

	}

	private void overturnCard() {

		if (!overturned) {

			showButton.setBackgroundColor(getResources().getColor(R.color.sand));
			showButton.setTextColor(getResources().getColor(R.color.brown));

			showButton.setText(currentTask.getMeaning());
			overturned = true;
		} else {

			showButton.setBackgroundColor(getResources().getColor(R.color.azure));
			showButton.setTextColor(getResources().getColor(R.color.blue));

			showButton.setText(currentTask.getPhrase());
			overturned = false;
		}

	}

	private void countCard() {
		count++;
		if (count > taskList.size()) {
			Collections.shuffle(taskList);
			count = 0;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_next:
			countCard();
			showTask(taskList);
			break;
		case R.id.button_show:
			overturnCard();
			break;

		default:
			break;
		}

	}

}
