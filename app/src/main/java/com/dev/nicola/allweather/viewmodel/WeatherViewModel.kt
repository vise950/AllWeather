package com.dev.nicola.allweather.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.dev.nicola.allweather.model.Weather
import com.dev.nicola.allweather.repository.WeatherRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository,
                                           private val disposable: CompositeDisposable) : ViewModel() {

    val weatherData: MediatorLiveData<Weather> = MediatorLiveData()

    init {
        weatherData.addSource(weatherRepository.weatherData,
                {
                    weatherData.value = it
                })
    }

    fun updateWeather(coordinates: Pair<Double, Double>? = null) {
        weatherRepository.updateWeather(disposable,
                coordinates?.first ?: 0.0,
                coordinates?.second ?: 0.0)
    }
}
