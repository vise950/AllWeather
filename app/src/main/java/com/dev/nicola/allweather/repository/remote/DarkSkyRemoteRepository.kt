package com.dev.nicola.allweather.repository.remote

import co.eggon.eggoid.extension.error
import co.eggon.eggoid.extension.safeExec
import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.util.DARK_SKY_BASE_URL
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.result.Result
import java.util.*

class DarkSkyRemoteRepository() {

    private val realm = Init.getRealmInstance()

    fun fetchDarkSky(lat: Double, lng: Double) {
        Fuel.get("$DARK_SKY_BASE_URL$lat,$lng", listOf("units" to "us", "lang" to Locale.getDefault().language, "exclude" to "minutely,alerts,flags"))
                .responseObject<RootDarkSky> { request, _, result ->
                    request.error("request")
                    when (result) {
                        is Result.Success -> {
                            realm.safeExec {
                                result.value.id = "$lat$lng"
                                it.copyToRealmOrUpdate(result.value)
                            }.onError { it.error("darkSky realm") }
                        }
                        is Result.Failure -> {
                            result.getException().error("darkSky error")
                        }
                    }
                }
    }
}