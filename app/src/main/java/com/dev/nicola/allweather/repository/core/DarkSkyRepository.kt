package com.dev.nicola.allweather.repository.core

import android.content.Context
import co.eggon.eggoid.extension.isConnectionAvailable
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import com.dev.nicola.allweather.util.RealmLiveData

class DarkSkyRepository(var context: Context) {
    private var darkSkyLocalRepository: DarkSkyLocalRepository = DarkSkyLocalRepository()
    private var darkSkyRemoteRepository: DarkSkyRemoteRepository = DarkSkyRemoteRepository()

    fun fetchDarkSkyWeather(lat: Double, lng: Double) {
        if (context.isConnectionAvailable())
            darkSkyRemoteRepository.fetchDarkSky(lat, lng)
    }

    fun getDarkSkyWeather(lat: Double, lng: Double): RealmLiveData<RootDarkSky> = darkSkyLocalRepository.getData(lat, lng)
}