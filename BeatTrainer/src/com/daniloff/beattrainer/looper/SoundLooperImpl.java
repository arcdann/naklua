package com.daniloff.beattrainer.looper;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.daniloff.adbeattrainer.R;

public class SoundLooperImpl implements SoundLooper {

	private SoundPool soundPool;
	int soundId;

	@SuppressLint("UseSparseArrays")
	public SoundLooperImpl(Context ctx) {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundId = soundPool.load(ctx, R.raw.click, 1);
	}

	@Override
	public void loopSound() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				playSound();
			}
		}, 0, 250);
	}

	public void playSound() {
		soundPool.play(soundId, 1, 1, 1, 0, 1);
	}

}
