package com.dev.nicola.allweather.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.dev.nicola.allweather.db.dao.DarkSkyDao
import com.dev.nicola.allweather.db.dao.FavoritePlaceDao
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.model.darkSky.DailyDataDarkSky
import com.dev.nicola.allweather.model.darkSky.HourlyDataDarkSky
import com.dev.nicola.allweather.model.darkSky.RootDarkSky

@Database(entities = [RootDarkSky::class, DailyDataDarkSky::class, HourlyDataDarkSky::class], version = 1, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {
//    abstract fun favoritePlaceDao(): FavoritePlaceDao
    abstract fun darkSkyDao(): DarkSkyDao
}