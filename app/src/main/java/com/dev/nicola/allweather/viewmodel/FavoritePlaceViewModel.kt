package com.dev.nicola.allweather.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import javax.inject.Inject

class FavoritePlaceViewModel @Inject constructor(private val placeRepo: FavoritePlaceRepository, placeId: String? = null) : ViewModel() {

    val places: MediatorLiveData<List<FavoritePlace>> = MediatorLiveData()
    val place: MediatorLiveData<FavoritePlace> = MediatorLiveData()

    init {
        placeId?.let {
            place.addSource(placeRepo.getPlace(it), { place.value = it })
        } ?: run {
            places.addSource(placeRepo.getPlaces(), { places.value = it })
        }
    }

    fun addPlace(favoritePlace: FavoritePlace) {
        placeRepo.addPlace(favoritePlace)
    }

    fun removePlace(placeIds: List<String>) {
        placeRepo.removePlace(placeIds)
    }
}