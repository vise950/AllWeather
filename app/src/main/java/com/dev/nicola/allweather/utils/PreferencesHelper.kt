package com.dev.nicola.allweather.utils

import android.content.Context
import android.preference.PreferenceManager
import com.dev.nicola.allweather.ui.activity.MainActivity

object PreferencesHelper {

    val KEY_PREF_TEMPERATURE: String = "temperature"
    val KEY_PREF_SPEED: String = "speed"
    val KEY_PREF_TIME: String = "time"
    val KEY_PREF_THEME: String = "theme"
    val KEY_PREF_WEATHER_PROVIDER: String = "weather_provider"
    val KEY_PREF_PRO_VERSION: String = "is_pro_version"
    val KEY_FIRST_LAUNCH: String = "is_first_launch"
    val KEY_LAST_UPDATE: String = "last_update_data"

    val DEFAULT_PREF_TEMPERATURE: String = "celsius"
    val DEFAULT_PREF_SPEED: String = "kmh"
    val DEFAULT_PREF_TIME: String = "24"
    val DEFAULT_PREF_THEME: String = "light"
    val DEFAULT_PREF_WEATHER_PROVIDER: String = "darkSky"

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

//    fun getWeatherProvider(context: Context): Enum<WeatherProvider> {
//        val p = getDefaultPreferences(context, KEY_PREF_WEATHER_PROVIDER, DEFAULT_PREF_WEATHER_PROVIDER)
//        return WeatherProvider.fromString(p.toString())
//    }

    fun isProVersion(context: Context): Boolean? {
        return getPreferences(context, KEY_PREF_PRO_VERSION, false) as Boolean
    }

    fun isFirstLaunch(context: Context): Boolean? {
        return getPreferences(context, KEY_FIRST_LAUNCH, true) as Boolean
    }

    fun isPreferenceChange(context: Context, key: String, defaultValue: Any, oldPreference: String): Boolean? {
        val newPreference = getDefaultPreferences(context, key, defaultValue)
        return oldPreference != newPreference
    }
}