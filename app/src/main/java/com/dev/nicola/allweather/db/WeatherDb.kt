package com.dev.nicola.allweather.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.dev.nicola.allweather.db.dao.FavoritePlaceDao
import com.dev.nicola.allweather.model.FavoritePlace

@Database(entities = [(FavoritePlace::class)], version = 1)
abstract class WeatherDb : RoomDatabase() {
//    companion object {
//        private var database: WeatherDb? = null
//
//        fun getDatabase(context: Context): WeatherDb =
//                database ?: run {
//                    createDatabase(context).also { database = it }
//                }
//
//        private fun createDatabase(context: Context): WeatherDb =
//                Room.databaseBuilder(context, WeatherDb::class.java, "weather.db")
//                        .build()
//    }

//    abstract fun darkSkyDao(): DarkSkyDao

    abstract fun favoritePlaceDao(): FavoritePlaceDao
}