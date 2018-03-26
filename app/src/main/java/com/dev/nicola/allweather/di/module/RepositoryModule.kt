package com.dev.nicola.allweather.di.module

import com.dev.nicola.allweather.db.dao.DarkSkyDao
import com.dev.nicola.allweather.db.dao.FavoritePlaceDao
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RepositoryModule {

    @Provides
    fun providePlaceRepository(dao: FavoritePlaceDao): FavoritePlaceRepository = FavoritePlaceRepository(dao)

    @Provides
    fun provideDarkSkyLocalRepository(darkSkyDao: DarkSkyDao): DarkSkyLocalRepository = DarkSkyLocalRepository(darkSkyDao)

    @Provides
    fun provideDarkSkyRemoteRepository(@DarkSky retrofit: Retrofit, darkSkyDao: DarkSkyDao): DarkSkyRemoteRepository = DarkSkyRemoteRepository(retrofit, darkSkyDao)
}