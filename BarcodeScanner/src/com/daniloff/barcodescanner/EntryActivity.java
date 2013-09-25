package com.daniloff.barcodescanner;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class EntryActivity extends Activity {

	private EditText editText;
	private TextView infoText;

	private List<String> validCodes;

	// private String legalPincode = "144009";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);

		infoText = (TextView) findViewById(R.id.entry_infoView);

		editText = (EditText) findViewById(R.id.editText1);
		
		Context context=getBaseContext();

		validCodes = StringUtils.receiveWords(context, R.raw.valid_pincodes);

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		editText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
					analizePincode();
					return true;
				} else {
					return false;
				}
			}
		});
	}

	protected void analizePincode() {
		String enterdePin = editText.getText().toString();
		editText.setText("");
		// if (enterdePin.equals(legalPincode)) {
		if (validCodes.contains(enterdePin)) {

			String currentDate = (String) DateFormat.format("yyyy-MM-dd", new Date());

			infoText.setText("" + currentDate);

			moveToScannerActivity(enterdePin, currentDate);
		} else {
			infoText.setText("Pincode incorrect");
		}

	}

	private void moveToScannerActivity(String enteredPin, String currentDate) {
		Intent intent = new Intent(EntryActivity.this, ScannerActivity.class);
		intent.putExtra("pin", enteredPin);
		intent.putExtra("date", currentDate);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entry, menu);
		return true;
	}

}
