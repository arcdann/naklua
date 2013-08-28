package com.daniloff.adanagramlite;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChoiceActivity extends Activity implements OnClickListener {

	private Button buttonNewGame;
	private Button buttonResumeGame;
	private Button buttonRules;
	private Button buttonAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);

		buttonNewGame = (Button) findViewById(R.id.button_newGame);
		buttonResumeGame = (Button) findViewById(R.id.button_resumeGame);
		buttonRules = (Button) findViewById(R.id.button_rules);
		buttonAbout = (Button) findViewById(R.id.button_about);

		buttonNewGame.setOnClickListener(this);
		buttonResumeGame.setOnClickListener(this);
		buttonRules.setOnClickListener(this);
		buttonAbout.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choice, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_newGame:
			Intent intentNewGame = new Intent(ChoiceActivity.this, MainActivity.class);
			startActivity(intentNewGame);
			break;

		case R.id.button_resumeGame:
			Intent intentResume = new Intent(ChoiceActivity.this, MainActivity.class);
			intentResume.putExtra("resumed", true);
			startActivity(intentResume);
			break;

		case R.id.button_rules:
			Intent intentRules = new Intent(ChoiceActivity.this, RulesActivity.class);
			startActivity(intentRules);
			break;

		case R.id.button_about:
			Intent intentAbout = new Intent(ChoiceActivity.this, AboutActivity.class);
			startActivity(intentAbout);
			break;

		default:
			break;
		}
	}

	public void onBackPressed() {
		finish();
	}
}
