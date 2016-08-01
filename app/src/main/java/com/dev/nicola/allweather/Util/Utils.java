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
import java.text.ParseException;
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


    public int getWeatherIcon(String condition) {
        int icon;

        switch (condition) {
            case "1000":
            case "clear-day":
                icon = R.drawable.clear_day;
                break;
            case "clear-night":
                icon = R.drawable.clear_night;
                break;
            case "1003":
            case "partly-cloudy-day":
                icon = R.drawable.partly_cloudy_day;
                break;
            case "partly-cloudy-night":
                icon = R.drawable.partly_cloudy_night;
                break;
            case "1087":
            case "1009":
            case "1006":
            case "cloudy":
                icon = R.drawable.cloud;
                break;
            case "1195":
            case "1192":
            case "1189":
            case "1186":
            case "1183":
            case "1063":
            case "rain":
                icon = R.drawable.rain;
                break;
            case "1225":
            case "1222":
            case "1219":
            case "1216":
            case "1213":
            case "1210":
            case "1114":
            case "1066":
            case "snow":
                icon = R.drawable.snow;
                break;
            case "1072":
            case "1069":
            case "sleet":
                icon = R.drawable.sleet;
                break;
            case "1117":
            case "wind":
                icon = R.drawable.wind;
                break;
            case "1147":
            case "1135":
            case "1030":
            case "fog":
                icon = R.drawable.fog;
                break;
            case "1273":
            case "1276":
            case "1279":
            case "1282":
                icon=R.drawable.storm;
                break;
            default:
                icon = R.drawable.unknown;
                break;
        }

        return icon;
    }

    public int getImage(long sunrise, long sunset, long time, String sSunrise, String sSunset) {
        int wall;

        if (sSunrise != null && sSunset != null) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String date = df.format(c.getTime());
            SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy h:mm a", Locale.getDefault());
            try {
                Date d = dt.parse(date + " " + sSunrise);
                sunrise = d.getTime();
                Date d1 = dt.parse(date + " " + sSunset);
                sunset = d1.getTime();
                time = time * 1000L;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (time >= sunrise - 1800L && time <= sunrise + 1800L)
            wall = R.drawable.sunset_wall;
        else if (time > sunrise + 1800L && time < sunset - 1800L)
            wall = R.drawable.day_wall;
        else if (time >= sunset - 1800L && time <= sunset + 1800L)
            wall = R.drawable.sunrise_wall;
        else
            wall = R.drawable.night_wall;

        return wall;
    }


    public String getWindDirection(int degrees) {
        String direction;
        String cardinal[] = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        int n = (int) ((degrees / 22.5) + 0.5);
        direction = cardinal[n % cardinal.length];
        return direction;
    }


    public String getHourFormat(long time, String sTime, String units) {
        Date date = null;
        if (sTime != null) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String d = df.format(c.getTime());
            SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy h:mm a", Locale.getDefault());
            try {
                date = dt.parse(d + " " + sTime);
                Log.d(TAG, "date " + date);
                time = date.getTime();
                Log.d(TAG, "time " + time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            date = new Date(time * 1000L);
        }

        SimpleDateFormat hour = null;

        /*  h is used for AM/PM times (1-12).
            H is used for 24 hour times (1-24).
            a is the AM/PM marker
            m is minute in hour
            Two h's will print a leading zero: 01:13 PM. One h will print without the leading zero: 1:13 PM.
        */
        switch (units) {
            case "1":
                hour = new SimpleDateFormat("h:mm a", Locale.getDefault());
                break;
            case "2":
                hour = new SimpleDateFormat("H:mm", Locale.getDefault());
                break;
        }
        assert hour != null;
        return hour.format(date);
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
        return calendar.get(Calendar.HOUR_OF_DAY);
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
