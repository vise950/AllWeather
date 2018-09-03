package com.dev.nicola.allweather.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import com.dev.nicola.allweather.util.RealmLiveData

class FavoritePlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val placeRepo: FavoritePlaceRepository = FavoritePlaceRepository()

    fun addPlace(favoritePlace: FavoritePlace) {
        placeRepo.addPlace(favoritePlace)
    }

    fun removePlace(placeIds: List<String>) {
        placeRepo.removePlace(placeIds)
    }

    fun getPlaces(): RealmLiveData<FavoritePlace> = placeRepo.getPlaces()

    fun getPlace(id: String): RealmLiveData<FavoritePlace> = placeRepo.getPlace(id)
}