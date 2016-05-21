package com.dev.nicola.allweather.Util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.dev.nicola.allweather.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Nicola on 02/04/2016.
 */
public class Utils {

    private static String TAG = Utils.class.getSimpleName();
    private Context mContext;

    public Utils(Context context) {
        this.mContext = context;
    }

    public String getLocationName(double latitude, double longitude) {
        String cityName = "Not Found";
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0)
                cityName = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "exception " + e);
        }
        return cityName;
    }


    public int getIcon(String condition) {
        int icon = 0;

        switch (condition) {
            case "clear-day":
                icon = R.drawable.clear_day;
                break;
            case "clear-night":
                icon = R.drawable.clear_night;
                break;
            case "partly-cloudy-day":
                icon = R.drawable.cloud_day;
                break;
            case "partly-cloudy-night":
                icon = R.drawable.cloud_night;
                break;
            case "cloudy":
                icon = R.drawable.cloud;
                break;
//            case "rain":
//                icon = R.drawable.rain;
//                break;
            case "snow":
                icon = R.drawable.snow;
                break;
            case "sleet":
                icon = R.drawable.sleet;
                break;
        }

        return icon;
    }


    public String getWindDirection(int degrees) {
        String direction;
        String cardinal[] = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        int n = (int) ((degrees / 22.5) + 0.5);
        direction = cardinal[n % cardinal.length];
        return direction;
    }

    public String getHourFormat(Integer time) {
        String t;
        Date date = new Date(time * 1000L);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        t = format.format(date);
        return t;
    }

    public String getDayFormat(Integer time) {
        String t;
        String days[] = {"Domenica", "Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato"};
        String months[] = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000L);
        calendar.setTimeZone(TimeZone.getDefault());

        int dayindex = calendar.get(Calendar.DAY_OF_WEEK);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        t = days[dayindex - 1] + " " + day + " " + months[month];
        return t;
    }


    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public boolean checkGpsEnable() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public boolean checkInternetConnession() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
