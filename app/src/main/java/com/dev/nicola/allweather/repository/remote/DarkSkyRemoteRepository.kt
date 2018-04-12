package com.dev.nicola.allweather.repository.remote

import co.eggon.eggoid.extension.error
import co.eggon.eggoid.extension.network
import com.dev.nicola.allweather.db.dao.DarkSkyDao
import com.dev.nicola.allweather.di.DarkSky
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.network.WeatherService
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.experimental.async
import retrofit2.Retrofit
import javax.inject.Inject

class DarkSkyRemoteRepository @Inject constructor(@DarkSky val retrofit: Retrofit, @DarkSky val dao: DarkSkyDao) {

    fun getDarkSkyData(disposable: CompositeDisposable, lat: Double, lng: Double) {
        retrofit.create(WeatherService::class.java)
                .getDarkSkyData(lat, lng)
                .network(disposable, {
                    saveData(it)
                }, {
                    it.error("DARKSKY")
                })
    }

    private fun saveData(data: RootDarkSky) {
        async {
            data.updateKeys()
            dao.insertData(data)
            dao.insertDailyData(*data.daily.data.toTypedArray())
            dao.insertHourlyData(*data.hourly.data.toTypedArray())
        }
    }
}
