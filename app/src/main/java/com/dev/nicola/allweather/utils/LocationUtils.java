package com.dev.nicola.allweather.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nicola on 18/08/2016.
 */
public class LocationUtils {

    public static String getLocationName(Context context, double latitude, double longitude) {
        String cityName = "Not Found";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        Log.d("LocationUtils", "location latitude " + latitude);
        Log.d("LocationUtils", "location longitude " + longitude);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 5);
            if (addresses.size() > 0) {
                cityName = addresses.get(0).getLocality();
                Log.d("LocationUtils", "location size " + addresses.size());
                Log.d("LocationUtils", "location 1 " + addresses.get(0).getLocality());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    public static Location getCoordinateByName(Context context, String cityName) {
        Location location = null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
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
}
