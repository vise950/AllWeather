package com.dev.nicola.allweather.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import co.eggon.eggoid.extension.error
import co.eggon.eggoid.extension.isConnectionAvailable
import com.dev.nicola.allweather.model.Weather
import com.dev.nicola.allweather.model.darkSky.DailyDataDarkSky
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import com.dev.nicola.allweather.utils.PreferencesHelper
import com.dev.nicola.allweather.utils.WeatherProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WeatherRepository {

    var weatherData: MediatorLiveData<Weather> = MediatorLiveData()

    private lateinit var context: Context

    private var darkSkyLocalRepository: DarkSkyLocalRepository
    private var darkSkyRemoteRepository: DarkSkyRemoteRepository

    @Inject constructor(context: Context, darkSkyLocalRepository: DarkSkyLocalRepository, darkSkyRemoteRepository: DarkSkyRemoteRepository) {
        this.context = context
        this.darkSkyLocalRepository = darkSkyLocalRepository
        this.darkSkyRemoteRepository = darkSkyRemoteRepository
    }

    private val prefs by lazy { PreferencesHelper(context) }

    fun updateWeather(disposable: CompositeDisposable, lat: Double, lng: Double) {
        when (prefs.weatherProvider) {
            WeatherProvider.DARK_SKY -> {
//                weatherData.addSource(getDarkSkyWeather(lat, lng), { root ->
//                    "STEP_1".error()
//                    root?.let {
//                        "STEP_2".error()
//                        getDarkSkyDailyData(root).value?.let {
//                            "STEP_3".error()
//                            root.daily.data = it
//                        }
//                        "STEP_4".error()
//                        weatherData.value = Weather(it)
//                    }
//                })

                getDarkSkyWeather(lat, lng).also {
                    "STEP_1".error()
                    Transformations.switchMap(it, { root ->
                        "STEP_2".error()
                        getDarkSkyDailyData(root).also {
                            "STEP_3".error()
                            Transformations.map(it, {
                                "STEP_4".error()
                                root.daily.data = it
                                root
                            })
                        }
                    })
                }.let {
                    "STEP_5".error()
                    weatherData.value = Weather(it.value)
                }

                updateDarkSkyWeather(disposable, lat, lng)
            }
            WeatherProvider.APIXU -> {
            }
            WeatherProvider.YAHOO -> {
            }
        }
    }

    private fun updateDarkSkyWeather(disposable: CompositeDisposable, lat: Double, lng: Double) {
        if (context.isConnectionAvailable()) {
            darkSkyRemoteRepository.getDarkSkyData(disposable, lat, lng)
        }
    }

    private fun getDarkSkyWeather(lat: Double, lng: Double): LiveData<RootDarkSky> = darkSkyLocalRepository.getData(lat, lng)
    private fun getDarkSkyDailyData(data: RootDarkSky): LiveData<List<DailyDataDarkSky>> = darkSkyLocalRepository.getDailyData(data)
}