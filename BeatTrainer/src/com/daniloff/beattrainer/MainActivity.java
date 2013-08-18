package com.daniloff.beattrainer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.daniloff.adbeattrainer.R;
import com.daniloff.beattrainer.looper.SoundLooper;
import com.daniloff.beattrainer.looper.SoundLooperImpl;

public class MainActivity extends Activity implements OnClickListener {

	private Button startButton;
	private Button drumButton;
	private View layout;
	private SoundLooper logic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		logic = new SoundLooperImpl(getApplicationContext());
		start();
	}

	private void start() {
		startButton = (Button) findViewById(R.id.start_button);
		startButton.setOnClickListener(this);

		drumButton = (Button) findViewById(R.id.drum_button);
		drumButton.setOnClickListener(this);

		layout = findViewById(R.id.relative_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_button:
			// logic.loopSound();
			AudioGenerator audio = new AudioGenerator(8000);

			int noteDuration = 2400;
			double[] silence = audio.getSineWave(noteDuration, 8000, 0);// was
																		// 200
			double[] laNote = audio.getSineWave(noteDuration, 8000, 880.00);

			audio.createPlayer();
			for (int i = 0; i < 90; i++) {
				audio.writeSound(laNote);
				audio.writeSound(silence);
			}
			audio.destroyAudioTrack();

			layout.setBackgroundColor(Color.MAGENTA);
			break;

		case R.id.drum_button:
			drumButton.setBackgroundColor(Color.RED);

		}
	}
}
