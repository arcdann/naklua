package com.daniloff.adanagramlite;

import java.util.Locale;

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
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class ChoiceActivity extends Activity implements OnClickListener {

	private Button buttonNewGame;
	private Button buttonResumeGame;
	private Button buttonRules;
	private Button buttonAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);

		AdBuddiz.getInstance().cacheAds(this);

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

		boolean resuming = GlobalInvoke.paramsHandler.loadParamBoolean("PARAM_NAME_RESUMING");
		buttonResumeGame.setEnabled(resuming);

	}

	private void createAlertDialog() {
		String locale = Locale.getDefault().getDisplayLanguage();
		int defaultItem = 0;
		if (locale.substring(0, 2).equalsIgnoreCase("RU")) {
			defaultItem = 1;
		}
		new AlertDialog.Builder(getApplicationContext());
		String title = getString(R.string.selectLanguage);
		String buttonOkTitle = "OK";
		String[] items = { getString(R.string.selectEnglish), getString(R.string.selectRussian) };

		AlertDialog.Builder ad = new AlertDialog.Builder(this);

		ad.setTitle(title);
		ad.setSingleChoiceItems(items, defaultItem, null)
				.setPositiveButton(buttonOkTitle, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
						String lang = "en";
						if (selectedPosition == 1) {
							lang = "ru";
						}

						GlobalInvoke.paramsHandler.saveParamBoolean("PARAM_NAME_RESUMING", true);
						Intent intentNewGame = new Intent(ChoiceActivity.this, ButtonsInputActivity.class);
						intentNewGame.putExtra("button", "newGame");
						intentNewGame.putExtra("lang", lang);

						startActivity(intentNewGame);
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
			Intent intentResume = new Intent(ChoiceActivity.this, ButtonsInputActivity.class);
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
