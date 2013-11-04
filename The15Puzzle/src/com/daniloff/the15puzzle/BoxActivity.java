package com.daniloff.the15puzzle;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BoxActivity extends Activity implements OnClickListener {

	private final int X = 4;
	private final int Y = 4;
	private final int CHIP_ID_PREXIX = 1104;
	private final int ROW_ID_PREXIX = 1105;
	private final int CHIP_SIZE = 96;

	private TableLayout gameBox;
	private Button newGameButton;
	private Button pauseButton;
	private TextView moveCountView;
	private TextView timeView;

	private TextView infoView;

	private OnClickListener listener;
	private int moveCount;
	private final String BLANC = "16";
	private Button[][] chips;
	private int[][] cells;// ///////////////////////////////////

	private int emptyX;
	private int emptyY;

	private boolean paused;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_box);

		gameBox = (TableLayout) findViewById(R.id.gameBoxTable);
		newGameButton = (Button) findViewById(R.id.buttonNewGame);
		newGameButton.setOnClickListener(this);
		pauseButton = (Button) findViewById(R.id.buttonPause);
		pauseButton.setOnClickListener(this);
		moveCountView = (TextView) findViewById(R.id.movesCountView);

		infoView = (TextView) findViewById(R.id.textView1);

		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (paused)
					return;
				moveCount++;
				moveCountView.setText("" + moveCount);
				int id = v.getId();

				int comingX = id % ((id / 10) * 10);
				int comingY = id % ((id / 100) * 100) / 10;

				if ((comingX == emptyX && Math.abs(comingY - emptyY) == 1)
						|| (comingY == emptyY && Math.abs(comingX - emptyX) == 1)) {
					// if (comingX == emptyX || comingY == emptyY) {

					swapButtons(v, comingX, comingY);
				}

			}

			private void swapButtons(View v, int comingX, int comingY) {
				Button bEmpty = (Button) findViewById(CHIP_ID_PREXIX * 100 + emptyY * 10 + emptyX);
				Button bComing = (Button) findViewById(CHIP_ID_PREXIX * 100 + comingY * 10 + comingX);
				String buf = bEmpty.getText().toString();
				bEmpty.setText(bComing.getText().toString());
				bComing.setText(buf);

				bEmpty.setVisibility(View.VISIBLE);
				emptyX = comingX;
				emptyY = comingY;
				((Button) v).setVisibility(View.INVISIBLE);
			}
		};
		createGameBox();

	}

	private void createGameBox() {
		emptyX = X - 1;
		emptyY = Y - 1;
		moveCount = 0;
		paused = false;
		moveCountView.setText("" + moveCount);
		pauseButton.setText(R.string.buttonPause_text_Pause);

		chips = new Button[X][Y];

		for (int y = 0; y < Y; y++) {
			TableRow tr = new TableRow(this);
			tr.setId(ROW_ID_PREXIX * 100 + y * 10);
			for (int x = 0; x < X; x++) {
				chips[x][y] = createChip(x, y);
				tr.addView(chips[x][y]);
			}
			gameBox.addView(tr);
		}

	}

	private Button createChip(int x, int y) {
		Button b = new Button(this);
		b.setWidth(CHIP_SIZE);
		b.setHeight(CHIP_SIZE);
		b.setTextSize(16);// //////////
		b.setText("" + (y * X + x + 1));
		b.setId(CHIP_ID_PREXIX * 100 + y * 10 + x);
		b.setTypeface(null, Typeface.BOLD);
		if (b.getText().equals(BLANC)) {
			b.setVisibility(View.INVISIBLE);
		}
		b.setOnClickListener(listener);
		return b;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.box, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonNewGame:
			cleanGameBox();
			createGameBox();
			break;
		case R.id.buttonPause:
			if (!paused) {
				paused = true;
				pauseButton.setText(R.string.buttonPause_text_Resume);
				for (int y = 0; y < Y; y++) {
					for (int x = 0; x < X; x++) {
						Button b = (Button) findViewById(CHIP_ID_PREXIX * 100 + y * 10 + x);
						b.setTextColor(Color.GRAY);
					}
				}
			} else {
				paused = false;
				pauseButton.setText(R.string.buttonPause_text_Pause);
				for (int y = 0; y < Y; y++) {
					for (int x = 0; x < X; x++) {
						Button b = (Button) findViewById(CHIP_ID_PREXIX * 100 + y * 10 + x);
						b.setTextColor(Color.BLACK);
					}
				}
			}
			break;

		default:
			break;
		}

	}

	private void cleanGameBox() {
		for (int y = 0; y < Y; y++) {
			TableRow tr = (TableRow) findViewById(ROW_ID_PREXIX * 100 + y * 10);
			gameBox.removeView(tr);
		}

	}

}
