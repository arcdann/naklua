package com.daniloff.the15puzzle;

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
	
	private final int X=4; 
	private final int Y=4; 
	private final int CHIP_ID_BASE=11040000;
	private final int CHIP_SIZE=96;
	
	private TableLayout gameBox;
	private Button newGameButton;
	private Button pauseButton;
	private TextView moveCountView;
	private TextView timeView;
	
	private OnClickListener listener;
	private int moveCount;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_box);
		
		gameBox=(TableLayout) findViewById(R.id.gameBoxTable);
		newGameButton=(Button) findViewById(R.id.buttonNewGame);
		newGameButton.setOnClickListener(this);
		pauseButton=(Button) findViewById(R.id.buttonPause);
		moveCountView= (TextView) findViewById(R.id.movesCountView);
		
		listener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				moveCount++;
				moveCountView.setText(""+moveCount);
				((Button) v).setTextColor(Color.RED);
				
			}
		};
		createGameBox();
		
	}

	private void createGameBox() {
		moveCount=0;
		moveCountView.setText(""+moveCount);
		for(int y=0;y<Y;y++){
			TableRow tr=new TableRow(this);
			tr.setId(CHIP_ID_BASE+1000+y*100);
			for(int x=0;x<X;x++){
				Button chip=createChip(x,y);
				tr.addView(chip);
			}
			gameBox.addView(tr);
		}
		
	}

	private Button createChip(int x, int y) {
		Button b=new Button(this);
		b.setWidth(CHIP_SIZE);
		b.setHeight(CHIP_SIZE);
		b.setTextSize(36);////////////
		b.setText(""+(y*X+x+1));
		b.setTypeface(null, Typeface.BOLD);
		if(x==X-1&&y==Y-1){
			b.setVisibility(View.GONE);
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
			///////////
			break;

		default:
			break;
		}
		
		
		
	}

	private void cleanGameBox() {
		for(int y=0;y<Y;y++){
			TableRow tr=(TableRow) findViewById(CHIP_ID_BASE+1000+y*100);
			gameBox.removeView(tr);
		}
		
	}

}
