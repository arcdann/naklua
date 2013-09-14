package com.daniloff.adanagramlite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.daniloff.adanagramlite.proc.WordsHandler;
import com.daniloff.adanagramlite.proc.WordsHandlerImpl;

public class MainActivity extends Activity implements OnClickListener, AnagramView {

	private LinearLayout wrapLayout;
	private TextView taskTxt;
	private TextView stepTxt;
	private TextView attemptTxt;
	private TextView scoreTxt;
	private TextView recordTxt;
	private TextView godModeTxt;
	private Button buttonOK;
	private Button buttonHint;
	private Button buttonNext;
	private EditText answerTxt;

	private WordsHandler wordsHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeViews();

		wordsHandler = new WordsHandlerImpl();
		wordsHandler.setView(this);
		wordsHandler.setContext(getBaseContext());

		String pressedButton = getIntent().getStringExtra("button");
		if (pressedButton.equals("newGame")) {
			wordsHandler.setLang(getIntent().getStringExtra("lang"));
		} else {
			wordsHandler.setResumed(true);
		}

		wordsHandler.startLevel();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initializeViews() {

		wrapLayout = (LinearLayout) findViewById(R.id.wrap_layout);
		wrapLayout.setBackgroundColor(getResources().getColor(R.color.brown_light));

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

		answerTxt.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
					submitAnswer();
					return true;
				} else {
					return false;
				}
			}
		});
	}

	public void showTask(final String shuffledWord) {
		buttonHint.setEnabled(true);
		runOnUiThread(new Runnable() {
			public void run() {
				taskTxt.setText("");
				taskTxt.setText(shuffledWord);
				stepTxt.setText("step: " + wordsHandler.getStep() + "/" + wordsHandler.getParams().getStepsLimit());
				attemptTxt.setText("attempt: " + wordsHandler.getAttempt() + "/"
						+ wordsHandler.getParams().getAttemptLimit());
				recordTxt.setText("record: " + wordsHandler.getRecord());
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_ok:
			submitAnswer();
			break;

		case R.id.button_next:
			answerTxt.setText("");
			wordsHandler.nextWord();
			break;

		case R.id.button_hint:
			if (wordsHandler.isGodMode()) {
				wordsHandler.inputWholeWord();
			} else {
				// int charsCount = answerTxt.getText().length();
				// wordsHandler.hint(charsCount + 1);// позиция символа в строке
			}
			answerTxt.setSelection(answerTxt.length());
			break;
		}
	}

	@SuppressLint("DefaultLocale")
	private void submitAnswer() {
		String answer = answerTxt.getText().toString();
		if (answer.length() == wordsHandler.getParams().getWordLength()) {
			answer = answer.toLowerCase();
			wordsHandler.analyzeAnswer(answer);
			answerTxt.setText("");
		} else {
			toast("it must be " + (wordsHandler.getParams().getWordLength()) + " chars");
		}
	}

	@Override
	public void appendChar(final char c) {
		final StringBuilder sb = new StringBuilder(answerTxt.getText());
		sb.append("<font color='#0000FF'>");
		sb.append(c);
		sb.append("</font>");

		runOnUiThread(new Runnable() {
			public void run() {
				answerTxt.setText(Html.fromHtml(sb.toString()));
			}
		});
	}

	@Override
	public void toast(String message) {
		Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 12);
		toast.show();
	}

	@Override
	public void updateTextView(int viewTextID, String text) {
		TextView tv = (TextView) findViewById(viewTextID);
		tv.setText(text);
	}

	@Override
	public void setEnable(int buttonID, boolean b) {
		Button button = (Button) findViewById(buttonID);
		button.setEnabled(b);
	}

	@Override
	public void updateScoreColors(int score, int record) {
		if (record > score) {
			scoreTxt.setTextColor(getResources().getColor(R.color.brown_deep));
		} else {
			scoreTxt.setTextColor(Color.BLUE);
		}
	}

	@Override
	public void updateMode(boolean godMode) {
		if (godMode) {
			wrapLayout = (LinearLayout) findViewById(R.id.wrap_layout);
			LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			godModeTxt = new TextView(this);
			godModeTxt.setText("god mode");
			godModeTxt.setTextColor(getResources().getColor(R.color.purple));
			godModeTxt.setLayoutParams(lpView);
			wrapLayout.addView(godModeTxt);

			answerTxt.setTextColor(getResources().getColor(R.color.purple));
			buttonHint.setTextColor(getResources().getColor(R.color.purple));
		} else {
			wrapLayout.removeView(godModeTxt);
			answerTxt.setTextColor(Color.BLACK);
			buttonHint.setTextColor(Color.BLACK);
		}
	}

	@Override
	public void moveToFinishView() {
		Intent intent = new Intent(MainActivity.this, FinishActivity.class);
		intent.putExtra("score", wordsHandler.getScore());
		intent.putExtra("record", wordsHandler.getRecord());

		startActivity(intent);
	}

	public void moveToChoiceView() {
		Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
		startActivity(intent);
	}

	@Override
	public void simulateButtonPress(int hintedLetterIndex, int pressableTaskButtonIndex) {
		// TODO Auto-generated method stub
		
	}

}
