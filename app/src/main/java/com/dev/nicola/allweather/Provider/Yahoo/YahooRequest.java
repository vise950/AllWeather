package com.dev.nicola.allweather.Provider.Yahoo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nicola on 21/07/2016.
 */
public class YahooRequest {

    private static String TAG = YahooRequest.class.getSimpleName();

    private final String URL = "https://query.yahooapis.com/v1/public/yql?q=";
    private final String QUERY = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")";
    private final String FORMAT = "&format=json";

    private StringBuilder mBuilder;
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;

    public YahooRequest() {
    }

    public String setUrl(String location) {
        String url;

        mBuilder = new StringBuilder(URL);
        mBuilder.append(String.format(QUERY, location));
        mBuilder.append(FORMAT);

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
