package com.dev.nicola.allweather.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import javax.inject.Inject

class FavoritePlaceViewModel @Inject constructor(placeRepo: FavoritePlaceRepository) : ViewModel() {

    private val place: MediatorLiveData<FavoritePlace>? = MediatorLiveData()

    init {

    }

}