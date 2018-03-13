package com.dev.nicola.allweather.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import javax.inject.Inject

class FavoritePlaceViewModel @Inject constructor(private val placeRepo: FavoritePlaceRepository, placeId: String? = null) : ViewModel() {

    private val favoritePlaces: MediatorLiveData<List<FavoritePlace>> = MediatorLiveData()
    private val favoritePlace: MediatorLiveData<FavoritePlace> = MediatorLiveData()

    init {
        placeId?.let {
            favoritePlace.addSource(getPlace(it), { favoritePlace.value = it })
        } ?: run {
            favoritePlaces.addSource(getPlaces(), { favoritePlaces.value = it })
        }
    }

    fun addPlace(favoritePlace: FavoritePlace) {
        placeRepo.addPlace(favoritePlace)
    }

    fun removePlace(placeIds: List<String>) {
        placeRepo.removePlace(placeIds)
    }

    fun getPlaces(): LiveData<List<FavoritePlace>> = placeRepo.getPlaces()

    fun getPlace(placeId: String): LiveData<FavoritePlace> = placeRepo.getPlace(placeId)
}