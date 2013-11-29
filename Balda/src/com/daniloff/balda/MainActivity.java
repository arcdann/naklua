package com.daniloff.balda;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MainActivity extends Activity implements OnClickListener {

	private LinearLayout wordLayout;
	private TableLayout tableLayout;
	private LinearLayout keyboardLayout;
	private LinearLayout buttonsLayout;

	private LinearLayout adLayout;
	private AdView adView;
	private final String AD_PUBLISHER_ID = "a15288bcc48c40b";

	private final int X = 8;
	private final int Y = 6;
	// private final int ID_PREFIX = 499;

	private OnClickListener listener;
	private OnClickListener keyListener;

	private ConverterXYID conv;
	private WordsBench bench;
	private Button[][] buttons;

	private TextView wordView;
	private Button submitButton;
	private Button pauseButton;
	private Button newGameButton;
	private int xInput;
	private int yInput;
	private int xPrev;
	private int yPrev;

	private boolean canBeSubmitted;

	private DisplayMetrics metrics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Display display = getWindowManager().getDefaultDisplay();
		metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		wordLayout = (LinearLayout) findViewById(R.id.wordLayout);
		buttonsLayout=(LinearLayout) findViewById(R.id.keyboardLayout);
//		buttonsLayout.setVisibility(View.VISIBLE);
		adLayout = (LinearLayout) findViewById(R.id.adLayout);
		tableLayout = (TableLayout) findViewById(R.id.tableLayout);
		wordView = (TextView) findViewById(R.id.wordView);
		submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);
		newGameButton = (Button) findViewById(R.id.newGameButton);
		newGameButton.setOnClickListener(this);
		pauseButton = (Button) findViewById(R.id.pauseButton);
		pauseButton.setOnClickListener(this);

		adView = new AdView(this, AdSize.BANNER, AD_PUBLISHER_ID);
		adLayout.addView(adView);
		AdRequest adRequest = new AdRequest();
		adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		adRequest.addTestDevice("0437C9653026785E37E70C70B9B94957");
		adView.loadAd(adRequest);

		conv = new ConverterXYID();
		bench = new WordsBench();
		bench.setSheet(this);
		bench.prepareGame();
		bench.startGame();

		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				int x = conv.convertToX(id);
				int y = conv.convertToY(id);
				Cell cell = bench.cells[x][y];
				if (bench.isPutLetter()) {
					if (cell.isFillable()) {
						xInput = x;
						yInput = y;
						buttonsLayout.setVisibility(View.GONE);
						keyboardLayout.setVisibility(View.VISIBLE);
					}
				}
				if (bench.isWordDeclare()) {
					boolean verified = verifyCell(x, y);
					if (verified) {
						bench.getWordLetters().add(cell.getLetter());
						wordView.append(bench.getWordLetters().get(bench.getWordLetters().size() - 1));
						bench.cells[x][y].setChosen(true);
						// bench.matrix[x][y].setJustChosen(true);
						Button b = (Button) v;
						b.setTypeface(null, Typeface.BOLD);
						if (bench.cells[x][y].isRequired()) {
							canBeSubmitted = true;
						} else {
							b.setTextColor(Color.BLUE);
						}
					}
				}
			}
		};

		keyListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button key = (Button) v;
				String keyText = key.getText().toString();
				if (!keyText.equals("*")) {
					Button b = (Button) findViewById(conv.convertToID(xInput, yInput));
					bench.cells[xInput][yInput].setLetter(keyText);
					// b.setText(keyText);
					b.setText(bench.cells[xInput][yInput].getLetter());
					b.setTextColor(Color.RED);

					bench.cells[xInput][yInput].setRequired(true);
					bench.cells[xInput][yInput].setFilled(true);
					bench.cells[xInput][yInput].setFillable(false);
					bench.setFillabilityAround(xInput, yInput);

					bench.createWord();
				}
				keyboardLayout.setVisibility(View.GONE);
//				buttonsLayout.setVisibility(View.VISIBLE);
			}
		};

		createButtonsTable();
		createKeyboard();

	}

	protected boolean verifyCell(int x, int y) {
		boolean retBool = false;
		if (bench.getWordLetters().size() == 0) {
			retBool = true;
			xPrev = x;
			yPrev = y;
		} else {
			int diff = Math.abs(x - xPrev) + Math.abs(y - yPrev);
			if (diff == 1 && bench.cells[x][y].isFilled()) {
				retBool = true;
				xPrev = x;
				yPrev = y;
			}
		}
		if (!bench.cells[x][y].isFilled()) {
			retBool = false;
		}
		if (bench.cells[x][y].isChosen()) {
			retBool = false;
		}
		return retBool;
	}

	private void createKeyboard() {
		keyboardLayout = (LinearLayout) findViewById(R.id.keyboardLayout);
//		keyboardLayout.setBackgroundColor(Color.GRAY);
		keyboardLayout.setVisibility(View.GONE);

		LinearLayout.LayoutParams centerGravityParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		centerGravityParams.gravity = Gravity.CENTER;

		LinearLayout highKeysRowLayoyt = (LinearLayout) findViewById(R.id.highKeysRowLayoyt);
		LinearLayout middleKeysRowLayoyt = (LinearLayout) findViewById(R.id.middleKeysRowLayoyt);
		LinearLayout lowKeysRowLayoyt = (LinearLayout) findViewById(R.id.lowKeysRowLayoyt);

		highKeysRowLayoyt.setLayoutParams(centerGravityParams);
		middleKeysRowLayoyt.setLayoutParams(centerGravityParams);
		lowKeysRowLayoyt.setLayoutParams(centerGravityParams);

		char[] highRowLetters = new char[] { 'й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'х', 'ъ' };
		for (int i = 0; i < highRowLetters.length; i++) {
			Button key = createKey();
			key.setText(String.valueOf(highRowLetters[i]));
			highKeysRowLayoyt.addView(key);
		}
		char[] middleRowLetters = new char[] { 'ф', 'ы', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'ж', 'э' };
		for (int i = 0; i < middleRowLetters.length; i++) {
			Button key = createKey();
			key.setText(String.valueOf(middleRowLetters[i]));
			key.setLayoutParams(centerGravityParams);
			middleKeysRowLayoyt.addView(key);
		}
		char[] lowRowLetters = new char[] { 'я', 'ч', 'с', 'м', 'и', 'т', 'ь', 'б', 'ю', '*' };
		for (int i = 0; i < lowRowLetters.length; i++) {
			Button key = createKey();
			key.setText(String.valueOf(lowRowLetters[i]));
			lowKeysRowLayoyt.addView(key);
		}

	}

	public void defineButtonsSize(int wordLength) {

		int screenWidth = metrics.widthPixels;
		int wrapLayoutMarginH = 16;
		int buttonMarginH = 2;
		int buttonStroke = 1;
		int buttonPadding = 5;

		// buttonSize = (screenWidth - wrapLayoutMarginH * 2) / wordLength -
		// buttonMarginH * 2 - buttonStroke * 2;
		// if (buttonSize > BUTTON_MAX_SIZE) {
		// buttonSize = BUTTON_MAX_SIZE;
		// }
		// // System.out.println("buttonWidth: " + buttonSize);
		// textSize = buttonSize / 2 - buttonPadding;
	}

	@SuppressWarnings("deprecation")
	private Button createKey() {
		Button retKey = new Button(this);
		retKey.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_key));
		retKey.setWidth(36);// /////////////////////////////////////////////
		retKey.setOnClickListener(keyListener);
		return retKey;
	}

	@SuppressWarnings("deprecation")
	private void createButtonsTable() {
		buttons = new Button[X][Y];
		for (int y = 0; y < Y; y++) {
			TableRow tr = new TableRow(this);
			tr.setId(conv.convertToTablerowID(y));
			for (int x = 0; x < X; x++) {
				Button b = createButton(x, y);
				buttons[x][y] = b;
				b.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_cell));
				b.setWidth(48);// /////////////////
				b.setHeight(48);// /////////////////
				tr.addView(b);
			}
			tableLayout.addView(tr);
		}
		showTask();
	}

	public void showTask() {
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				Button b = (Button) findViewById(conv.convertToID(x, y));
				b.setText(bench.cells[x][y].getLetter());
			}
		}

	}

	private Button createButton(int x, int y) {
		Button retButton = new Button(this);
		retButton.setId(conv.convertToID(x, y));
		retButton.setOnClickListener(listener);
		return retButton;
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
		case R.id.submitButton:
			if (!canBeSubmitted) {
				break;
			} else {

				String word = wordView.getText().toString();
				canBeSubmitted=false;
				wordView.setText("");
				for (int y = 0; y < Y; y++) {
					for (int x = 0; x < X; x++) {
						Button b = (Button) findViewById(conv.convertToID(x, y));
						b.setTextColor(Color.BLACK);
						b.setTypeface(null, Typeface.NORMAL);
					}
				}
				bench.startRound();
			}
			break;
		case R.id.newGameButton:
			bench.startGame();
			createButtonsTable();
			break;

		default:
			break;
		}

	}

}
