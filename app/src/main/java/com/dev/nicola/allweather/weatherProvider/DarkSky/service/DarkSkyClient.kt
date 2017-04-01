package com.dev.nicola.allweather.weatherProvider.DarkSky.service

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DarkSkyClient(val context: Context) {

    private val BASE_URL = "https://api.darksky.net/"

    val service: DarkSkyService
        get() {
            val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit.create(DarkSkyService::class.java)
        }
}
