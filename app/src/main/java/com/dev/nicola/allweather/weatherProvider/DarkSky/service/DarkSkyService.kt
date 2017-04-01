package com.dev.nicola.allweather.weatherProvider.DarkSky.service

import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.weatherProvider.DarkSky.model.RootDarkSky
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface DarkSkyService {

    companion object {
        private const val API_KEY = BuildConfig.DARKSKY_API_KEY
    }

    @GET("forecast/$API_KEY/{latitude},{longitude}")
    fun getDarkSkyData(@Path("latitude") latitude: Double,
                       @Path("longitude") longitude: Double,
                       @Query("units") units: String,
                       @Query("lang") language: String = Locale.getDefault().language,
                       @Query("exclude") exclude: String = "minutely,alerts,flags")
            : Observable<RootDarkSky>
}
