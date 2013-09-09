package com.daniloff.adanagramlite;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daniloff.adanagramlite.proc.StringUtils;
import com.daniloff.adanagramlite.proc.WordsHandler;
import com.daniloff.adanagramlite.proc.WordsHandlerImpl;

public class ButtonsInputActivity extends Activity implements OnClickListener, OnLongClickListener, AnagramView {

	private final int TASKBUTTON_ID_PREFIX = 20201000;
	private final int ANSWERBUTTON_ID_PREFIX = 20202000;
	private final int BUTTON_SUZE = 48;

	private LinearLayout wrapLayout;
	private LinearLayout taskLayout;
	private LinearLayout answerLayout;

	private TextView taskTxt;
	private TextView stepTxt;
	private TextView attemptTxt;
	private TextView scoreTxt;
	private TextView recordTxt;
	private TextView godModeTxt;
	private Button buttonHint;
	private Button buttonNext;
	// private String answerTxt;

	private OnClickListener listener;

	private WordsHandler wordsHandler;

	private List<Button> taskButtons;
	private List<Button> answerButtons;
	private List<String> answerLetters;
	private List<String> taskLetters;

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

		taskButtons = new ArrayList<Button>();
		answerButtons = new ArrayList<Button>();
		answerLetters = new ArrayList<String>();

		listenButton();

		wordsHandler.startLevel();

	}

	private void initializeViews() {

		wrapLayout = (LinearLayout) findViewById(R.id.wrap_layout);
		wrapLayout.setBackgroundColor(getResources().getColor(R.color.brown_light));

		taskLayout = (LinearLayout) findViewById(R.id.task_layout);
		answerLayout = (LinearLayout) findViewById(R.id.answer_layout);

		buttonHint = (Button) findViewById(R.id.button_hint);
		buttonHint.setOnClickListener(this);
		buttonHint.setOnLongClickListener(this);

		buttonNext = (Button) findViewById(R.id.button_next);
		buttonNext.setOnClickListener(this);

		stepTxt = (TextView) findViewById(R.id.info_step);
		attemptTxt = (TextView) findViewById(R.id.info_attempt);
		scoreTxt = (TextView) findViewById(R.id.view_score);
		scoreTxt.setTextColor(Color.GREEN);
		recordTxt = (TextView) findViewById(R.id.view_record);
		recordTxt.setTextColor(Color.BLUE);

	}

	@Override
	public void showTask(String shuffledWord) {

		buttonHint.setEnabled(true);

		taskLetters = StringUtils.wordToLetters(shuffledWord);
		taskButtons = new ArrayList<Button>();

		for (int i = 0; i < taskLetters.size(); i++) {
			Button taskButton = new Button(this);
			taskButton.setWidth(BUTTON_SUZE);
			taskButton.setHeight(BUTTON_SUZE);
			taskButton.setText(taskLetters.get(i));
			taskButton.setId(TASKBUTTON_ID_PREFIX + i);
			taskButton.setOnClickListener(listener);

			taskButtons.add(taskButton);
			taskLayout.addView(taskButton);
		}

		stepTxt.setText("step: " + wordsHandler.getStep() + "/" + wordsHandler.getParams().getStepsLimit());
		attemptTxt.setText("attempt: " + wordsHandler.getAttempt() + "/" + wordsHandler.getParams().getAttemptLimit());
		recordTxt.setText("record: " + wordsHandler.getRecord());

	}

	private void listenButton() {
		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				int id = v.getId();
				int idPrefix = id / 1000 * 1000;

				if (idPrefix == TASKBUTTON_ID_PREFIX) {
					handleTaskButtonStress(id);
					taskLayout.removeView(v);
				}
				if (idPrefix == ANSWERBUTTON_ID_PREFIX) {
					handleAnswerButtonStress(id);
					answerLayout.removeView(v);
				}
			}
		};
	}

	private void handleTaskButtonStress(int id) {

		int index = id % TASKBUTTON_ID_PREFIX;

		Button answerButton = new Button(this);
		answerButton.setWidth(BUTTON_SUZE);
		answerButton.setHeight(BUTTON_SUZE);
		answerLetters.add(taskLetters.get(index));
		answerButtons.add(answerButton);
		answerButton.setText(taskLetters.get(index));
		answerButton.setId(ANSWERBUTTON_ID_PREFIX + answerLetters.size() - 1);
		answerButton.setOnClickListener(listener);
		answerLayout.addView(answerButton);

		taskButtons.remove(index);
		taskLetters.remove(index);

		for (int i = 0; i < taskButtons.size(); i++) {
			taskButtons.get(i).setId(TASKBUTTON_ID_PREFIX + i);
		}

		if (taskLetters.size() == 0) {
			submitAnswer(answerLetters);
		}

	}

	private void submitAnswer(List<String> letters) {

		String answer = StringUtils.lettersToWord(letters);
		clearAllFields();

		wordsHandler.analyzeAnswer(answer);

	}

	private void clearAllFields() {

		answerLetters.removeAll(answerLetters);
		answerButtons.removeAll(answerButtons);
		answerLayout.removeAllViews();

		taskLetters.removeAll(answerLetters);
		taskButtons.removeAll(answerButtons);
		taskLayout.removeAllViews();

	}

	private void inputHintedWord() {
		// TODO Auto-generated method stub

	}

	private void handleAnswerButtonStress(int id) {

		int index = id % ANSWERBUTTON_ID_PREFIX;

		Button taskButton = new Button(this);
		taskButton.setWidth(BUTTON_SUZE);
		taskButton.setHeight(BUTTON_SUZE);
		taskLetters.add(answerLetters.get(index));
		taskButtons.add(taskButton);
		taskButton.setText(answerLetters.get(index));
		taskButton.setId(TASKBUTTON_ID_PREFIX + taskLetters.size() - 1);
		taskButton.setOnClickListener(listener);
		taskLayout.addView(taskButton);

		answerLetters.remove(index);
		answerButtons.remove(index);

		for (int i = 0; i < answerButtons.size(); i++) {
			answerButtons.get(i).setId(ANSWERBUTTON_ID_PREFIX + i);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buttons_input, menu);
		return true;
	}

	@Override
	public void appendChar(char c) {
		// TODO Auto-generated method stub

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
		switch (v.getId()) {
		case R.id.button_next:
			clearAllFields();
			wordsHandler.nextWord();
			break;

		case R.id.button_hint:
			if (wordsHandler.isGodMode()) {
				// wordsHandler.inputWholeWord();
				inputHintedWord();
			} else {
				int charsCount = answerLetters.size();
				wordsHandler.hint(charsCount + 1);// позиция символа в строке
			}
			break;
		}

	}

	@Override
	public boolean onLongClick(View v) {
//
		return false;
	}

}
