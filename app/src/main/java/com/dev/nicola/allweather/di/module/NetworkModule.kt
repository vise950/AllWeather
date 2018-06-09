package com.dev.nicola.allweather.di.module

import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.utils.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
            GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRxCallAdapter(): RxJava2CallAdapterFactory =
            RxJava2CallAdapterFactory.create()


    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory,
                              rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit.Builder =
            Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)


    @Provides
    @Singleton
    @DarkSky
    fun provideDarkSkyRetrofit(builder: Retrofit.Builder): Retrofit =
            builder.baseUrl(Constant.DARK_SKY_BASE_URL).build()
}