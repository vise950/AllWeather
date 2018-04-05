package com.dev.nicola.allweather.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
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
//                weatherData.addSource(getDarkSkyWeather(disposable, lat, lng), {
//                    it?.let {
//                        weatherData.value = Weather(it)
//                    }
//                })

                weatherData.addSource(getDarkSkyWeather(disposable, lat, lng), { root ->
                    root?.let {
                        getDarkSkyDailyData(root).value?.let {
                            it[0].latitude.error("STEP_1")
                            root.daily.data = it
                        }
                        "STEP_2".error()
                        weatherData.value = Weather(it)
                    }
                })

//                getDarkSkyWeather(disposable, lat, lng).also {
//                    it.value?.latitude.error("STEP_1")
//                    Transformations.switchMap(it, { root ->
//                        getDarkSkyDailyData(root).also {
//                            it.value?.get(0)?.latitude.error("STEP_2")
//                            Transformations.map(it, {
//                                root.daily.data = it
//                                root.latitude.error("STEP_3")
//                                root
//                            })
//                        }
//                    })
//                }.let {
//                    it.value?.latitude.error("STEP_4")
//                    weatherData.value = Weather(it.value)
//                }

            }
            WeatherProvider.APIXU -> {
            }
            WeatherProvider.YAHOO -> {
            }
        }
    }

    private fun getDarkSkyWeather(disposable: CompositeDisposable, lat: Double, lng: Double): LiveData<RootDarkSky> {
        if (context.isConnectionAvailable()) {
            darkSkyRemoteRepository.getDarkSkyData(disposable, lat, lng)
        }
        return darkSkyLocalRepository.getData(lat, lng)
    }

    private fun getDarkSkyDailyData(data: RootDarkSky): LiveData<List<DailyDataDarkSky>> = darkSkyLocalRepository.getDailyData(data)

    /*

    var weatherLiveData = weatherRepository.getWeather(disposable,
                coordinates?.first ?: 0.0,
                coordinates?.second ?: 0.0)
        weatherLiveData = Transformations.switchMap(weatherLiveData, { weather ->
            weather?.let {
                val dailyData = weatherRepository.getDailyData(it)
                Transformations.map(dailyData, {
                    weather.daily.data = it
                    weather
                })
            }
        })
        return weatherLiveData

     */
}