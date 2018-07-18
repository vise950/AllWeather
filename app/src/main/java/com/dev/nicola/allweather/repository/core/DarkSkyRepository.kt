package com.dev.nicola.allweather.repository.core

import android.content.Context
import co.eggon.eggoid.extension.isConnectionAvailable
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import com.dev.nicola.allweather.util.RealmLiveData
import javax.inject.Inject

class DarkSkyRepository @Inject constructor(var context: Context,
                                            var darkSkyLocalRepository: DarkSkyLocalRepository,
                                            var darkSkyRemoteRepository: DarkSkyRemoteRepository) {

    fun updateDarkSkyWeather(lat: Double, lng: Double) {
        if (context.isConnectionAvailable())
            darkSkyRemoteRepository.getDarkSkyData(lat, lng)
    }

    fun getDarkSkyWeather(lat: Double, lng: Double): RealmLiveData<RootDarkSky> =
            darkSkyLocalRepository.getData(lat, lng)
}