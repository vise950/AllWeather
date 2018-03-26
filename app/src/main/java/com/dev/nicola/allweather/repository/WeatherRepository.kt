package com.dev.nicola.allweather.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import co.eggon.eggoid.extension.isConnectionAvailable
import com.dev.nicola.allweather.model.Weather
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WeatherRepository {

    private val weatherData: MediatorLiveData<Weather> = MediatorLiveData()


    private val context: Context

    private val darkSkyLocalRepository: DarkSkyLocalRepository
    private val darkSkyRemoteRepository: DarkSkyRemoteRepository

    @Inject constructor(context: Context, darkSkyLocalRepository: DarkSkyLocalRepository, darkSkyRemoteRepository: DarkSkyRemoteRepository) {
        this.context = context
        this.darkSkyLocalRepository = darkSkyLocalRepository
        this.darkSkyRemoteRepository = darkSkyRemoteRepository
    }

//    @Inject
//    lateinit var darkSkyLocalRepository: DarkSkyLocalRepository
//    @Inject
//    lateinit var darkSkyRemoteRepository: DarkSkyRemoteRepository
//
//
    fun getWeather(disposable: CompositeDisposable, lat: Double, lng: Double): LiveData<Weather> {
//        when (weatherProvider) {
//            WeatherProvider.DARK_SKY -> {
        weatherData.addSource(getDarkSkyWeather(disposable, lat, lng), {
            it?.let {
                weatherData.value = Weather(it)
            }
        })
//            }
//            WeatherProvider.APIXU -> {
//            }
//            WeatherProvider.YAHOO -> {
//            }
//            else -> {
//            }
//        }
//
        return weatherData
    }

    private fun getDarkSkyWeather(disposable: CompositeDisposable, lat: Double, lng: Double): LiveData<RootDarkSky> {
        if (context.isConnectionAvailable()) {
            darkSkyRemoteRepository.getDarkSkyData(disposable, lat, lng)
        }

        return MediatorLiveData()
    }
}