package com.dev.nicola.allweather.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import com.dev.nicola.allweather.application.Injector
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.model.Weather
import com.dev.nicola.allweather.repository.core.DarkSkyRepository
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import com.dev.nicola.allweather.util.PreferencesHelper
import com.dev.nicola.allweather.util.WeatherProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val context: Context) {

    private val prefs by lazy { PreferencesHelper(context) }
    private var weatherData: MediatorLiveData<Weather> = MediatorLiveData()

    @Inject
    lateinit var disposable: CompositeDisposable

    @Inject
    @DarkSky
    lateinit var darkSkyLocalRepository: DarkSkyLocalRepository
    @Inject
    @DarkSky
    lateinit var darkSkyRemoteRepository: DarkSkyRemoteRepository
    private lateinit var darkSkyRepository: DarkSkyRepository


    init {
        Injector.get().inject(this)

        when (prefs.weatherProvider) {
            WeatherProvider.DARK_SKY ->
                darkSkyRepository = DarkSkyRepository(context, darkSkyLocalRepository, darkSkyRemoteRepository, disposable)
            WeatherProvider.YAHOO -> {
            }
            WeatherProvider.APIXU -> {
            }
        }
    }

    fun updateWeather(lat: Double, lng: Double) {
        when (prefs.weatherProvider) {
            WeatherProvider.DARK_SKY -> darkSkyRepository.updateDarkSkyWeather(lat, lng)
            WeatherProvider.APIXU -> {
            }
            WeatherProvider.YAHOO -> {
            }
        }
    }

    fun getWeather(lat: Double, lng: Double): LiveData<Weather> {
        when (prefs.weatherProvider) {
            WeatherProvider.DARK_SKY -> weatherData.addSource(darkSkyRepository.getDarkSkyWeather(lat, lng)) { weatherData.value = Weather(it) }
            WeatherProvider.APIXU -> {
            }
            WeatherProvider.YAHOO -> {
            }
        }
        return weatherData
    }
}