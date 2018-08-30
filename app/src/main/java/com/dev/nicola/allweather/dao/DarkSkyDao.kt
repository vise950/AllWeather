package com.dev.nicola.allweather.dao

import co.eggon.eggoid.extension.safeExec
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.util.RealmLiveData
import com.dev.nicola.allweather.util.asLiveData
import io.realm.Realm
import javax.inject.Inject

class DarkSkyDao @Inject constructor(private val realm: Realm) {

    fun insert(data: RootDarkSky) {
        realm.safeExec {
            it.copyToRealmOrUpdate(data)
        }
    }

    fun getData(lat: Double, lng: Double): RealmLiveData<RootDarkSky> =
            realm.where(RootDarkSky::class.java).equalTo(DBConstant.ID, "$lat$lng").findAllAsync().asLiveData()
}