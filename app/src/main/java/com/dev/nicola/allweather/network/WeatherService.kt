package com.dev.nicola.allweather.network

import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.model.apixu.RootApixu
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.model.yahoo.RootYahoo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.*

interface WeatherService {

    companion object {
        private const val DARK_SKY_API_KEY = BuildConfig.DARKSKY_API_KEY
        private const val APIXU_API_KEY = BuildConfig.APIXU_API_KEY
    }

    @GET("forecast/$DARK_SKY_API_KEY/{latitude},{longitude}")
    fun getDarkSkyData(@Url url: String,
                       @Path("latitude") latitude: Double,
                       @Path("longitude") longitude: Double,
                       @Query("units") units: String = "us",
                       @Query("lang") language: String = Locale.getDefault().language,
                       @Query("exclude") exclude: String = "minutely,alerts,flags")
            : Deferred<Response<RootDarkSky>>

    @GET("v1/forecast.json")
    fun getApixuData(@Query("q") coordinates: String,
                     @Query("days") days: Int = 10,
                     @Query("key") apiKey: String = APIXU_API_KEY)
            : Deferred<RootApixu>

    @GET("v1/public/yql")
    fun getYahooData(@Query("q") query: String,
                     @Query("format") format: String = "json")
            : Deferred<RootYahoo>

}
