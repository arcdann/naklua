package com.daniloff.barcodescanner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.daniloff.QrReader.QKActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScannerActivity extends Activity implements OnClickListener {

	private String tab = "    ";

	private List<String> scannedCodes;
	private String message;
	private String pinCode;
	private String scanDate;

	private TextView intentExtraView;
	private TextView scanResultView;

	private Button nextButton;
	private Button uploadButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanner);

		intentExtraView = (TextView) findViewById(R.id.intentView);
		scanResultView = (TextView) findViewById(R.id.infoView);

		nextButton = (Button) findViewById(R.id.scan_nextButton);
		nextButton.setOnClickListener(this);

		uploadButton = (Button) findViewById(R.id.scan_uploadButton);
		uploadButton.setOnClickListener(this);

		String pinCode = getIntent().getStringExtra("pin");
		String scanDate = getIntent().getStringExtra("date");

		intentExtraView.setText("Pin: " + pinCode + " Date: " + scanDate);

		scannedCodes = new ArrayList<String>();

		// scanBarCode();

		obtainBarCode();

	}

	private void obtainBarCode() {

		String code = getIntent().getStringExtra("scannedCode");
		DataReceiver.setObtainedCode(code);

	}

	private void scanBarCode() {

		Intent intent = new Intent(ScannerActivity.this, QKActivity.class);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scanner, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.scan_nextButton:
			scanBarCode();
			break;
		case R.id.scan_uploadButton:
			uploadMessage();
			break;

		default:
			break;
		}

	}

	private void uploadMessage() {
		String message = createMessage();

		System.out.println(message);

		// connectToServer();

	}

	private String createMessage() {

		StringBuilder sb = new StringBuilder();
		sb.append(DataReceiver.getPincode()).append("\n");
		sb.append(DataReceiver.getScanDate()).append("\n");

		for (String barcode : DataReceiver.getScannedCodes()) {
			sb.append(barcode).append("\n");
		}

		return sb.toString();

	}

	@SuppressWarnings("unused")
	private void connectToServer() {
		String serverAddress = "http://www.tc65.nl/o/s.php?message";// ************

		Socket socket = null;
		try {
			socket = new Socket(serverAddress, 9898);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
