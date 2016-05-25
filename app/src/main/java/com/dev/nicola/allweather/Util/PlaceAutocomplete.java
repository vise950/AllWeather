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
    public List<SearchItem> suggestionsList;
    private StringBuilder mBuilder;
    private OkHttpClient mClient;
    private Request mRequest;
    private Response mResponse;
    private JSONObject mObject;

    public void autocomplete(String query) {
        String url;
        suggestionsList = new ArrayList<>();
        String suggestion;

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
            JSONArray predsJsonArray = mObject.getJSONArray("predictions");
            for (int i = 0; i < predsJsonArray.length(); i++) {
                suggestion = predsJsonArray.getJSONObject(i).getString("description");
                Log.d(TAG, "Suggestion " + suggestion);
                suggestionsList.add(new SearchItem(suggestion));
            }

        } catch (IOException | JSONException e) {
            Log.d(TAG, "exception:" + e);
        }
//        return suggestionsList;
    }


    public List<SearchItem> getSuggestionList() {
        return suggestionsList;
    }

}
