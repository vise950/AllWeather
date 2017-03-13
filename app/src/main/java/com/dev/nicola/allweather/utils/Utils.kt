package com.dev.nicola.allweather.utils

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatDelegate

import com.dev.nicola.allweather.R

/**
 * Created by Nicola on 02/04/2016.
 */
class Utils {

    companion object {
        fun checkGpsEnable(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        fun checkInternetConnection(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnectedOrConnecting
        }

        fun isProVersion(context: Context):Boolean{
            return PreferencesUtils.getPreferences(context,context.resources.getString(R.string.key_pro_version), false) as Boolean
        }

        fun changeTheme(context: Context) {
            when (PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.key_pref_theme), context.getString(R.string.default_pref_theme))) {

                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                "auto" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
            }
        }

        fun trimString(s: String): String {
            val index = s.indexOf(',')
            val lastIndex = s.lastIndexOf(',')
            if (index != lastIndex) {
                return s.substring(0, index) + s.substring(lastIndex, s.length)
            }
            return s
        }

    }
}
