package com.dev.nicola.allweather.dao

import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.util.RealmLiveData
import com.dev.nicola.allweather.util.asLiveData
import com.ewt.nicola.realm.extension.safeExec
import io.realm.Realm

class DarkSkyDao(private val realm: Realm) {

    fun insert(data: RootDarkSky) {
        realm.safeExec {
            it.copyToRealmOrUpdate(data)
        }
    }

    fun getData(lat: Double, lng: Double): RealmLiveData<RootDarkSky> =
            realm.where(RootDarkSky::class.java).equalTo(DBConstant.ID, "$lat$lng").findAllAsync().asLiveData()
}