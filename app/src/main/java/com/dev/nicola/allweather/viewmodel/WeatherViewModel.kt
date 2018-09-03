package com.dev.nicola.allweather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.repository.core.DarkSkyRepository
import com.dev.nicola.allweather.util.RealmLiveData

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val darkSkyRepository = DarkSkyRepository(application)

    fun updateWeather(lat: Double, lng: Double) {
        darkSkyRepository.fetchDarkSkyWeather(lat, lng)
    }

    fun getWeather(lat: Double, lng: Double): RealmLiveData<RootDarkSky> = darkSkyRepository.getDarkSkyWeather(lat, lng)
}