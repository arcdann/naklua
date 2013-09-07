package com.daniloff.adanagramlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.daniloff.adanagramlite.proc.ParamsHandler;

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

		GlobalInvoke.paramsHandler = new ParamsHandler();
		GlobalInvoke.paramsHandler.setView(this);
		GlobalInvoke.paramsHandler.setContext(getBaseContext());

	}

	private void createAlertDialog() {
		new AlertDialog.Builder(getApplicationContext());
		String buttonOkTitle = "OK";
		String[] items = { "English", "Русский" };

		new AlertDialog.Builder(this).setSingleChoiceItems(items, 0, null)
				.setPositiveButton(buttonOkTitle, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
						String lang = "en";
						if (selectedPosition == 1) {
							lang = "ru";
						}

						GlobalInvoke.paramsHandler.saveParamBoolean("PARAM_NAME_RESUMING", true);
						Intent intentNewGame = new Intent(ChoiceActivity.this, MainActivity.class);
						intentNewGame.putExtra("button", "newGame");
						intentNewGame.putExtra("lang", lang);

						startActivity(intentNewGame);// /lang
					}
				}).show();

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
			createAlertDialog();
			break;

		case R.id.button_resumeGame:
			Intent intentResume = new Intent(ChoiceActivity.this, MainActivity.class);
			intentResume.putExtra("button", "resume");
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
