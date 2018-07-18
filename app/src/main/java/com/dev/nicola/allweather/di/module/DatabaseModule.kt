package com.dev.nicola.allweather.di.module

import com.dev.nicola.allweather.dao.DarkSkyDao
import com.dev.nicola.allweather.dao.FavoritePlaceDao
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.util.darkSkyDao
import com.dev.nicola.allweather.util.favoritePlaceDao
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    fun providePlaceDao(realm: Realm): FavoritePlaceDao = realm.favoritePlaceDao()

    @Provides
    @Singleton
    @DarkSky
    fun provideDarkSkyDao(realm: Realm): DarkSkyDao = realm.darkSkyDao()
}