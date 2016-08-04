package com.dev.nicola.allweather.Provider;

import android.util.Log;

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

    public JSONObject getData(String provider, Double latitude, Double longitude) {
        String url;

        switch (provider) {

            case "ForecastIO":
                mBuilder = new StringBuilder("https://api.forecast.io/forecast/");
                mBuilder.append("dfdabe66b08f57ce02d697bb9fe2b5d1");
                mBuilder.append("/");
                mBuilder.append(latitude);
                mBuilder.append(",");
                mBuilder.append(longitude);
                mBuilder.append("?units=si");
                mBuilder.append("&lang=");
                mBuilder.append(Locale.getDefault().getLanguage());
                mBuilder.append("&exclude=minutely,alerts,flags");
                break;

            case "Apixu":
                mBuilder = new StringBuilder("https://api.apixu.com/v1/forecast.json");
                mBuilder.append("?key=");
                mBuilder.append("f6583f8fe8854178a36175631163003");
                mBuilder.append("&q=");
                mBuilder.append(latitude);
                mBuilder.append(",");
                mBuilder.append(longitude);
                mBuilder.append("&days=10");
                break;
        }

        url = mBuilder.toString();

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

