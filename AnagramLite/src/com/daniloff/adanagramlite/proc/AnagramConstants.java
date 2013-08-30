package com.daniloff.adanagramlite.proc;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import com.daniloff.adanagramlite.R;

public class AnagramConstants {

	@SuppressLint("UseSparseArrays")
	public static final Map<Integer, LevelParams> LEVEL_PARAMS_EN = new HashMap<Integer, LevelParams>();

	static {
		LEVEL_PARAMS_EN.put(1, new LevelParams(30, 3, R.raw.ch03en, 3, 1, 1, 2, 1));
		LEVEL_PARAMS_EN.put(2, new LevelParams(30, 4, R.raw.ch04en, 5, 1, 1, 2, 2));
		LEVEL_PARAMS_EN.put(3, new LevelParams(30, 5, R.raw.ch05en, 8, 1, 1, 2, 2));
		LEVEL_PARAMS_EN.put(4, new LevelParams(25, 6, R.raw.ch06en, 10, 1, 1, 2, 2));
		LEVEL_PARAMS_EN.put(5, new LevelParams(25, 7, R.raw.ch07en, 12, 2, 2, 3, 3));
		LEVEL_PARAMS_EN.put(6, new LevelParams(20, 8, R.raw.ch08en, 15, 2, 2, 3, 3));
		LEVEL_PARAMS_EN.put(7, new LevelParams(20, 9, R.raw.ch09en, 18, 2, 2, 3, 3));
		LEVEL_PARAMS_EN.put(8, new LevelParams(15, 10, R.raw.ch10en, 20, 2, 2, 3, 3));
		LEVEL_PARAMS_EN.put(9, new LevelParams(15, 11, R.raw.ch11en, 25, 3, 5, 3, 5));
		LEVEL_PARAMS_EN.put(10, new LevelParams(15, 12, R.raw.ch12en, 30, 2, 5, 3, 5));
	}

	@SuppressLint("UseSparseArrays")
	public static final Map<Integer, LevelParams> LEVEL_PARAMS_RU = new HashMap<Integer, LevelParams>();

	static {
		LEVEL_PARAMS_RU.put(1, new LevelParams(30, 3, R.raw.ch03ru, 3, 1, 1, 2, 1));
		LEVEL_PARAMS_RU.put(2, new LevelParams(30, 4, R.raw.ch04ru, 5, 1, 1, 2, 2));
		LEVEL_PARAMS_RU.put(3, new LevelParams(30, 5, R.raw.ch05ru, 8, 1, 1, 2, 2));
		LEVEL_PARAMS_RU.put(4, new LevelParams(25, 6, R.raw.ch06ru, 10, 1, 1, 2, 2));
		LEVEL_PARAMS_RU.put(5, new LevelParams(25, 7, R.raw.ch07ru, 12, 2, 2, 3, 3));
		LEVEL_PARAMS_RU.put(6, new LevelParams(20, 8, R.raw.ch08ru, 15, 2, 2, 3, 3));
		LEVEL_PARAMS_RU.put(7, new LevelParams(20, 9, R.raw.ch09ru, 18, 2, 2, 3, 3));
		LEVEL_PARAMS_RU.put(8, new LevelParams(15, 10, R.raw.ch10ru, 20, 2, 2, 3, 3));
		LEVEL_PARAMS_RU.put(9, new LevelParams(15, 11, R.raw.ch11ru, 25, 3, 5, 3, 5));
		LEVEL_PARAMS_RU.put(10, new LevelParams(15, 12, R.raw.ch12ru, 30, 2, 5, 3, 5));
	}

}
