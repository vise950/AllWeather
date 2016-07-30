package com.dev.nicola.allweather.Provider.Apixu;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nicola on 04/06/2016.
 */
public class ApixuRequest {

    private static String TAG = ApixuRequest.class.getSimpleName();

    private final String URL = "https://api.apixu.com/v1/forecast.json";
    private final String API_KEY = "f6583f8fe8854178a36175631163003";
    private final String DAYS = "&days=10";

    private StringBuilder mBuilder;
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;

    public ApixuRequest() {
    }

    public String setUrl(double latitude, double longitude) {
        String url;

        mBuilder = new StringBuilder(URL);
        mBuilder.append("?key=");
        mBuilder.append(API_KEY);
        mBuilder.append("&q=");
        mBuilder.append(latitude);
        mBuilder.append(",");
        mBuilder.append(longitude);
        mBuilder.append(DAYS);

        url = mBuilder.toString();
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
