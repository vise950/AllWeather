package com.dev.nicola.allweather.repository.local

import com.dev.nicola.allweather.db.dao.DarkSkyDao
import com.dev.nicola.allweather.di.DarkSky
import javax.inject.Inject

class DarkSkyLocalRepository @Inject constructor(/*@DarkSky*/ val dao: DarkSkyDao) {

}