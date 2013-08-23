package com.daniloff.adanagramlite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daniloff.adanagramlite.proc.WordsHandler;

public class MainActivity extends Activity implements OnClickListener, AnagramView {

	private TextView taskTxt;
	private TextView stepTxt;
	private TextView attemptTxt;
	private TextView scoreTxt;
	private TextView recordTxt;
	private Button buttonOK;
	private Button buttonHint;
	private Button buttonNext;
	private EditText answerTxt;
	private int hintRemain;

	private WordsHandler wordsHandler;
	public Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inicializeViews();

		wordsHandler = new WordsHandler();
		wordsHandler.setView(this);
		wordsHandler.setContext(getBaseContext());
		wordsHandler.start();
	}

	private void inicializeViews() {
		buttonOK = (Button) findViewById(R.id.button_ok);
		buttonOK.setOnClickListener(this);

		buttonHint = (Button) findViewById(R.id.button_hint);
		buttonHint.setOnClickListener(this);

		buttonNext = (Button) findViewById(R.id.button_next);
		buttonNext.setOnClickListener(this);

		taskTxt = (TextView) findViewById(R.id.view_task);
		answerTxt = (EditText) findViewById(R.id.txt_answer);

		stepTxt = (TextView) findViewById(R.id.info_step);
		attemptTxt = (TextView) findViewById(R.id.info_attempt);
		scoreTxt = (TextView) findViewById(R.id.view_score);
		scoreTxt.setTextColor(Color.GREEN);
		recordTxt = (TextView) findViewById(R.id.view_record);
		recordTxt.setTextColor(Color.BLUE);

	}

	public void showTask(final String shuffledWord) {
		hintRemain = wordsHandler.getParams().getHintLimit();
		buttonHint.setText("Hint (" + hintRemain + ")");
		buttonHint.setEnabled(true);
		runOnUiThread(new Runnable() {
			public void run() {
				taskTxt.setText(shuffledWord);
				stepTxt.setText("step: " + wordsHandler.getStep() + "/" + wordsHandler.getParams().getStepsLimit());
				attemptTxt.setText("attempt: " + wordsHandler.getAttempt() + "/"
						+ wordsHandler.getParams().getAttemptLimit());
				// scoreTxt.setText("score: " + wordsHandler.getScore());
				recordTxt.setText("record: " + wordsHandler.getRecord());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_ok:
			String answer = answerTxt.getText().toString();
			if (answer.length() == wordsHandler.getParams().getWordLength()) {
				answer = answer.toLowerCase();
				wordsHandler.analyzeAnswer(answer);
				answerTxt.setText("");
			} else {
				toast("it must be " + (wordsHandler.getParams().getWordLength()) + " chars");
			}
			break;

		case R.id.button_next:
			answerTxt.setText("");
			wordsHandler.nextWord();
			break;

		case R.id.button_hint:
			if (wordsHandler.isGodMode()) {
				wordsHandler.inputWholeWord();
			} else {
				int charsCount = answerTxt.getText().length();
				wordsHandler.hint(charsCount + 1);// позиция символа в строке
				hintRemain--;
				buttonHint.setText("Hint (" + hintRemain + ")");
				if (hintRemain == 0) {
					buttonHint.setEnabled(false);
				}
			}
			break;
		}
	}

	public void setTask(TextView word) {
		this.taskTxt = word;
	}

	@Override
	public void appendChar(final char c) {
		final StringBuilder sb = new StringBuilder(answerTxt.getText());
		sb.append(c);
		runOnUiThread(new Runnable() {
			public void run() {
				answerTxt.setText(sb);
				answerTxt.setSelection(answerTxt.length());
			}
		});
	}

	@Override
	public void toast(String message) {
		Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void updateTextView(int viewTextID, String text) {
		TextView tv = (TextView) findViewById(viewTextID);
		tv.setText(text);
	}

	@Override
	public void updateScoreColors(int score, int record) {
		if (record > score) {
			scoreTxt.setTextColor(Color.GREEN);
		} else {
			scoreTxt.setTextColor(Color.BLUE);
		}
	}

}
