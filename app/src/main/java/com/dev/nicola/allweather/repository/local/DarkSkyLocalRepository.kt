package com.dev.nicola.allweather.repository.local

import android.arch.lifecycle.LiveData
import com.dev.nicola.allweather.db.dao.DarkSkyDao
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import javax.inject.Inject

class DarkSkyLocalRepository @Inject constructor(/*@DarkSky*/ val dao: DarkSkyDao) {

    fun getData(lat: Double, lng: Double): LiveData<RootDarkSky> = dao.getData(lat,lng)
}