package com.dev.nicola.allweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.dev.nicola.allweather.model.Weather
import com.dev.nicola.allweather.repository.WeatherRepository
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    fun updateWeather(lat: Double, lng: Double) {
        weatherRepository.updateWeather(lat, lng)
    }

    fun getWeather(lat: Double, lng: Double): LiveData<Weather> = weatherRepository.getWeather(lat, lng)
}