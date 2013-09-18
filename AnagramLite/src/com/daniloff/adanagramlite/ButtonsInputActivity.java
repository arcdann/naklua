package com.daniloff.adanagramlite;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.daniloff.adanagramlite.proc.StringUtils;
import com.daniloff.adanagramlite.proc.WordsHandler;
import com.daniloff.adanagramlite.proc.WordsHandlerImpl;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

@SuppressLint("DefaultLocale")
public class ButtonsInputActivity extends Activity implements OnClickListener, OnLongClickListener, AnagramView {

	private final int BUTTON_MAX_SIZE = 72;
	private int textSize;

	private final int BUTTON_ID_PREFIX = 1216000;

	private LinearLayout wrapLayout;
	private LinearLayout taskLayout;
	private LinearLayout answerLayout;

	private TextView stepTxt;
	private TextView attemptTxt;
	private TextView scoreTxt;
	private TextView recordTxt;
	private Button buttonHint;
	private Button buttonNext;

	private OnClickListener listener;

	private WordsHandler wordsHandler;

	private List<Button> taskButtonsList;
	private List<Button> answerButtonsList;
	private List<String> answerLettersList;
	private List<String> taskLettersList;
	private int hintRemain;
	private EditText magicText;
	private LayoutParams buttonsLayoutParams;
	private DisplayMetrics metrics;
	private int buttonSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buttons_input);

		AdBuddiz.getInstance().onStart(this);

		initializeViews();

		Display display = getWindowManager().getDefaultDisplay();
		metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		wordsHandler = new WordsHandlerImpl();
		wordsHandler.setView(this);
		wordsHandler.setContext(getBaseContext());

		String pressedButton = getIntent().getStringExtra("button");
		if (pressedButton.equals("newGame")) {
			wordsHandler.setLang(getIntent().getStringExtra("lang"));
		} else {
			wordsHandler.setResumed(true);
		}

		taskButtonsList = new ArrayList<Button>();
		answerButtonsList = new ArrayList<Button>();
		answerLettersList = new ArrayList<String>();

		listenButton();

		wordsHandler.startLevel();
	}

	@Override
	public void defineButtonsSize(int wordLength) {

		int screenWidth = metrics.widthPixels;
		int wrapLayoutMarginH = 16;
		int buttonMarginH = 2;
		int buttonStroke = 1;
		int buttonPadding=5;

		buttonSize = (screenWidth - wrapLayoutMarginH * 2) / wordLength - buttonMarginH * 2 - buttonStroke * 2;
		if(buttonSize>BUTTON_MAX_SIZE){
			buttonSize=BUTTON_MAX_SIZE;
		}
		System.out.println("buttonWidth: " + buttonSize);
		textSize=buttonSize/2-buttonPadding;
	}

	private void initializeViews() {

		wrapLayout = (LinearLayout) findViewById(R.id.wrap_layout);
		wrapLayout.setBackgroundColor(getResources().getColor(R.color.brown_light));

		taskLayout = (LinearLayout) findViewById(R.id.task_layout);
		answerLayout = (LinearLayout) findViewById(R.id.answer_layout);

		buttonsLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		buttonsLayoutParams.setMargins(2, 0, 2, 0);

		buttonHint = (Button) findViewById(R.id.button_hint);
		buttonHint.setOnClickListener(this);
//		buttonHint.setOnLongClickListener(this);

		buttonNext = (Button) findViewById(R.id.button_next);
		buttonNext.setOnClickListener(this);

		stepTxt = (TextView) findViewById(R.id.info_step);
		attemptTxt = (TextView) findViewById(R.id.info_attempt);
		scoreTxt = (TextView) findViewById(R.id.view_score);
		scoreTxt.setTextColor(Color.GREEN);
		recordTxt = (TextView) findViewById(R.id.view_record);
		recordTxt.setTextColor(Color.BLUE);
		magicText = (EditText) findViewById(R.id.magicWord);
	}

	@Override
	public void applyNewTaskParams() {

		hintRemain = wordsHandler.getParams().getHintLimit();
		buttonHint.setText("Hint (" + hintRemain + ")");
		buttonHint.setEnabled(true);
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void showTask(String shuffledWord) {

		clearAnswerField();
		magicText.setText("");

		taskLettersList = StringUtils.wordToLetters(shuffledWord);
		taskButtonsList = new ArrayList<Button>();

		for (int i = 0; i < taskLettersList.size(); i++) {

			Button taskButton = createButton();

			taskButton.setText(taskLettersList.get(i).toUpperCase());
			taskButton.setTypeface(null, Typeface.BOLD);
			taskButton.setId(BUTTON_ID_PREFIX + i);
			taskButton.setOnClickListener(listener);

			taskButtonsList.add(taskButton);
			taskLayout.addView(taskButton);
		}
		stepTxt.setText("step: " + wordsHandler.getStep() + "/" + wordsHandler.getParams().getStepsLimit());
		attemptTxt.setText("attempt: " + wordsHandler.getAttempt() + "/" + wordsHandler.getParams().getAttemptLimit());
		recordTxt.setText("record: " + wordsHandler.getRecord());
	}

	@SuppressWarnings("deprecation")
	private Button createButton() {
		Button retButton = new Button(this);
		retButton.setLayoutParams(buttonsLayoutParams);

		retButton.setWidth(buttonSize);
		retButton.setHeight(buttonSize);

		retButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_button));
		retButton.setIncludeFontPadding(false);
		retButton.setTextSize(textSize);

		return retButton;
	}

	private void listenButton() {

		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				int id = v.getId();

				String field;
				if (id % BUTTON_ID_PREFIX / 100 > 0) {
					field = "answer";
				} else {
					field = "task";
				}

				if (field.equals("task")) {
					handleTaskButtonStress(id);
				}
				if (field.equals("answer")) {
					handleAnswerButtonStress(id);
				}
			}
		};
	}

	@SuppressLint("DefaultLocale")
	private void handleTaskButtonStress(int id) {

		int index = id % BUTTON_ID_PREFIX;

		taskButtonsList.get(index).setVisibility(View.INVISIBLE);

		answerLettersList.add(taskLettersList.get(index));

		Button answerButton = createButton();

		answerButton.setText(taskLettersList.get(index).toUpperCase());
		answerButton.setTypeface(null, Typeface.BOLD);
		answerButton.setId(id * 1000 + 100 + answerLettersList.size() - 1);

		answerButton.setOnClickListener(listener);
		answerButtonsList.add(answerButton);

		answerLayout.addView(answerButton);

		if (answerLettersList.size() >= taskLettersList.size()) {
			submitAnswer();
		}
	}

	private void submitAnswer() {

		String answer = StringUtils.lettersToWord(answerLettersList);
		clearTaskField();
		wordsHandler.analyzeAnswer(answer);
	}

	private void clearTaskField() {

		taskLettersList.removeAll(answerLettersList);
		taskButtonsList.removeAll(answerButtonsList);
		taskLayout.removeAllViews();
	}

	private void clearAnswerField() {

		answerLettersList.removeAll(answerLettersList);
		answerButtonsList.removeAll(answerButtonsList);
		answerLayout.removeAllViews();
	}

	private void handleAnswerButtonStress(int id) {

		int index = (id % BUTTON_ID_PREFIX % 10);

		for (int i = (answerLettersList.size() - 1); i >= index; i--) {

			int taskLellersIndex = answerButtonsList.get(i).getId() % BUTTON_ID_PREFIX / 1000;
			taskButtonsList.get(taskLellersIndex).setVisibility(View.VISIBLE);

			answerLettersList.remove(i);
			answerButtonsList.remove(i);
			answerLayout.removeViewAt(i);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buttons_input, menu);
		return true;
	}

	@Override
	public void toast(String message) {

		Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP, 0, 48);
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
			scoreTxt.setTextColor(getResources().getColor(R.color.brown_deep));
		} else {
			scoreTxt.setTextColor(Color.RED);
		}
	}

	@Override
	public void updateMode(boolean magicMode) {
		magicText.setText("");
		magicText.setBackgroundColor(getResources().getColor(R.color.brown_light));
		magicText.setTextColor(getResources().getColor(R.color.purple));
		magicText.setHint("Magic Mode");
	}

	@Override
	public void moveToFinishView() {

		Intent intent = new Intent(ButtonsInputActivity.this, FinishActivity.class);
		intent.putExtra("score", wordsHandler.getScore());
		intent.putExtra("record", wordsHandler.getRecord());

		startActivity(intent);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_next:
			clearTaskField();
			wordsHandler.nextWord();
			break;

		case R.id.button_hint:
			if (!wordsHandler.isMagicMode()) {
				if (taskLettersList.size() > answerLettersList.size() + 1) {
					hintRemain--;
					buttonHint.setText("Hint (" + hintRemain + ")");

					if (hintRemain < 1) {
						buttonHint.setEnabled(false);
					}

					String askHint = StringUtils.lettersToWord(answerLettersList);
					wordsHandler.hint(askHint);
				}
			} else {
				wordsHandler.hint("");
				magicText.setSelection(magicText.length());
			}
		}
	}

	@Override
	public boolean onLongClick(View v) {

		if (wordsHandler.isMagicMode()) {
			wordsHandler.toggleMagicMode();
		}

		magicText.setVisibility(View.VISIBLE);
		magicText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

					wordsHandler.analyzeAnswer("*" + magicText.getText().toString() + "!!!");
					return true;
				} else {
					return false;
				}
			}
		});
		return true;
	}

	@Override
	public void simulateAnswerButtonPress(int firstWrongLetterIndex) {
		if (firstWrongLetterIndex >= 0) {
			handleAnswerButtonStress(answerButtonsList.get(firstWrongLetterIndex).getId());
		}
	}

	@Override
	public void simulateTaskButtonPress(int pressableTaskButtonIndex) {
		if (pressableTaskButtonIndex >= 0) {
			handleTaskButtonStress(taskButtonsList.get(pressableTaskButtonIndex).getId());

			for (int i = 0; i < answerButtonsList.size(); i++) {
				answerButtonsList.get(i).setTextColor(getResources().getColor(R.color.dark_blue));
			}
		}
	}

	@Override
	public boolean[] getTaskButtonVisibility() {
		boolean[] retVizArray = new boolean[taskButtonsList.size()];
		for (int i = 0; i < taskButtonsList.size(); i++) {
			int viz = taskButtonsList.get(i).getVisibility();
			if (viz == View.VISIBLE) {
				retVizArray[i] = true;
			} else {
				retVizArray[i] = false;
			}
		}
		return retVizArray;
	}

	@Override
	public void closeMagicTextView() {
		magicText.setVisibility(View.GONE);
	}

	@Override
	public void showAd() {
		// AdBuddiz.getInstance().onStart(this);
	}



}
