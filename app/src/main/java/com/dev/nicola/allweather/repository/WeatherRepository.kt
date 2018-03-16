package com.dev.nicola.allweather.repository

import android.content.Context
import co.eggon.eggoid.extension.isConnectionAvailable
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.repository.local.DarkSkyLocalRepository
import com.dev.nicola.allweather.repository.remote.DarkSkyRemoteRepository
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherRepository {

    private val context: Context

//    private val darkSkyRetrofit: Retrofit
    private val darkSkyLocalRepository: DarkSkyLocalRepository
    private val darkSkyRemoteRepository: DarkSkyRemoteRepository

    @Inject constructor(context: Context, /*darkSkyRetrofit: Retrofit,*/ darkSkyLocalRepository: DarkSkyLocalRepository, darkSkyRemoteRepository: DarkSkyRemoteRepository) {
        this.context = context
//        this.darkSkyRetrofit = darkSkyRetrofit
        this.darkSkyLocalRepository = darkSkyLocalRepository
        this.darkSkyRemoteRepository = darkSkyRemoteRepository
    }

    private fun getDarkSkyWeather(disposable: CompositeDisposable, lat: Double, lng: Double)/*: LiveData<Weather> */ {
        if (context.isConnectionAvailable()) {
            darkSkyRemoteRepository.getDarkSkyData(disposable, lat, lng)
        }
    }
}