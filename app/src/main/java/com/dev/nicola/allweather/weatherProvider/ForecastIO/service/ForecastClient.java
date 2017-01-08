package com.dev.nicola.allweather.weatherProvider.ForecastIO.service;

import com.dev.nicola.allweather.BuildConfig;

import retrofit2.Retrofit;

/**
 * Created by Nicola on 29/12/2016.
 */

public class ForecastClient {

    //    // https://api.darksky.net/forecast/dfdabe66b08f57ce02d697bb9fe2b5d1/37.421998333333335,-122.08400000000002?units=us&lang=en&exclude=minutely,alerts,flags

    private static final String BASE_URL="https://api.darksky.net/forecast/";
    private static final String API_KEY= BuildConfig.FORECASTIO_API_KEY;
    public static final String API_URL = BASE_URL + API_KEY;

    private static ForecastClient mForecastClient;
    private Retrofit mRetrofit;

    public static ForecastClient getClient() {
        if (mForecastClient == null)
            mForecastClient = new ForecastClient();
        return mForecastClient;
    }
//    private ForecastClient() {
//        mRetrofit = new Retrofit.Builder()
//                .baseUrl(API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .setClient(new OkClient(new OkHttpClient()))
//                .build();
//    }
}
