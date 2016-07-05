package com.dev.nicola.allweather.Provider.Apixu;

import android.content.Context;
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

    private Context mContext;

    private StringBuilder mBuilder;
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;

    public ApixuRequest(Context context) {
        this.mContext = context;
    }

    public String setUrl(double latitude, double longitide) {
        String url;

        mBuilder = new StringBuilder(URL);
        mBuilder.append("?key=" + API_KEY);
        mBuilder.append("&q=" + latitude + "," + longitide);
        mBuilder.append("&days=8");

        url = mBuilder.toString();
        Log.d(TAG, "url " + url);

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
