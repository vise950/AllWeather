package com.dev.nicola.allweather.viewmodel

import androidx.lifecycle.ViewModel
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import com.dev.nicola.allweather.util.RealmLiveData
import javax.inject.Inject

class FavoritePlaceViewModel @Inject constructor(private val placeRepo: FavoritePlaceRepository) : ViewModel() {

    fun addPlace(favoritePlace: FavoritePlace) {
        placeRepo.addPlace(favoritePlace)
    }

    fun removePlace(placeIds: List<String>) {
        placeRepo.removePlace(placeIds)
    }

    fun getPlaces(): RealmLiveData<FavoritePlace> = placeRepo.getPlaces()

    fun getPlace(id: String): RealmLiveData<FavoritePlace> = placeRepo.getPlace(id)
}