package com.dev.nicola.allweather.repository

import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.util.RealmLiveData
import com.dev.nicola.allweather.util.favoritePlaceDao

class FavoritePlaceRepository {

    private val favoritePlaceDao = Init.getRealmInstance().favoritePlaceDao()

    fun getPlace(placeId: String): RealmLiveData<FavoritePlace> = favoritePlaceDao.getPlace(placeId)

    fun getPlaces(): RealmLiveData<FavoritePlace> = favoritePlaceDao.getPlaces()

    fun addPlace(place: FavoritePlace) {
        favoritePlaceDao.insert(place)
    }

    fun removePlace(placeIds: List<String>) {
        placeIds.forEach { favoritePlaceDao.delete(it) }
    }
}