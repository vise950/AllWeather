package com.dev.nicola.allweather.repository

import android.arch.lifecycle.LiveData
import com.dev.nicola.allweather.db.dao.FavoritePlaceDao
import com.dev.nicola.allweather.model.FavoritePlace
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class FavoritePlaceRepository @Inject constructor(private val dao: FavoritePlaceDao) {

    fun getPlace(placeId: String): LiveData<FavoritePlace> = dao.getPlace(placeId)

    fun getPlaces(): LiveData<List<FavoritePlace>> = dao.getPlaces()

    fun addPlace(place: FavoritePlace) {
        async {
            dao.insert(place)
        }
    }

    //todo
    fun removePlace(placeIds: List<String>) {
        placeIds.forEach {
            async {
                dao.removePlace(it)
            }
        }
    }
}