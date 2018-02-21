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

    @Query("select * from favorite_place")
    fun getPlaces(): LiveData<List<FavoritePlace>>

    @Query("select * from favorite_place where id = :placeId")
    fun getPlace(placeId: String): LiveData<FavoritePlace>

    @Query("delete from favorite_place where id = :placeId")
    fun removePlace(placeId: String)
}