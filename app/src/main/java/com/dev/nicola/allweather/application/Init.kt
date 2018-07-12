package com.dev.nicola.allweather.application

import android.app.Application
import com.dev.nicola.allweather.di.component.AppComponent
import com.dev.nicola.allweather.di.component.DaggerAppComponent
import com.dev.nicola.allweather.di.module.AppModule
import com.dev.nicola.allweather.util.isDebug
import com.facebook.stetho.Stetho

class Init : Application() {

    companion object {
        private var INSTANCE: Init? = null

        fun get(): Init = INSTANCE!!
    }

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        if (isDebug()) {
            Stetho.initializeWithDefaults(this)
        }

        INSTANCE = this
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}


class Injector {
    companion object {
        fun get(): AppComponent = Init.get().appComponent
    }
}