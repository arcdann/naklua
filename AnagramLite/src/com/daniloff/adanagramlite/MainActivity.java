package com.daniloff.adanagramlite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.daniloff.adanagramlite.proc.WordsHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, AnagramView {

	public static MainActivity image;

	private String resString;
	private int hintLimit = 1;

	private TextView taskTxt;
	private TextView levelTxt;
	private TextView stepTxt;
	private TextView attemptTxt;
	private TextView scoreTxt;
	private TextView recordTxt;
	private Button buttonOK;
	private Button buttonHint;
	private Button buttonNext;
	private TextView answerTxt;

	private final int WORD_LENGTH_MIN = 4;
	private int level = 0;

	private WordsHandler wordsHandler;
	public Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inicializeViews();
		readFile();

		wordsHandler = new WordsHandler();
		wordsHandler.setView(this);
		wordsHandler.start(resString);

		// showTask();
	}

	private void inicializeViews() {
		buttonOK = (Button) findViewById(R.id.button_ok);
		buttonOK.setOnClickListener(this);

		buttonHint = (Button) findViewById(R.id.button_hint);
		buttonHint.setText("Hint (" + hintLimit + ")");
		buttonHint.setOnClickListener(this);

		buttonNext = (Button) findViewById(R.id.button_next);
		buttonNext.setOnClickListener(this);

		taskTxt = (TextView) findViewById(R.id.view_task);
		levelTxt = (TextView) findViewById(R.id.info_level);

		answerTxt = (TextView) findViewById(R.id.txt_answer);
		stepTxt = (TextView) findViewById(R.id.info_step);
		attemptTxt=(TextView) findViewById(R.id.info_attempt);
	}

	public void showTask(final String shuffledWord) {
		hintLimit = 1;// ///////
		buttonHint.setText("Hint (" + hintLimit + ")");
		buttonHint.setEnabled(true);
		runOnUiThread(new Runnable() {
			public void run() {

				taskTxt.setText(shuffledWord);
				levelTxt.setText("level: " + level);
				stepTxt.setText("step: " + wordsHandler.getStepRemainCount());
				attemptTxt.setText("att: " + wordsHandler.getAttempt());

			}
		});

		attemptTxt = (TextView) findViewById(R.id.info_attempt);
		runOnUiThread(new Runnable() {
			public void run() {
				attemptTxt.setText("attempt: " + wordsHandler.getAttempt());
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void readFile() {
		Context context = getBaseContext();
		InputStream inputStream = context.getResources().openRawResource(R.raw.b4);
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
		resString = text.toString();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_ok:
			StringBuilder sb = new StringBuilder(answerTxt.getText());
			String answer = sb.toString();
			if (answer.length() == WORD_LENGTH_MIN + level) {
				wordsHandler.analyzeAnswer(answer);
				answerTxt.setText("");
				wordsHandler.newTask();
			} else {
				Toast toast = Toast.makeText(getApplicationContext(),
						("it must be " + (WORD_LENGTH_MIN + level) + " chars"), Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			break;

		case R.id.button_next:
			answerTxt.setText("");
			wordsHandler.newTask();
			break;

		case R.id.button_hint:
			wordsHandler.hint(1);// ////
			hintLimit--;
			buttonHint.setText("Hint (" + hintLimit + ")");
			if (hintLimit == 0) {
				buttonHint.setEnabled(false);
			}

			// handler.newTask();
			break;

		}

	}

	public void setTask(TextView word) {
		this.taskTxt = word;
	}

	@Override
	public void setTaskTxt() {
		// TODO Auto-generated method stub

	}

	@Override
	public void appendChar(final char c) {
		final StringBuilder sb = new StringBuilder(answerTxt.getText());
		sb.append(c);
		runOnUiThread(new Runnable() {
			public void run() {
				answerTxt.setText(sb);
			}
		});

	}

}
