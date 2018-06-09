package com.dev.nicola.allweather.repository

import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.model.Weather
import com.dev.nicola.allweather.repository.core.DarkSkyRepository
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import com.dev.nicola.allweather.utils.PreferencesHelper
import com.dev.nicola.allweather.utils.WeatherProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val context: Context) {

//    private val darkSkyRepository: DarkSkyRepository()

    var weatherData: MediatorLiveData<Weather> = MediatorLiveData()

    //    private lateinit var context: Context
//    @Inject
//    lateinit var darkSkyLocalRepository: DarkSkyLocalRepository
//    @Inject
//    lateinit var darkSkyRemoteRepository: DarkSkyRemoteRepository

//    @Inject constructor(context: Context, darkSkyLocalRepository: DarkSkyLocalRepository, darkSkyRemoteRepository: DarkSkyRemoteRepository) {
//        this.context = context
//        this.darkSkyLocalRepository = darkSkyLocalRepository
//        this.darkSkyRemoteRepository = darkSkyRemoteRepository
//    }

    @Inject
    @DarkSky
    lateinit var darkSkyLocalRepository: DarkSkyLocalRepository
    @Inject
    @DarkSky
    lateinit var darkSkyRemoteRepository: DarkSkyRemoteRepository

    private val prefs by lazy { PreferencesHelper(context) }

    init {
        when (prefs.weatherProvider) {
            WeatherProvider.DARK_SKY -> {
                weatherData.addSource(DarkSkyRepository(context, darkSkyLocalRepository, darkSkyRemoteRepository).darkSkyData,
                        { weatherData.value = Weather(it) })
            }
            WeatherProvider.YAHOO -> {
            }
            WeatherProvider.APIXU -> {
            }
        }
    }

    fun updateWeather(disposable: CompositeDisposable, lat: Double, lng: Double) {
        when (prefs.weatherProvider) {
            WeatherProvider.DARK_SKY -> {
//                val data = Transformations.switchMap(getDarkSkyData(lat, lng), { data ->
//                    Transformations.map(getDarkSkyDailyData(data), {
//                        data.daily.data = it
//                        Transformations.map(getDarkSkyHourlyData(data), {
//                            data.hourly.data = it
//                        })
//                        data
//                    })
//                })
//                weatherData.addSource(data, { weatherData.value = Weather(it) })

                DarkSkyRepository(context, darkSkyLocalRepository, darkSkyRemoteRepository).updateDarkSkyWeather(disposable, Pair(lat, lng))
            }
            WeatherProvider.APIXU -> {
            }
            WeatherProvider.YAHOO -> {
            }
        }
    }

//    private fun updateDarkSkyWeather(disposable: CompositeDisposable, lat: Double, lng: Double) {
//        if (context.isConnectionAvailable()) {
//            darkSkyRemoteRepository.getDarkSkyData(disposable, lat, lng)
//        }
//    }
//
//    private fun getDarkSkyData(lat: Double, lng: Double): LiveData<RootDarkSky> = darkSkyLocalRepository.getData(lat, lng)
//    private fun getDarkSkyDailyData(data: RootDarkSky): LiveData<List<DailyDataDarkSky>> = darkSkyLocalRepository.getDailyData(data)
//    private fun getDarkSkyHourlyData(data: RootDarkSky): LiveData<List<HourlyDataDarkSky>> = darkSkyLocalRepository.getHourlyData(data)
}