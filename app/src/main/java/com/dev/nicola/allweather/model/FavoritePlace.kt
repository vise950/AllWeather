package com.dev.nicola.allweather.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorite_place")
data class FavoritePlace(
        @PrimaryKey
        val id: String,
        val name: String,
        val latitude: Long,
        val longitude: Long)