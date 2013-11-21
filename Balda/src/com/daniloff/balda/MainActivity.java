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

public class MainActivity extends Activity {

	private LinearLayout wordLayout;
	private TableLayout tableLayout;
	private LinearLayout keyboardLayout;
	
	private LinearLayout adLayout;
	private AdView adView;
	private final String AD_PUBLISHER_ID = "a15288bcc48c40b";

	// private char[] initWord = new char[] { 'м', 'у', 'д', 'а', 'к' };

	private final int X = 5;
	private final int Y = 5;
	private final int ID_PREFIX = 2011;

	private OnClickListener listener;
	private OnClickListener keyListener;

	private WordsBench bench;
	private Button[][] buttons;

	private TextView infoView;
	private int xInput = -1;
	private int yInput = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wordLayout = (LinearLayout) findViewById(R.id.wordLayout);
		adLayout = (LinearLayout) findViewById(R.id.adLayout);
		tableLayout = (TableLayout) findViewById(R.id.tableLayout);
		infoView = (TextView) findViewById(R.id.textView1);

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
				if (bench.matrix[x][y].isFillable()) {
					
					xInput=x;
					yInput=y;
					keyboardLayout.setVisibility(View.VISIBLE);

	//				Button b = (Button) findViewById(v.getId());
	//				b.setBackgroundColor(Color.GRAY);

	//				bench.matrix[x][y].setLetter("X");
//					bench.matrix[x][y].setFilled(true);
//					bench.setFillabilityAround(x, y);
//					b.setText(bench.matrix[x][y].getLetter());
				}
			}
		};
		
		keyListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button key=(Button)v;
				String keyText=key.getText().toString();
				infoView.setText(keyText);

				Button b=(Button) findViewById(( ID_PREFIX * 1000 + 100 + yInput)* 1000 + 100 + xInput);
				bench.matrix[xInput][yInput].setLetter(keyText);
				//			b.setText(keyText);
				b.setText(bench.matrix[xInput][yInput].getLetter());
				
				bench.matrix[xInput][yInput].setFilled(true);
				bench.matrix[xInput][yInput].setFillable(false);
				bench.setFillabilityAround(xInput, yInput);
				
				xInput=-1;
				yInput=-1;
				
				keyboardLayout.setVisibility(View.GONE);

				
				
			}
		};
		
		createTable();
		createKeyboard();

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

}
