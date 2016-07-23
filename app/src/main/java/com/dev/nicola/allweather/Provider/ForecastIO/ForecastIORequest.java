package com.dev.nicola.allweather.Provider.ForecastIO;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nicola on 29/04/2016.
 */
public class ForecastIORequest {

    private static String TAG = ForecastIORequest.class.getSimpleName();

    private final String URL = "https://api.forecast.io/forecast/";
    private final String API_KEY = "dfdabe66b08f57ce02d697bb9fe2b5d1";
    private final String UNITS = "?units=si";
    private final String LANGUAGE = "&lang=";
    private final String EXCLUDE = "&exclude=minutely,alerts,flags";

    private StringBuilder mBuilder;
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;


    public ForecastIORequest() {
    }


    public String setUrl(double latitude, double longitude) {
        String url;

        mBuilder = new StringBuilder(URL);
        mBuilder.append(API_KEY);
        mBuilder.append("/");
        mBuilder.append(latitude);
        mBuilder.append(",");
        mBuilder.append(longitude);
        mBuilder.append(UNITS);
        mBuilder.append(LANGUAGE);
        mBuilder.append(Locale.getDefault().getLanguage());
        mBuilder.append(EXCLUDE);

        url = mBuilder.toString();
        Log.d(TAG, "url:" + url);
        return url;
    }


    public JSONObject getData(String url) {
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