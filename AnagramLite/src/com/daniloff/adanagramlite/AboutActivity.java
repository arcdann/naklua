package com.daniloff.adanagramlite;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity implements OnClickListener {

	private Button buttonOK;
	private TextView productVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		buttonOK = (Button) findViewById(R.id.about_button_ok);
		buttonOK.setOnClickListener(this);

		productVersion = (TextView) findViewById(R.id.about_versionNumber);
		try {
			productVersion.setText(getApplicationContext().getPackageManager().getPackageInfo(
					getApplicationContext().getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(AboutActivity.this, ChoiceActivity.class);
		startActivity(intent);
	}

}
