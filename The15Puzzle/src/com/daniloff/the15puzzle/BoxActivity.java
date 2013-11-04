package com.daniloff.the15puzzle;

import android.R.color;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
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

	private int xEmpty = X - 1;
	private int yEmpty = Y - 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_box);

		gameBox = (TableLayout) findViewById(R.id.gameBoxTable);
		newGameButton = (Button) findViewById(R.id.buttonNewGame);
		newGameButton.setOnClickListener(this);
		pauseButton = (Button) findViewById(R.id.buttonPause);
		moveCountView = (TextView) findViewById(R.id.movesCountView);

		infoView = (TextView) findViewById(R.id.textView1);

		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				moveCount++;
				moveCountView.setText("" + moveCount);
				int id = v.getId();

				int xClicked = id % ((id / 10) * 10);
				int yClicked = id % ((id / 100) * 100) / 10;

				if ((xClicked == xEmpty&&Math.abs(yClicked-yEmpty)==1) ||( yClicked == yEmpty &&Math.abs(xClicked-xEmpty)==1)){
					Button bDon=(Button) findViewById(CHIP_ID_PREXIX * 100 + yEmpty * 10 + xEmpty);
					Button bAcc=(Button) findViewById(CHIP_ID_PREXIX * 100 + yClicked * 10 + xClicked);
					String buf=bDon.getText().toString();
					bDon.setText((CharSequence) ((TextView) findViewById(CHIP_ID_PREXIX * 100 + yClicked * 10 + xClicked)).getText().toString());
					bAcc.setText(buf);
					bDon.setVisibility(View.VISIBLE);
					xEmpty = xClicked;
					yEmpty = yClicked;
					infoView.setText(v.getId() + " x=" + xClicked + " y=" + yClicked);
					((Button) v).setVisibility(View.INVISIBLE);
				}
//				if(xClicked==xEmpty){
//					int numX=xEmpty-xClicked;
//					if(numX>0)
//					for (int i=xEmpty;i<xClicked;i--){
//						
//					}
//					
//				}

			}
		};
		createGameBox();

	}

	private void createGameBox() {
		moveCount = 0;
		moveCountView.setText("" + moveCount);

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
			// /////////
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
