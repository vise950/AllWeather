package com.dev.nicola.allweather.utils;

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
    private static final String TYPES = "(cities)";
    private static final String GOOGLE_API_KEY = "AIzaSyASZMgu_N3JTvHcbLhs57ZEKOEyqrIPF6g";

    private static String TAG = PlaceAutocomplete.class.getSimpleName();
    public List<SearchItem> suggestionsList;

    public void autocomplete(String query) {
        String url;
        suggestionsList = new ArrayList<>();
        String suggestion;

        url = PLACES_API_BASE + TYPE_AUTOCOMPLETE +
                OUT_JSON +
                "?input=" +
                query +
                "&types=" +
                TYPES +
                "&key=" +
                GOOGLE_API_KEY;

        Log.d(TAG, "url autocomplete " + url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();

            JSONObject object = new JSONObject(responseData);
            JSONArray predsJsonArray = object.getJSONArray("predictions");
            for (int i = 0; i < 3; i++) {
                suggestion = predsJsonArray.getJSONObject(i).getString("description");
                Log.d(TAG, "suggestion " + suggestion);
                int index = suggestion.indexOf(',');
                int lastIndex = suggestion.lastIndexOf(',');
                if (index != lastIndex)
                    suggestion = suggestion.substring(0, index) + suggestion.substring(lastIndex, suggestion.length());

                suggestionsList.add(new SearchItem(suggestion));
            }

        } catch (IOException | JSONException e) {
            Log.d(TAG, "exception:" + e);
        }
    }


    public List<SearchItem> getSuggestionList() {
        return suggestionsList;
    }

}
