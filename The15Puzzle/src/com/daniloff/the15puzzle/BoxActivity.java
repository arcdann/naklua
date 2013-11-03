package com.daniloff.the15puzzle;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class BoxActivity extends Activity {
	
	private final int X=4; 
	private final int Y=4; 
	private final int CHIP_ID_BASE=11040000;
	
	private TableLayout GameBox;
	private Button newGameButton;
	private Button pauseButton;
	private TextView moveCount;
	private TextView timeView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_box);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.box, menu);
		return true;
	}

}
