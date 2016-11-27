package com.dev.nicola.allweather.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;

import com.dev.nicola.allweather.R;

/**
 * Created by Nicola on 02/04/2016.
 */
public class Utils {


    public static boolean checkGpsEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public static boolean checkInternetConnession(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void setTheme(Context context) {
        switch (PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.key_pref_theme), context.getString(R.string.default_pref_theme))) {

            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "auto":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        }
    }


}
