package com.dev.nicola.allweather.repository.core

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import co.eggon.eggoid.extension.error
import co.eggon.eggoid.extension.isConnectionAvailable
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DarkSkyRepository @Inject constructor(var context: Context,
                                            var darkSkyLocalRepository: DarkSkyLocalRepository,
                                            var darkSkyRemoteRepository: DarkSkyRemoteRepository,
                                            private val disposable: CompositeDisposable) {

    fun updateDarkSkyWeather(lat: Double, lng: Double) {
        if (context.isConnectionAvailable())
            darkSkyRemoteRepository.getDarkSkyData(disposable, lat, lng)
    }

    fun getDarkSkyWeather(lat: Double, lng: Double): LiveData<RootDarkSky> =
            Transformations.switchMap(darkSkyLocalRepository.getData(lat, lng)) { data ->
                data?.let {
                    Transformations.map(darkSkyLocalRepository.getDailyData(lat,lng)) {
                        data.daily.data = it
                        Transformations.map(darkSkyLocalRepository.getHourlyData(lat,lng)) {
                            it.error("hourly data")
                            data.hourly.data = it
                        }
                        data
                    }
                }
            }
}