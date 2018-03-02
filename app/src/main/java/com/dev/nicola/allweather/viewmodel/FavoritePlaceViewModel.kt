package com.dev.nicola.allweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import javax.inject.Inject

class FavoritePlaceViewModel @Inject constructor(private val placeRepo: FavoritePlaceRepository) : ViewModel() {

    private val favoritePlace: MediatorLiveData<List<FavoritePlace>> = MediatorLiveData()

    init {
        favoritePlace.addSource(getPlaces(), { favoritePlace.value = it })
    }

    fun addPlace(favoritePlace: FavoritePlace) {
        placeRepo.addPlace(favoritePlace)
    }

    fun removePlace(placeId: String) {
        placeRepo.removePlace(placeId)
    }

    fun getPlaces(): LiveData<List<FavoritePlace>> = placeRepo.getPlaces()

}