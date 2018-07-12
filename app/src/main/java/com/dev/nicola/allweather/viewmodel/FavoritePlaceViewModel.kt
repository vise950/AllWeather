package com.dev.nicola.allweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import javax.inject.Inject

class FavoritePlaceViewModel @Inject constructor(private val placeRepo: FavoritePlaceRepository) : ViewModel() {

    fun addPlace(favoritePlace: FavoritePlace) {
        placeRepo.addPlace(favoritePlace)
    }

    fun removePlace(placeIds: List<String>) {
        placeRepo.removePlace(placeIds)
    }

    fun getPlaces(): LiveData<List<FavoritePlace>> = placeRepo.getPlaces()

    fun getPlace(id: String): LiveData<FavoritePlace> = placeRepo.getPlace(id)
}