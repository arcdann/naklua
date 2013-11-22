package com.daniloff.balda;

import android.R.color;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
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

	private LinearLayout adLayout;
	private AdView adView;
	private final String AD_PUBLISHER_ID = "a15288bcc48c40b";

	private final int X = 8;
	private final int Y = 6;
	private final int ID_PREFIX = 2011;

	private OnClickListener listener;
	private OnClickListener keyListener;

	private WordsBench bench;
	private Button[][] buttons;

	private TextView wordView;
	private Button submitButton;
	private int xInput;
	private int yInput;
	private int xPrev;
	private int yPrev;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wordLayout = (LinearLayout) findViewById(R.id.wordLayout);
		adLayout = (LinearLayout) findViewById(R.id.adLayout);
		tableLayout = (TableLayout) findViewById(R.id.tableLayout);
		wordView = (TextView) findViewById(R.id.wordView);
		submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(this);

		adView = new AdView(this, AdSize.BANNER, AD_PUBLISHER_ID);
		adLayout.addView(adView);
		AdRequest adRequest = new AdRequest();
		adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		adRequest.addTestDevice("0437C9653026785E37E70C70B9B94957");
		adView.loadAd(adRequest);

		bench = new WordsBench();
		bench.setSheet(this);
		bench.startGame();

		listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				int x = id % (id / 100);
				int y = (id % (id / 100000) / 1000);
				Cell cell = bench.matrix[x][y];
				if (bench.isPutLetter()) {
					if (cell.isFillable()) {
						xInput = x;
						yInput = y;
						keyboardLayout.setVisibility(View.VISIBLE);
					}
				}
				if (bench.isWordDeclare()) {
					boolean verified = verifyCell(x, y);
					if (verified) {
						bench.getWordLetters().add(cell.getLetter());
						wordView.append(bench.getWordLetters().get(bench.getWordLetters().size() - 1));
						bench.matrix[x][y].setChosen(true);
//						bench.matrix[x][y].setJustChosen(true);
					}
				}
			}
		};

		keyListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button key = (Button) v;
				String keyText = key.getText().toString();

				Button b = (Button) findViewById((ID_PREFIX * 1000 + 100 + yInput) * 1000 + 100 + xInput);
				bench.matrix[xInput][yInput].setLetter(keyText);
				// b.setText(keyText);
				b.setText(bench.matrix[xInput][yInput].getLetter());
				b.setTextColor(Color.RED);

				bench.matrix[xInput][yInput].setFilled(true);
				bench.matrix[xInput][yInput].setFillable(false);
				bench.setFillabilityAround(xInput, yInput);

				bench.createWord();

				keyboardLayout.setVisibility(View.GONE);

			}
		};

		createTable();
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
			if (diff == 1&&bench.matrix[x][y].isFilled()) {
				retBool = true;
				xPrev = x;
				yPrev = y;
			}
		}
		if (!bench.matrix[x][y].isFilled()) {
			retBool = false;
		}
		if (bench.matrix[x][y].isChosen()) {
			retBool = false;
		}
		return retBool;
	}

	private void createKeyboard() {
		keyboardLayout = (LinearLayout) findViewById(R.id.keyboardLayout);
		keyboardLayout.setVisibility(View.GONE);
		LinearLayout highKeysRowLayoyt = (LinearLayout) findViewById(R.id.highKeysRowLayoyt);
		LinearLayout middleKeysRowLayoyt = (LinearLayout) findViewById(R.id.middleKeysRowLayoyt);
		LinearLayout lowKeysRowLayoyt = (LinearLayout) findViewById(R.id.lowKeysRowLayoyt);

		char[] highRowLetters = new char[] { 'й', 'ц', 'у', 'к', 'е', 'н', 'г', 'ш', 'щ', 'з', 'х', 'х', 'ъ' };
		for (int i = 0; i < highRowLetters.length; i++) {
			Button key = createKey();
			key.setText(String.valueOf(highRowLetters[i]));
			highKeysRowLayoyt.addView(key);
		}
		char[] middleRowLetters = new char[] { 'ф', 'ы', 'в', 'а', 'п', 'р', 'о', 'л', 'д', 'ж', 'э' };
		for (int i = 0; i < middleRowLetters.length; i++) {
			Button key = createKey();
			key.setText(String.valueOf(middleRowLetters[i]));
			middleKeysRowLayoyt.addView(key);
		}
		char[] lowRowLetters = new char[] { 'я', 'ч', 'с', 'м', 'и', 'т', 'ь', 'б', 'ю' };
		for (int i = 0; i < lowRowLetters.length; i++) {
			Button key = createKey();
			key.setText(String.valueOf(lowRowLetters[i]));
			lowKeysRowLayoyt.addView(key);
		}

	}

	private Button createKey() {
		Button retKey = new Button(this);
		retKey.setOnClickListener(keyListener);
		return retKey;
	}

	private void createTable() {
		for (int y = 0; y < Y; y++) {
			TableRow tr = new TableRow(this);
			int trID = ID_PREFIX * 1000 + 100 + y;
			tr.setId(trID);
			for (int x = 0; x < X; x++) {
				Button b = createButton(trID, x);
				// buttons[x][y]=b;
				tr.addView(b);
			}
			tableLayout.addView(tr);
		}
		// createTask();
		showTask();
	}

	public void showTask() {
		for (int x = 0; x < X; x++) {
			for (int y = 0; y < Y; y++) {
				Button b = (Button) findViewById((ID_PREFIX * 1000 + 100 + y) * 1000 + 100 + x);
				b.setText(bench.matrix[x][y].getLetter());
			}
		}

	}

	// private void createTask() {
	// int y = Y / 2;
	// for (int x = 0; x < X; x++) {
	// Button b = (Button) findViewById((ID_PREFIX * 1000 + 100 + y) * 1000 +
	// 100 + x);
	// b.setText(String.valueOf(initWord[x]));
	// }
	//
	// }

	private Button createButton(int trID, int x) {
		Button retButton = new Button(this);
		retButton.setId(trID * 1000 + 100 + x);
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
			String word=wordView.getText().toString();
			wordView.setText("");
			bench.startRound();
			break;

		default:
			break;
		}

	}

}
