package com.daniloff.stopwatch;

import android.widget.TextView;


public class TimeTeacker implements Runnable {
	private WatchActivity watch;
	private TextView timeWiew;
	private long time = 0;
	private final long EPS = 50;

	@Override
	public void run() {
		
		while (watch.isStarted()) {
			long advancedTime = time + 100;
			try {
				Thread.sleep(EPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!watch.isStopped()) {
				
				long t=System.currentTimeMillis()-watch.getStartTime();
				if(advancedTime-t<EPS){
					time=advancedTime;
					watch.setTimeView(time,timeWiew);
				}
			}
//			if (!watch.isStarted()) {
//				time = 0;
//				watch.setTimeView(String.format("%3.1f", time));
//			}
		}
	}

	public void setWatch(WatchActivity watch) {
		this.watch = watch;
	}

	public double getTime() {
		return time;
	}

	public void setTimeView(TextView timeWiew) {
		this.timeWiew = timeWiew;
	}

}
