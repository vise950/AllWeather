package com.dev.nicola.allweather.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.dev.nicola.allweather.model.FavoritePlace.Companion.TABLE


@Entity(tableName = TABLE)
data class FavoritePlace(
        @PrimaryKey
        val id: String,
        val name: String,
        val latitude: Double,
        val longitude: Double
) {
    companion object {
        const val TABLE = "favorite_place"
    }
}