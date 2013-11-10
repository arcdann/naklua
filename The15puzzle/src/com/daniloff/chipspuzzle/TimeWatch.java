package com.daniloff.chipspuzzle;

public class TimeWatch implements Runnable {
	private BoxActivity box;
	private double time = 0;

	@Override
	public void run() {
		while (box.isStarted()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!box.isPaused()) {
				time = time + 0.1;
				box.setTimeView(String.format("%3.1f", time));
			}
			if (!box.isStarted()) {
				time = 0;
				box.setTimeView(String.format("%3.1f", time));
			}
		}
	}

	public void setBox(BoxActivity box) {
		this.box = box;
	}

	public double getTime() {
		return time;
	}

}
