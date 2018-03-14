package com.dev.nicola.allweather.di.module

import com.dev.nicola.allweather.db.dao.FavoritePlaceDao
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun providePlaceDao(dao: FavoritePlaceDao): FavoritePlaceRepository = FavoritePlaceRepository(dao)
}