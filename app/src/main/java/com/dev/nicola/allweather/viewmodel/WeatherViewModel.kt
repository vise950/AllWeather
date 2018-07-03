package com.dev.nicola.allweather.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.dev.nicola.allweather.model.Weather
import com.dev.nicola.allweather.repository.WeatherRepository
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    val weatherData: MediatorLiveData<Weather> = MediatorLiveData()

    init {
        weatherData.addSource(weatherRepository.weatherData) { weatherData.value = it }
    }

    fun updateWeather() {
        weatherRepository.updateWeather()
    }
}