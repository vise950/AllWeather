package com.dev.nicola.allweather.repository.core

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import co.eggon.eggoid.extension.isConnectionAvailable
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import com.dev.nicola.allweather.utils.LATITUDE
import com.dev.nicola.allweather.utils.LONGITUDE
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DarkSkyRepository @Inject constructor(var context: Context,
                                            var darkSkyLocalRepository: DarkSkyLocalRepository,
                                            var darkSkyRemoteRepository: DarkSkyRemoteRepository,
                                            private val disposable: CompositeDisposable) {

    var darkSkyData: MediatorLiveData<RootDarkSky> = MediatorLiveData()

//    private var data: LiveData<RootDarkSky>? = null

    init {
//        val data = Transformations.switchMap(darkSkyLocalRepository.getData(LATITUDE, LONGITUDE)) { data ->
//            data?.let {
//                Transformations.map(darkSkyLocalRepository.getDailyData(data)) {
//                    data.daily.data = it
//                    Transformations.map(darkSkyLocalRepository.getHourlyData(data)) { data.hourly.data = it }
//                    data
//                }
//            }
//        }
//
//        data?.let { darkSkyData.addSource(it) { darkSkyData.value = it } }
    }

    fun updateDarkSkyWeather() {
        if (context.isConnectionAvailable())
            darkSkyRemoteRepository.getDarkSkyData(disposable, LATITUDE, LONGITUDE)

        getData()
    }

    //fixme la prima volta viene fatto l'observe 3 volte
    private fun getData() {
        Transformations.switchMap(darkSkyLocalRepository.getData(LATITUDE, LONGITUDE)) { data ->
            data?.let {
                Transformations.map(darkSkyLocalRepository.getDailyData(data)) {
                    data.daily.data = it
                    Transformations.map(darkSkyLocalRepository.getHourlyData(data)) { data.hourly.data = it }
                    data
                }
            }
        }?.let {
            darkSkyData.addSource(it) { darkSkyData.value = it }
        }
    }
}