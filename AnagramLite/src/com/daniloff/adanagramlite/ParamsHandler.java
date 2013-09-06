package com.daniloff.adanagramlite;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcel;
import android.os.Parcelable;

public class ParamsHandler implements Parcelable {

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
		return null;
	}

	public int loadParamInt(String paramName) {
		SharedPreferences settings = context.getSharedPreferences(SETTINGS_FILENAME, Context.MODE_PRIVATE);
		return settings.getInt(paramName, 0);
	}

	public boolean loadParamBoolean(String paramName) {
		return false;
	}

	public void setView(ChoiceActivity view) {
	}

	public void setContext(Context baseContext) {
		context = baseContext;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
