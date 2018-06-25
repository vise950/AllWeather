package com.dev.nicola.allweather.repository.core

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
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

    //    private lateinit var coordinates: Pair<Double, Double>
    private var coordinates = Pair(0.0, 0.0)

    var darkSkyData: MediatorLiveData<RootDarkSky> = MediatorLiveData()

    init {
        val data = Transformations.switchMap(darkSkyLocalRepository.getData(coordinates.first, coordinates.second)) { data ->
            data?.let {
                Transformations.map(darkSkyLocalRepository.getDailyData(data)) {
                    data.daily.data = it
                    Transformations.map(darkSkyLocalRepository.getHourlyData(data)) { data.hourly.data = it }
                    data
                }
            }
        }

        darkSkyData.addSource(data) { darkSkyData.value = it }
    }

    fun updateDarkSkyWeather(coordinates: Pair<Double, Double>) {
        this.coordinates = coordinates
        if (context.isConnectionAvailable()) {
            darkSkyRemoteRepository.getDarkSkyData(disposable, coordinates.first, coordinates.second)
        }
    }
}