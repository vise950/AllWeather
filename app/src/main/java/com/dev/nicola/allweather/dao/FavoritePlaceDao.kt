package com.dev.nicola.allweather.dao

import co.eggon.eggoid.extension.safeExec
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.util.RealmLiveData
import com.dev.nicola.allweather.util.asLiveData
import io.realm.Realm
import javax.inject.Inject


class FavoritePlaceDao @Inject constructor(private val realm: Realm) {

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