package com.dev.nicola.allweather.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.dev.nicola.allweather.db.dao.DarkSkyDao
import com.dev.nicola.allweather.db.dao.FavoritePlaceDao
import com.dev.nicola.allweather.model.FavoritePlace

@Database(entities = [(FavoritePlace::class)], version = 1)
abstract class WeatherDb : RoomDatabase() {
    abstract fun favoritePlaceDao(): FavoritePlaceDao
    abstract fun darkSkyDao(): DarkSkyDao
}