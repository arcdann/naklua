package com.daniloff.adanagramlite;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daniloff.adanagramlite.proc.WordsHandler;
import com.daniloff.adanagramlite.proc.WordsHandlerImpl;

public class ButtonsInputActivity extends Activity implements OnClickListener, AnagramView {

	private final int TASKBUTTON_ID_PREFIX = 2013090800;
	private final int ANSWERBUTTON_ID_PREFIX = 2013090900;

	private LinearLayout wrapLayout;
	private LinearLayout taskLayout;

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
		setContentView(R.layout.activity_buttons_input);

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

	private void initializeViews() {

		wrapLayout = (LinearLayout) findViewById(R.id.wrap_layout);
		wrapLayout.setBackgroundColor(getResources().getColor(R.color.brown_light));

		taskLayout = (LinearLayout) findViewById(R.id.task_layout);

		buttonOK = (Button) findViewById(R.id.button_ok);
		buttonOK.setOnClickListener(this);

		buttonHint = (Button) findViewById(R.id.button_hint);
		buttonHint.setOnClickListener(this);

		buttonNext = (Button) findViewById(R.id.button_next);
		buttonNext.setOnClickListener(this);

		// taskTxt = (TextView) findViewById(R.id.view_task);
		// answerTxt = (EditText) findViewById(R.id.txt_answer);

		stepTxt = (TextView) findViewById(R.id.info_step);
		attemptTxt = (TextView) findViewById(R.id.info_attempt);
		scoreTxt = (TextView) findViewById(R.id.view_score);
		scoreTxt.setTextColor(Color.GREEN);
		recordTxt = (TextView) findViewById(R.id.view_record);
		recordTxt.setTextColor(Color.BLUE);

		// answerTxt.setOnKeyListener(new OnKeyListener() {
		//
		// @Override
		// public boolean onKey(View v, int keyCode, KeyEvent event) {
		// if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode ==
		// KeyEvent.KEYCODE_ENTER) {
		// submitAnswer();
		// return true;
		// } else {
		// return false;
		// }
		// }
		// });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buttons_input, menu);
		return true;
	}

	@Override
	public void showTask(String shuffledWord) {
		buttonHint.setEnabled(true);

		char[] wordChars = shuffledWord.toCharArray();
		Button[] taskButtons = new Button[wordChars.length];

		for (int i = 0; i < wordChars.length; i++) {
			taskButtons[i] = new Button(this);
			Button tB = taskButtons[i];
			// tB.setText(wordChars[i]);
			tB.setText(wordChars, i, 1);
			tB.setId(TASKBUTTON_ID_PREFIX + i);
			taskLayout.addView(tB);
		}

	}

	@Override
	public void appendChar(char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toast(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTextView(int viewID, String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEnable(int buttonID, boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateScoreColors(int score, int record) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMode(boolean godMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveToFinishView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
