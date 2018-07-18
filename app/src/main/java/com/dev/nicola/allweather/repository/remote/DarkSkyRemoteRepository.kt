package com.dev.nicola.allweather.repository.remote

import co.eggon.eggoid.extension.error
import co.eggon.eggoid.extension.objectToRealm
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.network.WeatherService
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import retrofit2.Retrofit
import javax.inject.Inject

class DarkSkyRemoteRepository @Inject constructor(@DarkSky val retrofit: Retrofit, val disposable: CompositeDisposable, val realm: Realm) {

    fun getDarkSkyData(lat: Double, lng: Double) {
        retrofit.create(WeatherService::class.java)
                .getDarkSkyData(lat, lng)
                .objectToRealm(realm, disposable, true) {
                    it.id = "${it.latitude}${it.longitude}"
                }
                .onError { it.error("getDarkSkyData") }
    }
}