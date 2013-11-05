package com.daniloff.barcodescanner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.daniloff.QrReader.QKActivity;

public class EntryActivity extends Activity implements OnClickListener {

	private final int PINCODE_LENGTH = 6;
	private final int BUTTON_ID_PREFIX = 1001000;

	private List<String> pincodeDigits = new ArrayList<String>();
	private TextView[] pincodeDigitViews = new TextView[PINCODE_LENGTH];
	private TextView infoText;
	private List<String> validCodes;
	private LayoutParams digViewLayoutParams;

	private int buttonSize = 128;
	private int textSize = 48;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);

		infoText = (TextView) findViewById(R.id.entry_infoView);
		infoText.setText("Input pincode");

		digViewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		digViewLayoutParams.setMargins(2, 2, 2, 2);

		createKeyboard();
		createDigitsPanel();

		Context context = getBaseContext();

		validCodes = StringUtils.receiveWords(context, R.raw.valid_pincodes);

	}

	private void createKeyboard() {
		TableLayout tl = (TableLayout) findViewById(R.id.buttonsTable);

		TableRow tr1 = new TableRow(this);
		tr1.setGravity(Gravity.CENTER_HORIZONTAL);
		int startIndex1 = 7;
		for (int i = 0; i < 3; i++) {
			tr1.addView(createButton(startIndex1 + i));
		}
		TableRow tr2 = new TableRow(this);
		tr2.setGravity(Gravity.CENTER_HORIZONTAL);
		int startIndex2 = 4;
		for (int i = 0; i < 3; i++) {
			tr2.addView(createButton(startIndex2 + i));
		}
		TableRow tr3 = new TableRow(this);
		tr3.setGravity(Gravity.CENTER_HORIZONTAL);
		int startIndex3 = 1;
		for (int i = 0; i < 3; i++) {
			tr3.addView(createButton(startIndex3 + i));
		}
		TableRow tr4 = new TableRow(this);
		tr4.setGravity(Gravity.CENTER_HORIZONTAL);
		tr4.addView(createButton(0));
		tr4.addView(createButton(10));
		tr4.addView(createButton(11));

		tl.addView(tr1);
		tl.addView(tr2);
		tl.addView(tr3);
		tl.addView(tr4);

	}

	private void createDigitsPanel() {
		LinearLayout digitsLayout = (LinearLayout) findViewById(R.id.digits);
		digitsLayout.setGravity(Gravity.CENTER);
		for (int i = 0; i < PINCODE_LENGTH; i++) {
			TextView digitView = createPincodeDigitView(i);
			pincodeDigitViews[i] = digitView;
			digitsLayout.addView(digitView);
		}
	}

	@SuppressWarnings("deprecation")
	private Button createButton(int index) {
		TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		buttonParams.setMargins(2, 2, 2, 2);
		buttonParams.width = buttonSize;
		buttonParams.height = buttonSize;
		Button retButton = new Button(this);
		retButton.setLayoutParams(buttonParams);
		retButton.setId(BUTTON_ID_PREFIX + index);
		retButton.setGravity(Gravity.CENTER);

		retButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_button));
		retButton.setIncludeFontPadding(false);
		retButton.setTextSize(textSize);

		if (index >= 0 && index <= 9) {
			retButton.setText("" + index);
		} else {
			if (index == 10) {
				retButton.setTextColor(Color.BLUE);
				retButton.setText("<");
			}
			if (index == 11) {
				retButton.setTextColor(Color.RED);
				retButton.setText("C");
			}
		}

		retButton.setOnClickListener(this);
		return retButton;
	}

	private TextView createPincodeDigitView(int i) {
		TextView retView = new TextView(this);
		retView.setHeight(buttonSize / 2);
		retView.setWidth(buttonSize / 2);

		retView.setLayoutParams(digViewLayoutParams);

		retView.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_button));
		retView.setIncludeFontPadding(false);
		retView.setTextSize(textSize / 2);

		return retView;

	}

	protected void analizePincode() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < PINCODE_LENGTH; i++) {
			sb.append(pincodeDigits.get(i));
		}
		String enterdePin = sb.toString();
		if (validCodes.contains(enterdePin)) {
			infoText.setText("Correct pincode");
			infoText.setTextColor(Color.BLUE);
			String currentDate = (String) DateFormat.format("yyyy-MM-dd", new Date());

			DataReceiver.setPincode(enterdePin);
			DataReceiver.setScanDate(currentDate);

			moveToScanning(enterdePin, currentDate);
		} else {
			onIncorrectPincode();
		}

	}

	private void onIncorrectPincode() {
		infoText.setText("Incorrect pincode");
		infoText.setTextColor(Color.RED);
		deleteAllDigits();
	}

	private void moveToScanning(String enteredPin, String currentDate) {
		Intent intent = new Intent(EntryActivity.this, QKActivity.class);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entry, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int index = v.getId() - BUTTON_ID_PREFIX;
		switch (index) {
		case 10:
			deleteLastDigit();
			break;
		case 11:
			deleteAllDigits();
			break;

		default:
			inputDigit(index);
			break;
		}

	}

	private void inputDigit(int index) {
		String digit = String.valueOf(index);
		pincodeDigits.add(digit);
		pincodeDigitViews[pincodeDigits.size() - 1].setText(digit);

		if (pincodeDigits.size() == PINCODE_LENGTH) {
			analizePincode();
		}

	}

	private void deleteAllDigits() {
		pincodeDigits.removeAll(pincodeDigits);
		for (TextView cell : pincodeDigitViews) {
			cell.setText("");
		}
	}

	private void deleteLastDigit() {
		int index = pincodeDigits.size() - 1;
		pincodeDigits.remove(index);
		pincodeDigitViews[index].setText("");
	}

}
