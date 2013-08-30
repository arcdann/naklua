package com.daniloff.adanagramlite;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class EntryActivity extends Activity implements OnClickListener {

	private RadioGroup langSelect;
	private RadioButton selectEn;
	private RadioButton selectRu;
	private Button buttonOk;
	private CheckBox checkboxShowNextTime;
	private boolean showNextTime;
	public static String lang;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);

		selectEn = (RadioButton) findViewById(R.id.radio_en);
		selectEn.setOnClickListener(this);

		selectRu = (RadioButton) findViewById(R.id.radio_ru);
		selectRu.setOnClickListener(this);

		buttonOk = (Button) findViewById(R.id.buttonEntry_ok);
		buttonOk.setOnClickListener(this);

		checkboxShowNextTime = (CheckBox) findViewById(R.id.checkBoxEntry);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entry, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.radio_en:
			lang = "en";
			checkboxShowNextTime.setChecked(false);
			break;

		case R.id.radio_ru:
			lang = "ru";
			checkboxShowNextTime.setChecked(false);
			break;

		case R.id.buttonEntry_ok:
			Intent intent = new Intent(EntryActivity.this, ChoiceActivity.class);
			startActivity(intent);

			break;

		default:
			break;
		}

	}

}
