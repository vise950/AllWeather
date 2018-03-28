package com.dev.nicola.allweather.application

import android.app.Application
import com.dev.nicola.allweather.di.component.AppComponent
import com.dev.nicola.allweather.di.component.DaggerAppComponent
import com.dev.nicola.allweather.di.module.AppModule

class Init : Application() {

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