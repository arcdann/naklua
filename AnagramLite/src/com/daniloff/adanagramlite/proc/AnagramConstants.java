package com.daniloff.adanagramlite.proc;

import java.util.HashMap;
import java.util.Map;

import com.daniloff.adanagramlite.R;

public class AnagramConstants {
	public static final Map<Integer, LevelParams> LEVEL_PARAMS = new HashMap<Integer, LevelParams>();

	// LevelParams(int stepsLimit, int wordLength, int resource, int wordPrice,
	// int hintLimit, int hintPrice,int attemptLimit, int attemptPrice)

	static {
		LEVEL_PARAMS.put(1, new LevelParams(30, 4, R.raw.b4, 5, 1, 1, 2, 2));
		LEVEL_PARAMS.put(2, new LevelParams(20, 5, R.raw.b5, 10, 1, 1, 2, 2));
		LEVEL_PARAMS.put(3, new LevelParams(20, 6, R.raw.b6, 15, 1, 1, 2, 2));
 		LEVEL_PARAMS.put(4, new LevelParams(15, 7, R.raw.b7, 20, 2, 2, 3, 3));
	}
}
