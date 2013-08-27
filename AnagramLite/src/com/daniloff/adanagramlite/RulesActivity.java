package com.daniloff.adanagramlite;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RulesActivity extends Activity implements OnClickListener{
	
	private Button buttonOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules);
		
		buttonOK=(Button) findViewById(R.id.button_rulesOk);
		buttonOK.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rules, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent(RulesActivity.this, ChoiceActivity.class);
		startActivity(intent);
		
	
		
	}

}
