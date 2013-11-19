package com.daniloff.stopwatch;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class WatchActivity extends Activity implements OnClickListener, OnLongClickListener {

	private long startTime = 0;
	private long stopTime = 0;
	private long restartTime = 0;
	private long elapsedTime = 0;
	private long elapsedDiffTime = 0;
	private boolean started;
	private boolean stopped;
	private TextView timeView1;
	private TextView timeView2;
	private SimpleDateFormat formatterMils = new SimpleDateFormat("HH:mm:ss.SSS");
	private SimpleDateFormat formatterSec = new SimpleDateFormat("HH:mm:ss");
	private Button button1;
	private Button button2;
	private Button resetButton;

	private int point;
	private CountDownTimer delayTimerShow;
	private final long SHOW_DELAY = 3000;

	private Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_watch);

		timeView1 = (TextView) findViewById(R.id.textView1);
		timeView2 = (TextView) findViewById(R.id.textView2);
		timeView2.setVisibility(View.INVISIBLE);

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		button1.setText("Start");
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
		button2.setText("Start (" + point + ")");
		resetButton = (Button) findViewById(R.id.resetButton);
		resetButton.setEnabled(false);
		resetButton.setOnLongClickListener(this);

		delayTimerShow = new CountDownTimer(SHOW_DELAY, SHOW_DELAY) {
			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {
				timeView1.setEnabled(true);
				timeView2.setEnabled(true);
			}
		};
	}

	private void startGeneralClock() {
		startTime = System.currentTimeMillis();
		// restartTime = startTime;
		resetButton.setEnabled(false);

		timer = new Timer();
		final Runnable updateTask = new Runnable() {
			public void run() {
				if (started) {
					timeView1.setText(getElapsedTime());
				}
			}
		};

		int msec = 999 - (int) ((System.currentTimeMillis() - startTime) % 1000);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(updateTask);
			}
		}, msec, 1000);

		button1.setText("Stop");
		button2.setText("Restart (" + point + ")");
	}

	private void startDiffClock() {
		restartTime = System.currentTimeMillis();
		// restartTime = startTime;

		timer = new Timer();
		final Runnable updateTask = new Runnable() {
			public void run() {
				if (started) {
					timeView2.setText(getElapsedDiffTime());
				}
			}
		};

		int msec = 999 - (int) ((System.currentTimeMillis() - restartTime) % 1000);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(updateTask);
			}
		}, msec, 1000);

		button1.setText("Stop");
		button2.setText("Restart (" + point + ")");
	}

	private String getElapsedTime() {
		String timeFormatted = formatterSec.format(elapsedTime + System.currentTimeMillis() - startTime);
		return timeFormatted;
	}

	protected CharSequence getElapsedDiffTime() {
		String timeFormatted = formatterSec.format(elapsedDiffTime + System.currentTimeMillis() - restartTime);
		return timeFormatted;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.watch, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		System.out.println("XXXXXXXXXXXXX");
		switch (v.getId()) {
		case R.id.button1:
			if (!started) {
				if(point==0){
					point++;
				}
				startGeneralClock();
				started = true;

			} else {
				stopTime = System.currentTimeMillis();
				elapsedTime += stopTime - startTime;
				elapsedDiffTime += stopTime - restartTime;
				String generalTimeFormatted = formatterMils.format(elapsedTime);
				timeView1.setText(generalTimeFormatted);
				String diffTimeFormatted = formatterMils.format(elapsedDiffTime);
				timeView2.setText(diffTimeFormatted);
				button1.setText("Resume");
				started = false;
				resetButton.setEnabled(true);
			}
			break;
		case R.id.button2:
			point++;
			if (!started) {// start
				startGeneralClock();
				started = true;


				
	 

			} else {
				timeView2.setVisibility(View.VISIBLE);
				elapsedDiffTime=0;
				if(restartTime==0){
					restartTime=startTime;
				}
				long currentTime = System.currentTimeMillis();
				String timeFormattedCommon = formatterMils.format((currentTime - startTime));
				String timeFormattedLocal = formatterMils.format((currentTime - restartTime));
				restartTime = System.currentTimeMillis();
				button2.setText("Restart (" + point + ")");
				// //////////////////////////////////////////////////////////////////////
				timeView1.setText(timeFormattedCommon);
				// timeView1.setEnabled(false);
				timeView2.setText(timeFormattedLocal);
				// timeView2.setEnabled(false);
				// showDelayTimer.start();
				startDiffClock();
				
				
				timeView1.setEnabled(false);
				timeView2.setEnabled(false);
				delayTimerShow.start();
				printData();
				
				
			}

			break;
		default:
			break;
		}
	}

	private void printData() {
		System.out.println(point);
		System.out.println(formatterMils.format(System.currentTimeMillis()));
		System.out.println(formatterMils.format(elapsedTime));
		System.out.println(formatterMils.format(elapsedDiffTime));
		System.out.println();

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

	@Override
	public boolean onLongClick(View v) {
		reset();
		return false;
	}

	private void reset() {
		timeView1.setText("");
		timeView2.setText("");
		timeView2.setVisibility(View.INVISIBLE);
		point = 0;
		button1.setText("Start");
		button2.setText("Start (" + point + ")");
		elapsedTime = 0;
		elapsedDiffTime = 0;
		startTime=0;
		restartTime=0;
		stopTime=0;
		resetButton.setEnabled(false);
	}

}
