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

import android.app.Activity;
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
		
		scannedCodes=new ArrayList<String>();

		scanBarCode();

	}

	private void scanBarCode() {

		Thread t = new Thread();
		t.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Random rnd = new Random();
		String barCode = "" + rnd.nextInt(1000000000) + rnd.nextInt(1000000000);

		scanResultView.setText(barCode);

		scannedCodes.add(barCode);

		// sb.append(tab).append(tab).append("<SHELTER>").append(barCode).append("</SHELTER>").append("\n");

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
		String message=createMessage();
		
		System.out.println(message);
		
		// connectToServer();

	}

	private String createMessage() {

		StringBuilder sb = new StringBuilder();
		sb.append("<DEPOT_SHELTERS>").append("\n");
		sb.append(tab).append("<SECKEY>").append(pinCode).append("</SECKEY>").append("\n");
		sb.append(tab).append("<SCANDATE>").append(scanDate).append("</SCANDATE>").append("\n");
		sb.append(tab).append("<BARCODES>").append("\n");

		for (String barcode : scannedCodes) {
			sb.append(tab).append(tab).append("<SHELTER>").append(barcode).append("</SHELTER>").append("\n");
		}

		sb.append(tab).append("<BARCODES>").append("\n");
		sb.append("</DEPOT_SHELTERS>").append("\n");

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
