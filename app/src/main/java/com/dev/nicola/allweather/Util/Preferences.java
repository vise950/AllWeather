package com.dev.nicola.allweather.Util;

import android.content.Context;

/**
 * Created by Nicola on 01/04/2016.
 */
public class Preferences {

    private android.content.SharedPreferences mPreferences;
    private android.content.SharedPreferences.Editor mEditor;
    private Context mContext;


    public Preferences(Context context) {
        this.mContext = context;
    }

    public void setBooleanPrefences(String pref, String label, boolean b) {
        mPreferences = mContext.getSharedPreferences(pref, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        mEditor.putBoolean(label, b);
        mEditor.apply();
    }


    public boolean getBoolenaPrefences(String pref, String label) {
        boolean b;
        mPreferences = mContext.getSharedPreferences(pref, Context.MODE_PRIVATE);
        b = mPreferences.getBoolean(label, false);
        return b;
    }


    public void setStringPrefences(String pref, String label, String s) {
        mPreferences = mContext.getSharedPreferences(pref, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        mEditor.putString(label, s);
        mEditor.apply();
    }


    public String getStringPrefences(String pref, String label) {
        String b;
        mPreferences = mContext.getSharedPreferences(pref, Context.MODE_PRIVATE);
        b = mPreferences.getString(label, "");
        return b;
    }

}
