package com.dev.nicola.allweather.repository.local

import android.arch.lifecycle.LiveData
import com.dev.nicola.allweather.db.dao.DarkSkyDao
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.model.darkSky.DailyDataDarkSky
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import javax.inject.Inject

class DarkSkyLocalRepository @Inject constructor(@DarkSky val dao: DarkSkyDao) {

    fun getData(lat: Double, lng: Double): LiveData<RootDarkSky> = dao.getData(lat, lng)
    fun getDailyData(data: RootDarkSky): LiveData<List<DailyDataDarkSky>> = dao.getDailyData(data.latitude, data.longitude)
}