package com.daniloff.adanagramlite.proc;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.daniloff.adanagramlite.ChoiceActivity;

public class ParamsHandler {

	private static final String SETTINGS_FILENAME = "gamestate";
	private Context context;

	public void saveParamString(String paramName, String paramValue) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(paramName, paramValue);
		editor.commit();
	}

	public void saveParamInt(String paramName, int paramValue) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putInt(paramName, paramValue);
		editor.commit();
	}

	public void saveParamBoolean(String paramName, boolean paramValue) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putBoolean(paramName, paramValue);
		editor.commit();
	}

	public String loadParamString(String paramName) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
		return settings.getString(paramName, "");
	}

	public int loadParamInt(String paramName) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
		return settings.getInt(paramName, 0);
	}

	public boolean loadParamBoolean(String paramName) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
		return settings.getBoolean(paramName, false);
	}

	public void setView(ChoiceActivity view) {
	}

	public void setContext(Context baseContext) {
		context = baseContext;
	}

}
