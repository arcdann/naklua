package com.daniloff.adanagramlite;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		TextView appName=(TextView) findViewById(R.id.splash_appName);
		TextView appVer=(TextView) findViewById(R.id.splash_ver);
		
		appName.setText(R.string.app_name);
		try {
			appVer.setText("ver. "+getApplicationContext().getPackageManager().getPackageInfo(
					getApplicationContext().getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				Intent intent=new Intent(SplashActivity.this, ChoiceActivity.class);
				startActivity(intent);
			}
		}, SPLASH_TIME_OUT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
