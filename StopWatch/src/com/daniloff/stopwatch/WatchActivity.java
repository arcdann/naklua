package com.daniloff.stopwatch;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WatchActivity extends Activity implements OnClickListener {

	private long startTime = 0;
	private long stopTime = 0;
	private boolean started;
	private boolean stopped;
	private TextView timeView1;
	private TextView timeView2;
	private TimeTeacker timeTeacker1;
	private TimeTeacker timeTeacker2;
	private SimpleDateFormat formatterMils = new SimpleDateFormat("HH:mm:ss.SSS");
	private SimpleDateFormat formatterSec = new SimpleDateFormat("HH:mm:ss");
	private Button button1;
	private Button button2;
	private int point;
	private CountDownTimer showDelayTimer;
	private final long SHOW_DELAY = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_watch);

		timeView1 = (TextView) findViewById(R.id.textView1);
		timeView2 = (TextView) findViewById(R.id.textView2);

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		button1.setText("Start");
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
		button2.setText("Start (" + point + ")");
		
	

		createTimeTeackers();
//		createTimeTeacker(timeTeacker2, timeView2);

		showDelayTimer = new CountDownTimer(SHOW_DELAY, SHOW_DELAY) {
			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {
				// startClockViz();
				timeView1.setEnabled(true);
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.watch, menu);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			if (!started) {// start
				point++;
				startClock();
			} else {
				if (!stopped) {// stop
					long currentTime = System.currentTimeMillis();
					String timeFormatted = formatterMils.format(currentTime - startTime);
					timeView1.setText(timeFormatted);
					timeView1.setEnabled(false);
					button1.setText("Reset");
					stopped = true;
				} else {// reset
					timeView1.setText(formatterMils.format(0));
					started = false;
					stopped = false;
					button1.setText("Start");
				}
			}
			break;
		case R.id.button2:
			point++;
			if (!started) {// start
				startClock();
			} else {
				long currentTime = System.currentTimeMillis();
				String timeFormattedCommon = formatterMils.format((currentTime - startTime));
				String timeFormattedLocal = formatterMils.format((currentTime - stopTime));
				stopTime = System.currentTimeMillis();
				button2.setText("Stop (" + point + ")");
				////////////////////////////////////////////////////////////////////////
				timeView1.setText(timeFormattedCommon);
				timeView1.setEnabled(false);
				timeView2.setText(timeFormattedLocal);
				timeView2.setEnabled(false);
				showDelayTimer.start();

			}

			break;
		default:
			break;
		}

	}

	private void startClock() {
		startTime = System.currentTimeMillis();
		stopTime = startTime;
		timeView1.setEnabled(true);
		started = true;
		startClockViz();
		button1.setText("Stop");
		button2.setText("Stop (" + point + ")");
	}

	private void createTimeTeackers() {
		timeTeacker1 = new TimeTeacker();
		timeTeacker1.setWatch(this);
		timeTeacker1.setTimeView(timeView1);

		timeTeacker2 = new TimeTeacker();
		timeTeacker2.setWatch(this);
		timeTeacker2.setTimeView(timeView2);
	}

	private void startClockViz() {

		Thread tw = new Thread(timeTeacker1);//////////////////////////////////////////////////////////
		tw.setDaemon(true);
		tw.start();
	}

	public void setTimeView(final long time, final TextView timeView) {
		runOnUiThread(new Runnable() {
			@SuppressLint("NewApi")
			public void run() {
				if (timeView.isEnabled()) {
					String timeFormatted = formatterSec.format(time);
					timeView.setText(timeFormatted);
				}
			}
		});
	}

	public boolean isStarted() {
		return started;
	}

	public boolean isStopped() {
		return stopped;
	}

	public long getStartTime() {
		return startTime;
	}

}
