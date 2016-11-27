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

    private static final String BASE = "http://freegeoip.net/";
    private static final String FORMAT = "json/";


    private static String TAG = LocationIP.class.getSimpleName();
    public Double latitude;
    public Double longitude;
    private StringBuilder mBuilder;
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;
    private JSONObject mObject;


    /**
     * @return String with external IP
     * <p>
     * Example request IP:
     * https://wtfismyip.com/text
     * <p>
     * Example response:
     * 82.48.162.147
     */

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


    /**
     *
     * @param IP is external IP for create server request for location
     *
     * Example request position:
    http://freegeoip.net/json/IP_ADDRESS
     *
     * Example response:
    {
    ip: "82.48.162.147",
    country_code: "IT",
    country_name: "Italy",
    region_code: "34",
    region_name: "Veneto",
    city: "Creazzo",
    zip_code: "36051",
    time_zone: "Europe/Rome",
    latitude: 45.5315,
    longitude: 11.4779,
    metro_code: 0
    }
     */

    public void getLocation(String IP) {
        String url;

        mBuilder = new StringBuilder(BASE);
        mBuilder.append(FORMAT);
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
