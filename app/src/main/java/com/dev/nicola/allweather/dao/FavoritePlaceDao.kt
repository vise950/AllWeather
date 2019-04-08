package com.dev.nicola.allweather.dao

import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.util.RealmLiveData
import com.dev.nicola.allweather.util.asLiveData
import com.ewt.nicola.realm.extension.safeExec
import io.realm.Realm


class FavoritePlaceDao (private val realm: Realm) {

    fun insert(place: FavoritePlace) {
        realm.safeExec {
            it.copyToRealmOrUpdate(place)
        }
    }

    fun delete(placeId: String) {
        realm.safeExec {
            it.where(FavoritePlace::class.java).equalTo("id", placeId).findFirst()?.deleteFromRealm()
        }
    }

    fun getPlaces(): RealmLiveData<FavoritePlace> = realm.where(FavoritePlace::class.java).findAllAsync().asLiveData()

    fun getPlace(placeId: String): RealmLiveData<FavoritePlace> = realm.where(FavoritePlace::class.java).equalTo("id", placeId).findAllAsync().asLiveData()
}