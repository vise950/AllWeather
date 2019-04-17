package com.dev.nicola.allweather.repository.remote

import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.network.WeatherService
import com.dev.nicola.allweather.util.DARK_SKY_BASE_URL
import com.ewt.nicola.common.extension.log
import com.ewt.nicola.network.get

class WeatherRemoteRepository {

    private val realm = Init.getRealmInstance()

    fun fetchDarkSky(lat: Double, lng: Double) {
        Init.getRetrofit(DARK_SKY_BASE_URL)
                .create(WeatherService::class.java)
                .getDarkSkyData(lat, lng)
                .get()
                .then { it.log("response") }
                .error { it.log("error") }
    }

}