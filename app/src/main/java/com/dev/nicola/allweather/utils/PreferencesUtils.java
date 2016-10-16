package com.dev.nicola.allweather.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.dev.nicola.allweather.MainActivity;

/**
 * Created by Nicola on 17/08/2016.
 */
public class PreferencesUtils {

    public static String getDefaultPreferences(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    public static boolean getDefaultPreferences(Context context, String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }


    public static void setPreferences(Context context, String key, boolean value) {
        context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public static boolean getPreferences(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }
}
