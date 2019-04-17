package com.dev.nicola.allweather.repository.local

import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.util.RealmLiveData
import com.dev.nicola.allweather.util.darkSkyDao

class WeatherLocalRepository {

    private val darkSkyDao = Init.getRealmInstance().darkSkyDao()

    fun getData(lat: Double, lng: Double): RealmLiveData<RootDarkSky> = darkSkyDao.getData(lat, lng)
}