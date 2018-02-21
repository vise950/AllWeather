package com.dev.nicola.allweather.di.component

import android.support.v7.app.AppCompatActivity
import com.dev.nicola.allweather.di.module.AppModule
import com.dev.nicola.allweather.di.module.DatabaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(activity: AppCompatActivity)
}