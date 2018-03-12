package com.dev.nicola.allweather.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.dev.nicola.allweather.model.FavoritePlace

@Dao
interface FavoritePlaceDao {

    @Insert
    fun insert(place: FavoritePlace)

    @Query("SELECT * FROM ${FavoritePlace.TABLE}")
    fun getPlaces(): LiveData<List<FavoritePlace>>

    @Query("SELECT * FROM ${FavoritePlace.TABLE} WHERE id = :placeId")
    fun getPlace(placeId: String): LiveData<FavoritePlace>

//    @Query("DELETE FROM ${FavoritePlace.TABLE} WHERE id IN (:placeIds)")
//    fun removePlace(placeIds: List<String>)

    //todo
    @Query("DELETE FROM ${FavoritePlace.TABLE} WHERE id = :placeId")
    fun removePlace(placeId: String)
}