package com.dev.nicola.allweather.application

import android.app.Application
import com.dev.nicola.allweather.di.component.AppComponent
import com.dev.nicola.allweather.di.component.DaggerAppComponent
import com.dev.nicola.allweather.di.module.AppModule

class Init : Application() {

    companion object {
        const val DARK_SKY_BASE_URL = "https://api.darksky.net/"
        const val APIXU_BASE_URL = "https://api.apixu.com/"
        const val YAHOO_BASE_URL = "https://query.yahooapis.com/"
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}