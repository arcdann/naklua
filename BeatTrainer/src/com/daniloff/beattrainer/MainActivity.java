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
//			logic.loopSound();
	        AudioGenerator audio = new AudioGenerator(8000);

	        

	        int noteDuration = 2400;
	        
	        double[] silence = audio.getSineWave(noteDuration, 8000, 0);//was 200

	        double[] doNote = audio.getSineWave(noteDuration/2, 8000, 523.25);
	        double[] reNote = audio.getSineWave(noteDuration/2, 8000, 587.33);
	        double[] faNote = audio.getSineWave(noteDuration, 8000, 698.46);
	        double[] laNote = audio.getSineWave(noteDuration, 8000, 880.00);
	        double[] laNote2 =
	                audio.getSineWave((int) (noteDuration*1.25), 8000, 880.00);
	        double[] siNote = audio.getSineWave(noteDuration/2, 8000, 987.77);
	        double[] doNote2 =
	                audio.getSineWave((int) (noteDuration*1.25), 8000, 523.25);
	        double[] miNote = audio.getSineWave(noteDuration/2, 8000, 659.26);
	        double[] miNote2 = audio.getSineWave(noteDuration, 8000, 659.26);
	        double[] doNote3 = audio.getSineWave(noteDuration, 8000, 523.25);
	        double[] miNote3 = audio.getSineWave(noteDuration*3, 8000, 659.26);
	        double[] reNote2 = audio.getSineWave(noteDuration*4, 8000, 587.33);

	        audio.createPlayer();
	        for(int i=0;i<90;i++){
	        audio.writeSound(doNote);
	        audio.writeSound(silence);}
	    /*    audio.writeSound(reNote);
	        audio.writeSound(silence);
	        audio.writeSound(faNote);
	        audio.writeSound(silence);
	        audio.writeSound(laNote);
	        audio.writeSound(silence);
	        audio.writeSound(laNote2);
	        audio.writeSound(silence);
	        audio.writeSound(siNote);
	        audio.writeSound(silence);
	        audio.writeSound(laNote);
	        audio.writeSound(silence);
	        audio.writeSound(faNote);
	        audio.writeSound(silence);
	        audio.writeSound(doNote2);
	        audio.writeSound(silence);
	        audio.writeSound(miNote);
	        audio.writeSound(silence);
	        audio.writeSound(faNote);
	        audio.writeSound(silence);
	        audio.writeSound(faNote);
	        audio.writeSound(silence);
	        audio.writeSound(miNote2);
	        audio.writeSound(silence);
	        audio.writeSound(doNote3);
	        audio.writeSound(silence);
	        audio.writeSound(miNote3);
	        audio.writeSound(silence);
	        audio.writeSound(doNote);
	        audio.writeSound(silence);
	        audio.writeSound(reNote);
	        audio.writeSound(silence);
	        audio.writeSound(faNote);
	        audio.writeSound(silence);
	        audio.writeSound(laNote);
	        audio.writeSound(silence);
	        audio.writeSound(laNote2);
	        audio.writeSound(silence);
	        audio.writeSound(siNote);
	        audio.writeSound(silence);
	        audio.writeSound(laNote);
	        audio.writeSound(silence);
	        audio.writeSound(faNote);
	        audio.writeSound(silence);
	        audio.writeSound(doNote2);
	        audio.writeSound(silence);
	        audio.writeSound(miNote);
	        audio.writeSound(silence);
	        audio.writeSound(faNote);
	        audio.writeSound(silence);
	        audio.writeSound(faNote);
	        audio.writeSound(silence);
	        audio.writeSound(miNote2);
	        audio.writeSound(silence);
	        audio.writeSound(miNote2);
	        audio.writeSound(silence);
	        audio.writeSound(reNote2);*/

	        audio.destroyAudioTrack();

			layout.setBackgroundColor(Color.MAGENTA);
			break;

		case R.id.drum_button:
			drumButton.setBackgroundColor(Color.RED);

			try {
				flash();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void flash() throws InterruptedException {
		startButton.setBackgroundColor(Color.RED);
		layout.setBackgroundColor(Color.GREEN);
	}

}
