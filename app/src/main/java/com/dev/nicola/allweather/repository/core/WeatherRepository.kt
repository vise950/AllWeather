package com.dev.nicola.allweather.repository.core

import android.content.Context
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.repository.local.WeatherLocalRepository
import com.dev.nicola.allweather.repository.remote.WeatherRemoteRepository
import com.dev.nicola.allweather.util.RealmLiveData
import com.ewt.nicola.common.extension.isConnectionAvailable

class WeatherRepository(var context: Context) {
    private var weatherLocalRepository: WeatherLocalRepository = WeatherLocalRepository()
    private var weatherRemoteRepository: WeatherRemoteRepository = WeatherRemoteRepository()

    fun fetchDarkSkyWeather(lat: Double, lng: Double) {
        if (context.isConnectionAvailable())
            weatherRemoteRepository.fetchDarkSky(lat, lng)
    }

    fun getDarkSkyWeather(lat: Double, lng: Double): RealmLiveData<RootDarkSky> = weatherLocalRepository.getData(lat, lng)
}