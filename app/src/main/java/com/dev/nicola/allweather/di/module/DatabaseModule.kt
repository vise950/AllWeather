package com.dev.nicola.allweather.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.dev.nicola.allweather.db.DBConstant.DATABASE_NAME
import com.dev.nicola.allweather.db.WeatherDb
import com.dev.nicola.allweather.db.dao.DarkSkyDao
import com.dev.nicola.allweather.db.dao.FavoritePlaceDao
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.util.favoritePlaceDao
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): WeatherDb =
            Room.databaseBuilder(context, WeatherDb::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration() //todo only debug implementing migration
                    .build()

//    @Provides
//    @Singleton
//    fun providePlaceDao(db: WeatherDb): FavoritePlaceDao = db.favoritePlaceDao()

    @Provides
    @Singleton
    @DarkSky
    fun provideDarkSkyDao(db: WeatherDb): DarkSkyDao = db.darkSkyDao()

    @Provides
    fun provideRealm(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    fun providePlaceDao(realm: Realm): FavoritePlaceDao = realm.favoritePlaceDao()
}