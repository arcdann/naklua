package com.daniloff.the15puzzle;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BoxActivity extends Activity {
	
	private final int X=4; 
	private final int Y=4; 
	private final int CHIP_ID_BASE=11040000;
	private final int CHIP_SIZE=96;
	
	private TableLayout gameBox;
	private Button newGameButton;
	private Button pauseButton;
	private TextView moveCount;
	private TextView timeView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_box);
		
		gameBox=(TableLayout) findViewById(R.id.gameBoxTable);
		newGameButton=(Button) findViewById(R.id.buttonNewGame);
		pauseButton=(Button) findViewById(R.id.buttonPause);
		moveCount= (TextView) findViewById(R.id.textView3);
		
		createGameBox();
		
	}

	private void createGameBox() {
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
		b.setText(""+(y*X+x+1));
		b.setTypeface(null, Typeface.BOLD);
		if(x==X-1&&y==Y-1){
			b.setVisibility(View.GONE);
		}
		return b;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.box, menu);
		return true;
	}

}
