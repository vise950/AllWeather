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
//        Log.d("LocationUtils","latitude "+latitude);
//        Log.d("LocationUtils","longitude "+longitude);
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            Log.d("LocationUtils","list size "+addresses.size());
            if (addresses.size() > 0) {
                cityName = addresses.get(0).getLocality();
//                Log.d("LocationUtils","city "+cityName);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("LocationUtils", "exception " + e); // FIXME: 22/09/2016 exception java.io.IOException: Service not Available forse è un problema di play services
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
