package com.dev.nicola.allweather.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nicola on 31/05/2016.
 */
public class LocationIP {

    private static final String BASE = " http://api.ipinfodb.com/v3/ip-city/";
    private static final String FORMAT = "?format=json";
    private static final String API_KEY = "4eea81c1ec8394806a3f5f774bbcd4f97d680cfcaf47e7cf68be05589ed80a36";
    /**
     * Example request:
     * http://api.ipinfodb.com/v3/ip-city/?format=json&key=API_KEY&ip=IP_ADDRESS
     * <p/>
     * Example response:
     * {
     * "statusCode" : "OK",
     * "statusMessage" : "",
     * "ipAddress" : "74.125.45.100",
     * "countryCode" : "US",
     * "countryName" : "UNITED STATES",
     * "regionName" : "CALIFORNIA",
     * "cityName" : "MOUNTAIN VIEW",
     * "zipCode" : "94043",
     * "latitude" : "37.3861",
     * "longitude" : "-122.084",
     * "timeZone" : "-07:00"
     * }
     */

    private static String TAG = LocationIP.class.getSimpleName();
    public Double latitude;
    public Double longitude;
    private StringBuilder mBuilder;
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;
    private JSONObject mObject;

    public String getExternalIP() {
        String url = "https://wtfismyip.com/text";
        String ip = null;
        mClient = new OkHttpClient();
        mRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            mResponse = mClient.newCall(mRequest).execute();
            ip = mResponse.body().string();
        } catch (IOException e) {
            Log.d(TAG, "exception " + e);
        }
        return ip;
    }

    public void getLocation(String IP) {
        String url;

        mBuilder = new StringBuilder(BASE);
        mBuilder.append(FORMAT);
        mBuilder.append("&key=");
        mBuilder.append(API_KEY);
        mBuilder.append("&ip=");
        mBuilder.append(IP);
        url = mBuilder.toString();

        mClient = new OkHttpClient();
        mRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            mResponse = mClient.newCall(mRequest).execute();
            String responseData = mResponse.body().string();

            mObject = new JSONObject(responseData);
            latitude = mObject.getDouble("latitude");
            longitude = mObject.getDouble("longitude");
        } catch (IOException | JSONException e) {
            Log.d(TAG, "exception:" + e);
        }
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

}
