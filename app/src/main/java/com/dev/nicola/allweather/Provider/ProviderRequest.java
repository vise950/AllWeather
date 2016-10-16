package com.dev.nicola.allweather.provider;

import android.util.Log;

import com.dev.nicola.allweather.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nicola on 04/08/2016.
 */
public class ProviderRequest {

    private static String TAG = ProviderRequest.class.getSimpleName();

    private StringBuilder mBuilder;
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;

    public ProviderRequest() {
    }

    public JSONObject getData(String provider, Double latitude, Double longitude, String place) {
        String url;

        switch (provider) {

            case "forecastIO":
                mBuilder = new StringBuilder("https://api.darksky.net/forecast/");
                mBuilder.append(com.dev.nicola.allweather.BuildConfig.FORECASTIO_API_KEY);
                mBuilder.append("/");
                mBuilder.append(latitude);
                mBuilder.append(",");
                mBuilder.append(longitude);
                mBuilder.append("?units=us");
                mBuilder.append("&lang=");
                mBuilder.append(Locale.getDefault().getLanguage());
                mBuilder.append("&exclude=minutely,alerts,flags");
                break;

            case "apixu":
                mBuilder = new StringBuilder("https://api.apixu.com/v1/forecast.json");
                mBuilder.append("?key=");
                mBuilder.append(BuildConfig.APIXU_API_KEY);
                mBuilder.append("&q=");
                mBuilder.append(latitude);
                mBuilder.append(",");
                mBuilder.append(longitude);
                mBuilder.append("&days=10");
                break;

            case "yahoo":
                mBuilder = new StringBuilder("https://query.yahooapis.com/v1/public/yql?q=");
                mBuilder.append("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=");
                mBuilder.append("\"");
                mBuilder.append(place);
                mBuilder.append("\")");
                mBuilder.append("&format=json");
                break;
        }

        url = mBuilder.toString();

        Log.d(TAG, "url " + url);

        JSONObject mObject;

        mClient = new OkHttpClient();
        mRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            mResponse = mClient.newCall(mRequest).execute();
            String responseData = mResponse.body().string();
            mObject = new JSONObject(responseData);
        } catch (IOException | JSONException e) {
            Log.d(TAG, "exception:" + e);
            return null;
        }
        return mObject;
    }

}

