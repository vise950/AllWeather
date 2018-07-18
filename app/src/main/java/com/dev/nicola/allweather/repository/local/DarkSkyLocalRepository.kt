package com.dev.nicola.allweather.repository.local

import com.dev.nicola.allweather.dao.DarkSkyDao
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.util.RealmLiveData
import javax.inject.Inject

class DarkSkyLocalRepository @Inject constructor(@DarkSky val dao: DarkSkyDao) {

    fun getData(lat: Double, lng: Double): RealmLiveData<RootDarkSky> = dao.getData(lat, lng)
}