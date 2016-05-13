package com.dev.nicola.allweather.Util;

import android.util.Log;

import com.lapism.searchview.SearchItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nicola on 08/04/2016.
 */
public class PlaceAutocomplete {

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String TYPES = "geocode";
    private static final String GOOGLE_API_KEY = "AIzaSyASZMgu_N3JTvHcbLhs57ZEKOEyqrIPF6g";
    private static String TAG = PlaceAutocomplete.class.getSimpleName();
    private JSONObject mObject;
    private StringBuilder mBuilder;
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;

    public List autocomplete(String query) {
        String url;

        List<SearchItem>suggestionsList = null;

        mBuilder = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
        mBuilder.append("?input=" + query);
        mBuilder.append("&types=" + TYPES);
        mBuilder.append("&key=" + GOOGLE_API_KEY);
        url = mBuilder.toString();
        Log.d(TAG, "autocomplete url " + url);

        mClient = new OkHttpClient();
        mRequest = new Request.Builder()
                .url(url)
                .build();

        try {
            mResponse = mClient.newCall(mRequest).execute();
            String responseData = mResponse.body().string();
            mObject = new JSONObject(responseData);
            Log.d(TAG, "object " + mObject);

            JSONArray predsJsonArray = mObject.getJSONArray("predictions");

            // Extract the Place descriptions from the results
//            List<String> list = new ArrayList<>(predsJsonArray.length());
            suggestionsList = new ArrayList<>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                suggestionsList.add(new SearchItem(predsJsonArray.getJSONObject(i).getString("description")));
            }

        } catch (IOException | JSONException e) {
            Log.d(TAG, "exception:" + e);
        }

        return suggestionsList;
    }
}
