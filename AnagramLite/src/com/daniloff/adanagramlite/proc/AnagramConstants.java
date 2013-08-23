package com.daniloff.adanagramlite.proc;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import com.daniloff.adanagramlite.R;

public class AnagramConstants {
	@SuppressLint("UseSparseArrays")
	public static final Map<Integer, LevelParams> LEVEL_PARAMS = new HashMap<Integer, LevelParams>();

	static {
		LEVEL_PARAMS.put(1, new LevelParams(30, 4, R.raw.ch04, 5, 1, 1, 2, 2));
		LEVEL_PARAMS.put(2, new LevelParams(20, 5, R.raw.ch05, 10, 1, 1, 2, 2));
		LEVEL_PARAMS.put(3, new LevelParams(20, 6, R.raw.ch06, 15, 1, 1, 2, 2));
 		LEVEL_PARAMS.put(4, new LevelParams(15, 7, R.raw.ch07, 20, 2, 2, 3, 3));
	}
}
