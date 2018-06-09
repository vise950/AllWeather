package com.dev.nicola.allweather.di.module

import android.content.Context
import com.dev.nicola.allweather.db.dao.DarkSkyDao
import com.dev.nicola.allweather.db.dao.FavoritePlaceDao
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import com.dev.nicola.allweather.repository.core.DarkSkyRepository
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RepositoryModule {

    @Provides
    fun providePlaceRepository(dao: FavoritePlaceDao): FavoritePlaceRepository = FavoritePlaceRepository(dao)


    /* DARKSKY */
    @Provides
    @DarkSky
    fun provideDarkSkyLocalRepository(darkSkyDao: DarkSkyDao): DarkSkyLocalRepository = DarkSkyLocalRepository(darkSkyDao)

    @Provides
    @DarkSky
    fun provideDarkSkyRemoteRepository(retrofit: Retrofit, darkSkyDao: DarkSkyDao): DarkSkyRemoteRepository = DarkSkyRemoteRepository(retrofit, darkSkyDao)

    @Provides
    @DarkSky
    fun provideDarkSkyRepository(context: Context, localRepository: DarkSkyLocalRepository, remoteRepository: DarkSkyRemoteRepository) =
            DarkSkyRepository(context, localRepository, remoteRepository)
}