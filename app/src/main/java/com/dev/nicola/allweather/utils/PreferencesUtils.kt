package com.dev.nicola.allweather.utils

import android.content.Context
import android.preference.PreferenceManager

import com.dev.nicola.allweather.MainActivity

/**
 * Created by Nicola on 17/08/2016.
 */
object PreferencesUtils {

    /*
        return String from DefaultSharedPreferences
     */
    fun getDefaultPreferences(context: Context, key: String, defaultValue: String): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue)
    }

    /*
        return boolen from DefaultSharedPreferences
     */
    fun getDefaultPreferences(context: Context, key: String, defaultValue: Boolean): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue)
    }


    /*
        put boolean on SharedPreferences
     */
    fun setPreferences(context: Context, key: String, value: Boolean) {
        context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply()
    }


    /*
        put String on SharedPreferences
     */
    fun setPreferences(context: Context, key: String, value: String) {
        context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).edit().putString(key, value).apply()
    }


    /*
        put long on SharedPreferences
     */
    fun setPreferences(context: Context, key: String, value: Long) {
        context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).edit().putLong(key, value).apply()
    }


    /*
        put int on SharedPreferences
     */
    fun setPreferences(context: Context, key: String, value: Int) {
        context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).edit().putInt(key, value).apply()
    }


    /*
        put float on SharedPreferences
     */
    fun setPreferences(context: Context, key: String, value: Float) {
        context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).edit().putFloat(key, value).apply()
    }


    /*
        return boolean from SharedPreferences
     */
    fun getPreferences(context: Context, key: String, defaultValue: Boolean): Boolean {
        return context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).getBoolean(key, defaultValue)
    }


    /*
        return String from SharedPreferences
     */
    fun getPreferences(context: Context, key: String, defaultValue: String): String {
        return context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).getString(key, defaultValue)
    }


    /*
        return long from SharedPreferences
     */
    fun getPreferences(context: Context, key: String, defaultValue: Long): Long {
        return context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).getLong(key, defaultValue)
    }


    /*
        return int from SharedPreferences
     */
    fun getPreferences(context: Context, key: String, defaultValue: Int): Int {
        return context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).getInt(key, defaultValue)
    }


    /*
        return float from SharedPreferences
     */
    fun getPreferences(context: Context, key: String, defaultValue: Float): Float {
        return context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE).getFloat(key, defaultValue)
    }


}
