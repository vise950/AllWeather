package com.dev.nicola.allweather.retrofit

import android.content.Context
import com.dev.nicola.allweather.utils.Utils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherClient(val context: Context) {

    object WeatherUrl {
        const val DARK_SKY_BASE_URL = "https://api.darksky.net/"
        const val APIXU_BASE_URL = "https://api.apixu.com/"
        const val YAHOO_BASE_URL = "https://query.yahooapis.com/"
    }

    val service: WeatherService
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(Utils.ServiceHelper.ulrProvider(context))
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit.create(WeatherService::class.java)
        }
}


