package com.dev.nicola.allweather.di.component

import com.dev.nicola.allweather.di.module.AppModule
import com.dev.nicola.allweather.di.module.DatabaseModule
import com.dev.nicola.allweather.di.module.NetworkModule
import com.dev.nicola.allweather.di.module.RepositoryModule
import com.dev.nicola.allweather.ui.activity.HomeActivity
import com.dev.nicola.allweather.ui.activity.WeatherPlaceActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, RepositoryModule::class,NetworkModule::class])
interface AppComponent {
    fun inject(activity: HomeActivity)
    fun inject(activity: WeatherPlaceActivity)
}