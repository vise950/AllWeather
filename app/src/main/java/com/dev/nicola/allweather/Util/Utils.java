package com.dev.nicola.allweather.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
    private Resources mResources;

    public Utils(Context context, Resources resources) {
        this.mContext = context;
        this.mResources = resources;
    }

    public String getLocationName(double latitude, double longitude) {
        String cityName = "Not Found";
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0)
                cityName = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "exception " + e);
        }
        return cityName;
    }

    public Location getCoordinateByName(String cityName) {
        Location location = null;
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(cityName, 1);
            location = new Location(LocationManager.PASSIVE_PROVIDER);
            if (addresses.size() > 0) {
                location.setLatitude(addresses.get(0).getLatitude());
                location.setLongitude(addresses.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }


    public int getIcon(String condition) {
        int icon = 0;

        switch (condition) {
            case "1000":
            case "clear-day":
                icon = R.drawable.clear_day_2;
                break;
            case "clear-night":
                icon = R.drawable.clear_night_2;
                break;
            case "partly-cloudy-day":
                icon = R.drawable.cloud_day_2;
                break;
            case "partly-cloudy-night":
                icon = R.drawable.cloud_night_2;
                break;
            case "1006":
            case "cloudy":
                icon = R.drawable.cloud_2;
                break;
            case "rain":
                icon = R.drawable.rain_2;
                break;
            case "snow":
                icon = R.drawable.snow_2;
                break;
            case "sleet":
                icon = R.drawable.sleet_2;
                break;
            case "wind":
                icon = R.drawable.wind_2;
                break;
            case "fog":
                icon = R.drawable.fog_2;
                break;
            default:
                icon = R.drawable.unknown;
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

    //type = 0 --> 24h format
    //type = 1 --> 12h format
    public String getHourFormat(Integer time, String timezone, int type) {
        String t;
        SimpleDateFormat format = null;
        Date date = new Date(time * 1000L);
        if (type == 0)
            format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        if (type == 1)
            format = new SimpleDateFormat("HH:mm a", Locale.getDefault());

        format.setTimeZone(TimeZone.getTimeZone(timezone));
        t = format.format(date);

        return t;
    }

    public String getDayFormat(Integer time) {
        String t;
        String days[] = mResources.getStringArray(R.array.days);
        String months[] = mResources.getStringArray(R.array.months);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000L);
        calendar.setTimeZone(TimeZone.getDefault());

        int dayindex = calendar.get(Calendar.DAY_OF_WEEK);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        t = days[dayindex - 1] + " " + day + " " + months[month];
        return t;
    }

    public int getLocalTime() {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        return h;
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

    public void setTheme(Activity activity, String theme) {
        if (theme.equals("1"))
            activity.setTheme(R.style.lightTheme);
        else
            activity.setTheme(R.style.darkTheme);
    }

}
