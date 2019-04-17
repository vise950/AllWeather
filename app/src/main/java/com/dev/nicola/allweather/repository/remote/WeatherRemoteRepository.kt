package com.dev.nicola.allweather.repository.remote

import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.network.WeatherService
import com.dev.nicola.allweather.util.DARK_SKY_BASE_URL
import com.ewt.nicola.common.extension.log
import com.ewt.nicola.network.get
import com.ewt.nicola.realm.extension.safeExec
import java.util.*

class DarkSkyRemoteRepository {

    private val realm = Init.getRealmInstance()
//    private val weatherService = Init.getRetrofit().create(WeatherService::class.java)

//    fun fetchDarkSky(lat: Double, lng: Double) {
//        Fuel.get("$DARK_SKY_BASE_URL$lat,$lng",
//                listOf("units" to "us", "lang" to Locale.getDefault().language, "exclude" to "minutely,alerts,flags"))
//                .responseObject<RootDarkSky> { request, _, result ->
//                    request.log("request")
//                    when (result) {
//                        is Result.Success -> {
//                            realm.safeExec {
//                                result.value.id = "$lat$lng"
//                                it.copyToRealmOrUpdate(result.value)
//                            }.error { it.log("darkSky realm") }
//                        }
//                        is Result.Failure -> {
//                            result.getException().log("darkSky error")
//                        }
//                    }
//                }
//    }

    fun fetchDarkSky1(lat: Double, lng: Double) {
        Init.getRetrofit(DARK_SKY_BASE_URL)
                .create(WeatherService::class.java)
                .getDarkSkyData(lat, lng)
                .get()
                .then { it.log("response") }
                .error { it.log("error") }
    }

}