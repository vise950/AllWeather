package com.dev.nicola.allweather.application

import android.annotation.SuppressLint
import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Init : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var realm: Realm? = null
        private var okHttpClient: OkHttpClient? = null
        private var retrofit: Retrofit? = null

        fun getRealmInstance(): Realm =
                realm?.let {
                    it
                } ?: run {
                    Realm.getDefaultInstance()
                            .also {
                                realm = it
                            }
                }

        private fun getOkHttpClient(): OkHttpClient =
                okHttpClient?.let {
                    it
                } ?: run {
                    OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build()
                            .also {
                                okHttpClient = it
                            }
                }

        fun getRetrofit(baseUrl: String): Retrofit =
                retrofit?.let {
                    it
                } ?: run {
                    Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(getOkHttpClient())
                            .addCallAdapterFactory(CoroutineCallAdapterFactory())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .also {
                                retrofit = it
                            }
                }
    }

    override fun onCreate() {
        super.onCreate()
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())
    }
}

@GlideModule
class MyAppGlideModule : AppGlideModule()