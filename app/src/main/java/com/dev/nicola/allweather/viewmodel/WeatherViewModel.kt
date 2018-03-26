package com.dev.nicola.allweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import co.eggon.eggoid.extension.error
import com.dev.nicola.allweather.model.Weather
import com.dev.nicola.allweather.repository.WeatherRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository,
                                           private val disposable: CompositeDisposable) : ViewModel() {

    private val weatherData: MediatorLiveData<Weather> = MediatorLiveData()

    init {
        weatherData.addSource(updateWeather(),
                {
                    it.error("data")
                    weatherData.value = it
                })
    }

    fun updateWeather(coordinates: Pair<Double, Double>? = null): LiveData<Weather> {
//        return coordinates?.let {
//            weatherRepository.getWeather(disposable, it.first, it.second).also {
//                Transformations.switchMap(it, { data ->
//                    weatherRepository.getDailyData(data).also {
//                        Transformations.map(it, {
//                            data.daily.data = it
//                            data
//                        })
//                    }
//                })
//            }
//        } ?: run { MediatorLiveData<Weather>() }


        var weatherLiveData = weatherRepository.getWeather(disposable,
                coordinates?.first ?: 0.0,
                coordinates?.second ?: 0.0)
//        weatherLiveData = Transformations.switchMap(weatherLiveData, { weather ->
//            weather?.let {
//                val dailyData = weatherRepository.getDailyData(it)
//                Transformations.map(dailyData, {
//                    weather.daily.data = it
//                    weather
//                })
//            }
//        })
        return weatherLiveData
    }

    fun getWeather(): LiveData<Weather> = weatherData
}
