package com.dev.nicola.allweather.service

import com.dev.nicola.allweather.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Created by Nicola on 08/04/2016.
 */
class PlaceAutocompleteService {

    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private val data: Map<String, String>? = null

//    private val suggestionsList: ArrayList<SearchSuggestion>? = null

//    public PlaceAutocompleteService() {
//        data = new HashMap<>();
//        data.put("types", TYPES);
//        data.put("key", API_KEY);
//        data.put("language", Locale.getDefault().getLanguage());
//    }

    companion object {

        private val TAG = PlaceAutocompleteService::class.java.simpleName

        private const val BASE_URL = "https://maps.googleapis.com/"
        private const val URL_TYPE = "maps/api/place/autocomplete"
        private const val RETURN = "/json"
        private val TYPES = "(cities)"
        private val API_KEY = BuildConfig.GOOGLE_API_AUTOCOMPLETE_KEY
    }

    //    private void autocomplete(Context context,String query) {
    //        String url;
    //        suggestionsList = new ArrayList<>();
    //        String suggestion;
    //
    //        url = PLACES_API_BASE + TYPE_AUTOCOMPLETE +
    //                OUT_JSON +
    //                "?input=" + query +
    //                "&types=" + TYPES +
    //                "&language=" + Locale.getDefault().getLanguage() +
    //                "&key=" + GOOGLE_API_KEY;
    //
    //        Log.d(TAG, "url autocomplete " + url);
    //
    //        OkHttpClient client = new OkHttpClient();
    //        Request request = new Request.Builder()
    //                .url(url)
    //                .build();
    //
    //        try {
    //            Response response = client.newCall(request).execute();
    //            String responseData = response.body().string();
    //
    //            JSONObject object = new JSONObject(responseData);
    //            JSONArray predsJsonArray = object.getJSONArray("predictions");
    //            if (predsJsonArray.length() == 0) {
    //                suggestionsList.add(new SearchItem(mContext.getString(R.string.no_result_suggestion)));
    //            } else {
    //                for (int i = 0; i < 3; i++) {
    //                    suggestion = predsJsonArray.getJSONObject(i).getString("description");
    //                    Log.d(TAG, "searchView suggestion " + suggestion);
    //                    int index = suggestion.indexOf(',');
    //                    int lastIndex = suggestion.lastIndexOf(',');
    //                    if (index != lastIndex)
    //                        suggestion = suggestion.substring(0, index) + suggestion.substring(lastIndex, suggestion.length());
    //
    //                    suggestionsList.add(new SearchItem(suggestion));
    //                }
    //            }
    //
    //        } catch (IOException | JSONException e) {
    //            Log.d(TAG, "exception:" + e);
    //        }
    //    }


    //    mSearchView.hideProgress();
    //    ArrayList<SearchResult> searchResults = new ArrayList<>();
    //    //this is for later use when user click on some item
    //    searchedQuestions = new ArrayList<>();
    //    try {
    //        if(result.getBoolean("Success")){
    //            JSONArray jsonArray = result.getJSONArray("Data");
    //            for(int i=0;i<jsonArray.length();i++){
    //                Log.i(LOG_TAG,jsonArray.get(i).toString());
    //                Question question = new Gson().fromJson(jsonArray.get(i).toString(),Question.class);
    //                searchedQuestions.add(question);
    //                searchResults.add(new SearchResult(question.getQuestion()));
    //                mSearchView.swapSuggestions(searchResults);
    //            }
    //        }else{
    //            searchResults.add(new SearchResult("There is no question like you want."));
    //            mSearchView.swapSuggestions(searchResults);
    //        }
    //    } catch (JSONException e) {
    //        Log.e(LOG_TAG,e.toString());
    //        searchResults.add(new SearchResult("Something goes wrong try again later."));
    //        mSearchView.swapSuggestions(searchResults);
    //    }

    //    public void search(String query) {
    //         suggestionsList = new ArrayList<>();
    //
    //        if (data != null) {
    //            data.put("input", query);
    //        }
    //
    //        getService().getPrediction(data).enqueue(new Callback<Prediction>() {
    //            @Override
    //            public void onResponse(Call<Prediction> call, retrofit2.Response<Prediction> response) {
    //                if (response.isSuccessful() && response.body().getStatus().equals("OK")) {
    //                    for (int i = 0; i < response.body().getPredictions().size(); i++) {
    //                        suggestionsList.add(new PredictionResult(response.body().getPredictions().get(i).getDescription()));
    //                        Log.e(TAG,"add "+response.body().getPredictions().get(i).getDescription());
    //                    }
    //                }
    //            }
    //
    //            @Override
    //            public void onFailure(Call<Prediction> call, Throwable t) {
    //                Log.e(TAG, "onFailure " + t);
    //            }
    //        });
    //
    //    }


    //    public ArrayList<SearchSuggestion> getSuggestion() {
    //        return suggestionsList;
    //    }


}