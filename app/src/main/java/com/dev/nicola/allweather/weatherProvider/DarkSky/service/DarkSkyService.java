package com.dev.nicola.allweather.weatherProvider.DarkSky.service;

import com.dev.nicola.allweather.BuildConfig;
import com.dev.nicola.allweather.weatherProvider.DarkSky.model.RootData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Nicola on 09/01/2017.
 */

public interface DarkSkyService {

    String API_KEY = BuildConfig.DARKSKY_API_KEY;

    @GET("forecast/" + API_KEY + "/{latitude},{longitude}?units=us&lang=en&exclude=minutely,alerts,flags")
    Call<RootData> getDarkSkyData(@Path("latitude") double latitude, @Path("longitude") double longitude);
}
