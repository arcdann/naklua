package com.daniloff.adanagramlite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

public class MainActivity extends Activity implements OnClickListener, AnagramView {

	private static final String PARAM_NAME_RECORD = "record";
	private static final String SETTINGS_FILENAME = "settings.ini";

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
	private int hintRemain;

	private WordsHandler wordsHandler;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
		// godModeTxt = (TextView) findViewById(R.id.god_mode_info);
		// godModeTxt.setText("");

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
		hintRemain = wordsHandler.getParams().getHintLimit();
		buttonHint.setText("Hint (" + hintRemain + ")");
		buttonHint.setEnabled(true);
		runOnUiThread(new Runnable() {
			public void run() {
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
				int charsCount = answerTxt.getText().length();
				wordsHandler.hint(charsCount + 1);// позиция символа в строке
				hintRemain--;
				buttonHint.setText("Hint (" + hintRemain + ")");
				if (hintRemain == 0) {
					buttonHint.setEnabled(false);
				}
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
			if (answer.equalsIgnoreCase("godmode")) {
				wordsHandler.toggleGodMode();
				answerTxt.setText("");
			} else {
				toast("it must be " + (wordsHandler.getParams().getWordLength()) + " chars");
			}
		}
	}

	public void setTask(TextView word) {
		this.taskTxt = word;
	}

	@Override
	public void appendChar(final char c) {
		final StringBuilder sb = new StringBuilder(answerTxt.getText());
		sb.append("<font color='#0000FF'>");// blue
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
	public void updateScoreColors(int score, int record) {
		if (record > score) {
			scoreTxt.setTextColor(Color.GREEN);
		} else {
			scoreTxt.setTextColor(Color.BLUE);
		}
	}

	@Override
	public void updateMode(boolean godMode) {
		if (godMode) {
			LinearLayout wrapLayout = (LinearLayout) findViewById(R.id.wrap_layout);
			LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			godModeTxt = new TextView(this);
			godModeTxt.setText("god mode");
			godModeTxt.setTextColor(getResources().getColor(R.color.purple));
			godModeTxt.setLayoutParams(lpView);
			wrapLayout.addView(godModeTxt);

			answerTxt.setTextColor(getResources().getColor(R.color.purple));
			buttonHint.setTextColor(getResources().getColor(R.color.purple));
		}

	}

	@Override
	public void saveRecord(int record) {

		SharedPreferences settings = getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);

		Editor editor = settings.edit();
		editor.putInt(PARAM_NAME_RECORD, record);
		editor.commit();

	}

	@Override
	public int loadRecord() {
		SharedPreferences settings = getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);

		return settings.getInt(PARAM_NAME_RECORD, 0);
	}
}
