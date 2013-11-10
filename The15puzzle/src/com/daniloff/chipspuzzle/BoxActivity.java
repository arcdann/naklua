package com.daniloff.chipspuzzle;

import java.util.ArrayList;
import java.util.List;

import com.daniloff.the15puzzle.R;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

	private OnClickListener listener;
	private int moveCount;
	private final String BLANC = "16";
	private Button[][] chips;
//	private int[][] cells;// ///////////////////////////////////

	private int emptyX;
	private int emptyY;

	private boolean started;
	private boolean paused;
	private AnswerChecker answerChecker;
	TimeWatch timeWatch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_box);
		
	 answerChecker=new AnswerChecker();
	 answerChecker.start();

		gameBox = (TableLayout) findViewById(R.id.gameBoxTable);
		newGameButton = (Button) findViewById(R.id.buttonNewGame);
		newGameButton.setOnClickListener(this);
		pauseButton = (Button) findViewById(R.id.buttonPause);
		pauseButton.setOnClickListener(this);
		moveCountView = (TextView) findViewById(R.id.movesCountView);
		timeView = (TextView) findViewById(R.id.timeView);

		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (paused)
					return;
				if (!started) {
					started = true;
					startTimer();
				}
				int id = v.getId();

				int comingX = id % ((id / 10) * 10);
				int comingY = id % ((id / 100) * 100) / 10;

				// if (comingX == emptyX || comingY == emptyY) {
				// moveChips(comingX, comingY);
				// }
				if ((comingX == emptyX && Math.abs(comingY - emptyY) == 1)
						|| (comingY == emptyY && Math.abs(comingX - emptyX) == 1)) {

					moveCount++;
					moveCountView.setText("" + moveCount);
					swapButtons(comingX, comingY);
					((Button) v).setVisibility(View.INVISIBLE);
					
					List<String>answer=prepareAnswer();
					if(answerChecker.isRightAnswer(answer)){
						gameOver();
					}
				}

			}

			private void swapButtons(int comingX, int comingY) {
				Button bEmpty = (Button) findViewById(CHIP_ID_PREXIX * 100 + emptyY * 10 + emptyX);
				Button bComing = (Button) findViewById(CHIP_ID_PREXIX * 100 + comingY * 10 + comingX);
				String buf = bEmpty.getText().toString();
				bEmpty.setText(bComing.getText().toString());
				bComing.setText(buf);

				bEmpty.setVisibility(View.VISIBLE);
				emptyX = comingX;
				emptyY = comingY;

			}
		};
		createGameBox();

	}
	
	private void gameOver(){
		showResultActivity();
	}

	protected void showResultActivity() {
		Intent intentResult=new Intent(BoxActivity.this,ResultActivity.class);
		intentResult.putExtra("moveCount",moveCount);
		intentResult.putExtra("time",timeWatch.getTime());
		
		startActivity(intentResult);
	}

	protected List<String> prepareAnswer() {
		List<String> retList=new ArrayList<String>();
		for(int y=0;y<Y;y++){
			for(int x=0;x<X;x++){
				retList.add((String) ((TextView) findViewById(CHIP_ID_PREXIX * 100 + y * 10 + x)).getText());
			}
		}
//		System.out.println(retList);
		return retList;
	}

	private void startTimer() {
		timeWatch = new TimeWatch();
		timeWatch.setBox(this);
		Thread tw = new Thread(timeWatch);
		tw.setDaemon(true);
		tw.start();
	}

	protected void moveChips(int comingX, int comingY) {
		if (comingX == emptyX) {
			int stack = emptyY - comingY;
			if (stack > 0) {
				moveDown(stack);
			} else {
				moveUp(stack);
			}
		}
		if (comingY == emptyY) {
			int stack = emptyX - comingX;
			if (stack > 0) {
				moveRight(stack);
			} else {
				moveLeft(stack);
			}
		}

	}

	private void moveLeft(int stack) {
		// TODO Auto-generated method stub

	}

	private void moveRight(int stack) {
		// TODO Auto-generated method stub

	}

	private void moveUp(int stack) {
		// TODO Auto-generated method stub

	}

	private void moveDown(int stack) {
		// TODO Auto-generated method stub

	}

	private void createGameBox() {
		emptyX = X - 1;
		emptyY = Y - 1;
		moveCount = 0;
		started = false;
		paused = false;
		moveCountView.setText("" + moveCount);
		timeView.setText(String.format("%3.1f", 0.0));
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

	@SuppressWarnings("deprecation")
	private Button createChip(int x, int y) {
		Button b = new Button(this);
		b.setWidth(CHIP_SIZE);
		b.setHeight(CHIP_SIZE);
		b.setTextSize(16);// //////////
		b.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_button));
		b.setIncludeFontPadding(false);
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

			createAlertDialog();

			// cleanGameBox();
			// createGameBox();
			break;
		case R.id.buttonPause:
			Button emptyCell = (Button) findViewById(CHIP_ID_PREXIX * 100 + emptyY * 10 + emptyX);
			if (!paused) {
				paused = true;
				pauseButton.setText(R.string.buttonPause_text_Resume);
				for (int y = 0; y < Y; y++) {
					for (int x = 0; x < X; x++) {
						Button b = (Button) findViewById(CHIP_ID_PREXIX * 100 + y * 10 + x);
						b.setTextColor(color.darker_gray);
					}
				}
				emptyCell.setVisibility(View.VISIBLE);
			} else {
				paused = false;
				pauseButton.setText(R.string.buttonPause_text_Pause);
				for (int y = 0; y < Y; y++) {
					for (int x = 0; x < X; x++) {
						Button b = (Button) findViewById(CHIP_ID_PREXIX * 100 + y * 10 + x);
						b.setTextColor(Color.BLACK);
					}
				}
				emptyCell.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			break;
		}

	}

	private void createAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setTitle("Start new game?");
		builder.setMessage("Start new game?");
		builder.setCancelable(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						cleanGameBox();
						createGameBox();
					}
				});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void cleanGameBox() {
		for (int y = 0; y < Y; y++) {
			TableRow tr = (TableRow) findViewById(ROW_ID_PREXIX * 100 + y * 10);
			gameBox.removeView(tr);
		}

	}

	public boolean isStarted() {
		return started;
	}

	public boolean isPaused() {
		return paused;
	}

	public TextView getTimeView() {
		return timeView;
	}

	// public void setTimeView(TextView timeView) {
	// this.timeView = timeView;
	// }

	public void setTimeView(final String string) {
		// timeView = (TextView) findViewById(R.id.timeView);
		runOnUiThread(new Runnable() {
			public void run() {
				timeView.setText(string);
			}
		});
	}
}
