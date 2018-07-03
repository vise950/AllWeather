package com.dev.nicola.allweather.repository

import android.arch.lifecycle.MediatorLiveData
import android.content.Context
import com.dev.nicola.allweather.application.Injector
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

    private val prefs by lazy { PreferencesHelper(context) }
    var weatherData: MediatorLiveData<Weather> = MediatorLiveData()

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
            WeatherProvider.DARK_SKY -> {
                darkSkyRepository = DarkSkyRepository(context, darkSkyLocalRepository, darkSkyRemoteRepository, disposable)
                weatherData.addSource(darkSkyRepository.darkSkyData) { weatherData.value = Weather(it) }
            }
            WeatherProvider.YAHOO -> {
            }
            WeatherProvider.APIXU -> {
            }
        }
    }

    fun updateWeather() {
        when (prefs.weatherProvider) {
            WeatherProvider.DARK_SKY -> darkSkyRepository.updateDarkSkyWeather()
            WeatherProvider.APIXU -> {
            }
            WeatherProvider.YAHOO -> {
            }
        }
    }
}