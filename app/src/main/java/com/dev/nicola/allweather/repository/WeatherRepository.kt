package com.dev.nicola.allweather.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import co.eggon.eggoid.extension.error
import co.eggon.eggoid.extension.isConnectionAvailable
import com.dev.nicola.allweather.model.Weather
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import com.dev.nicola.allweather.utils.PreferencesHelper
import com.dev.nicola.allweather.utils.WeatherProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WeatherRepository {

    val weatherData: MediatorLiveData<Weather> = MediatorLiveData()

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
                weatherData.addSource(getDarkSkyWeather(disposable, lat, lng), {
                    it?.let {
                        weatherData.value = Weather(it)
                    }
                })
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
}