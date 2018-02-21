package com.dev.nicola.allweather.application

import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.dev.nicola.allweather.di.component.AppComponent
import com.dev.nicola.allweather.di.component.DaggerAppComponent
import com.dev.nicola.allweather.di.module.AppModule
import net.danlew.android.joda.JodaTimeAndroid

class Init : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
        JodaTimeAndroid.init(this)
    }


    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}

@GlideModule
class AppGlideModule : AppGlideModule()