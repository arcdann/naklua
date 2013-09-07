package com.daniloff.adanagramlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends Activity implements OnClickListener {

	private Button buttonOK;
	private int score;
	private int record;
	private TextView textScore;
	private TextView textRecord;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish);

		buttonOK = (Button) findViewById(R.id.button_finish);
		buttonOK.setOnClickListener(this);

		score = getIntent().getIntExtra("score", 0);
		record = getIntent().getIntExtra("record", 0);

		textScore = (TextView) findViewById(R.id.textScore);
		textRecord = (TextView) findViewById(R.id.textRecord);

		updateMessage();

	}

	private void updateMessage() {
		textScore.setText("Your score is " + score + "!");
		if (score < record) {
			textRecord.setText("The record is " + record);
		} else {
			textRecord.setText("It's a RECORD!");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.finish, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		moveToChoice();
	}

	public void onBackPressed() {
		moveToChoice();
	}

	private void moveToChoice() {
		Intent intent = new Intent(FinishActivity.this, ChoiceActivity.class);
		startActivity(intent);
	}

}
