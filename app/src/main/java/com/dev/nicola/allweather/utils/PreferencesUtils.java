package com.dev.nicola.allweather.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.dev.nicola.allweather.MainActivity;

/**
 * Created by Nicola on 17/08/2016.
 */
public class PreferencesUtils {

    /*
        return String from DefaultSharedPreferences
     */
    public static String getDefaultPreferences(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    /*
        return boolen from DefaultSharedPreferences
     */
    public static boolean getDefaultPreferences(Context context, String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }


    /*
        put boolean on SharedPreferences
     */
    public static void setPreferences(Context context, String key, boolean value) {
        context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }


    /*
        put String on SharedPreferences
     */
    public static void setPreferences(Context context, String key, String value) {
        context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }


    /*
        put long on SharedPreferences
     */
    public static void setPreferences(Context context, String key, long value) {
        context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).edit().putLong(key, value).apply();
    }


    /*
        put int on SharedPreferences
     */
    public static void setPreferences(Context context, String key, int value) {
        context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).edit().putInt(key, value).apply();
    }


    /*
        put float on SharedPreferences
     */
    public static void setPreferences(Context context, String key, float value) {
        context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).edit().putFloat(key, value).apply();
    }


    /*
        return boolean from SharedPreferences
     */
    public static boolean getPreferences(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }


    /*
        return String from SharedPreferences
     */
    public static String getPreferences(Context context, String key, String defaultValue) {
        return context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).getString(key, defaultValue);
    }


    /*
        return long from SharedPreferences
     */
    public static long getPreferences(Context context, String key, long defaultValue) {
        return context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).getLong(key, defaultValue);
    }


    /*
        return int from SharedPreferences
     */
    public static int getPreferences(Context context, String key, int defaultValue) {
        return context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).getInt(key, defaultValue);
    }


    /*
        return float from SharedPreferences
     */
    public static float getPreferences(Context context, String key, float defaultValue) {
        return context.getSharedPreferences(MainActivity.class.getName(), Context.MODE_PRIVATE).getFloat(key, defaultValue);
    }


}
