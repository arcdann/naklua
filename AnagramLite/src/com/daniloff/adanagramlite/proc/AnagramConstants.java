package com.daniloff.adanagramlite.proc;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import com.daniloff.adanagramlite.R;

public class AnagramConstants {
	@SuppressLint("UseSparseArrays")
	public static final Map<Integer, LevelParams> LEVEL_PARAMS = new HashMap<Integer, LevelParams>();

	static {
		LEVEL_PARAMS.put(1, new LevelParams(30, 3, R.raw.ch03en, 3, 1, 1, 2, 1));
		LEVEL_PARAMS.put(2, new LevelParams(30, 4, R.raw.ch04en, 5, 1, 1, 2, 2));
		LEVEL_PARAMS.put(3, new LevelParams(30, 5, R.raw.ch05en, 8, 1, 1, 2, 2));
		LEVEL_PARAMS.put(4, new LevelParams(25, 6, R.raw.ch06en, 10, 1, 1, 2, 2));
		LEVEL_PARAMS.put(5, new LevelParams(25, 7, R.raw.ch07en, 12, 2, 2, 3, 3));
		LEVEL_PARAMS.put(6, new LevelParams(20, 8, R.raw.ch08en, 15, 2, 2, 3, 3));
		LEVEL_PARAMS.put(7, new LevelParams(20, 9, R.raw.ch09en, 18, 2, 2, 3, 3));
		LEVEL_PARAMS.put(8, new LevelParams(15, 10, R.raw.ch10en, 20, 2, 2, 3, 3));
		LEVEL_PARAMS.put(9, new LevelParams(15, 11, R.raw.ch11en, 25, 3, 5, 3, 5));
		LEVEL_PARAMS.put(10, new LevelParams(15, 12, R.raw.ch12en, 30, 2, 5, 3, 5));
	}
}
