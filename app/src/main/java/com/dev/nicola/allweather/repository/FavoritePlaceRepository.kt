package com.dev.nicola.allweather.repository

import com.dev.nicola.allweather.dao.FavoritePlaceDao
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.util.RealmLiveData
import javax.inject.Inject

class FavoritePlaceRepository @Inject constructor(private val dao: FavoritePlaceDao) {

    fun getPlace(placeId: String): RealmLiveData<FavoritePlace> = dao.getPlace(placeId)

    fun getPlaces(): RealmLiveData<FavoritePlace> = dao.getPlaces()

    fun addPlace(place: FavoritePlace) {
        dao.insert(place)
    }

    fun removePlace(placeIds: List<String>) {
        placeIds.forEach { dao.delete(it) }
    }
}