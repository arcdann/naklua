package com.daniloff.chipspuzzle;

import com.daniloff.the15puzzle.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity implements OnClickListener {
	private int moves;
	private double timeSpent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		TextView stepsCountView = (TextView) findViewById(R.id.res_spentMoves);
		TextView timeView = (TextView) findViewById(R.id.textViewSpentTime);
		Button buttonOK = (Button) findViewById(R.id.buttonOK_res);
		buttonOK.setOnClickListener(this);

		moves = getIntent().getIntExtra("moveCount", 0);
		timeSpent = getIntent().getDoubleExtra("time", 0);

		stepsCountView.setText(String.valueOf(moves));
		timeView.setText(String.valueOf(timeSpent));
		timeView.setText(String.format("%3.1f", timeSpent));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent(ResultActivity.this,BoxActivity.class);
		startActivity(intent);
	}

}
