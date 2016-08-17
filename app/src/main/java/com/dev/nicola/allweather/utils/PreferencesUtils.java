package com.dev.nicola.allweather.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Nicola on 17/08/2016.
 */
public class PreferencesUtils {

    public static String getPreferences(Context context, String key, String defaultLabel) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultLabel);
    }
}
