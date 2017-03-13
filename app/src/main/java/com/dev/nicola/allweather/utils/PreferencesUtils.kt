package com.dev.nicola.allweather.utils

import android.content.Context
import android.preference.PreferenceManager
import com.dev.nicola.allweather.ui.activity.MainActivity

/**
 * Created by Nicola on 17/08/2016.
 */
object PreferencesUtils {

    fun setPreferences(context: Context, key: String, defaultValue: Any) {
        val sp = context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE)
        val editor = sp.edit()
        when (defaultValue) {
            is String -> editor.putString(key, defaultValue)
            is Boolean -> editor.putBoolean(key, defaultValue)
            is Int -> editor.putInt(key, defaultValue)
            is Float -> editor.putFloat(key, defaultValue)
            is Long -> editor.putLong(key, defaultValue)
        }
        editor.apply()
    }

    fun getPreferences(context: Context, key: String, defaultValue: Any): Any? {
        val sp = context.getSharedPreferences(MainActivity::class.java.name, Context.MODE_PRIVATE)
        var value: Any? = null

        when (defaultValue) {
            is String -> value = sp.getString(key, defaultValue)
            is Boolean -> value = sp.getBoolean(key, defaultValue)
            is Int -> value = sp.getInt(key, defaultValue)
            is Float -> value = sp.getFloat(key, defaultValue)
            is Long -> value = sp.getLong(key, defaultValue)
        }

        return value
    }


    fun getDefaultPreferences(context: Context, key: String, defaultValue: Any): Any? {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        var value: Any? = null

        when (defaultValue) {
            is String -> value = sp.getString(key, defaultValue)
            is Boolean -> value = sp.getBoolean(key, defaultValue)
            is Int -> value = sp.getInt(key, defaultValue)
            is Float -> value = sp.getFloat(key, defaultValue)
            is Long -> value = sp.getLong(key, defaultValue)
        }

        return value
    }
}
