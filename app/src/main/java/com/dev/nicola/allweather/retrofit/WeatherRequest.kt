package com.dev.nicola.allweather.retrofit

import android.content.Context
import com.dev.nicola.allweather.model.apixu.RootApixu
import com.dev.nicola.allweather.model.darkSky.RootDarkSky
import com.dev.nicola.allweather.model.yahoo.RootYahoo
import com.dev.nicola.allweather.utils.PreferencesHelper
import com.dev.nicola.allweather.utils.Utils
import com.dev.nicola.allweather.utils.WeatherProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class WeatherRequest {

    companion object {

        //todo bisogna ridurre il codice utilizzando una sola variable call con cast
        fun getWeatherData(context: Context, realm: Realm, latitude: Double?, longitude: Double?, onSuccess: (() -> Unit)?, onRealmError: ((Throwable) -> Unit)?, onError: ((Throwable) -> Unit)?) {
//            var call: Observable<Any>? = null
//            call?.cast(RootDarkSky::class.java)
//            WeatherClient(context).service.getDarkSkyData(latitude ?: 100.0, longitude ?: 100.0)
//            when (PreferencesHelper.getWeatherProvider(context)) {
//                WeatherProvider.DARK_SKY -> {
//                    call?.cast(RootDarkSky::class.java)
//                    call = WeatherClient(context).service.getDarkSkyData(latitude ?: 100.0, longitude ?: 100.0)
//                }
//                WeatherProvider.APIXU -> call = WeatherClient(context).service.getApixuData(latitude.toString() + "," + longitude.toString()) as Observable<RootApixu>
//                WeatherProvider.YAHOO -> call = WeatherClient(context).service.getYahooData("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"$name\")")as Observable<RootYahoo>
//            }

            val callDarkSky: Observable<RootDarkSky>?
            val callApixu: Observable<RootApixu>?
            var callYahoo: Observable<RootYahoo>?

            when (PreferencesHelper.getWeatherProvider(context)) {
                WeatherProvider.DARK_SKY -> {
                    callDarkSky = WeatherClient(context).service.getDarkSkyData(latitude ?: 100.0, longitude ?: 100.0)
                    callDarkSky.let {
                        it.subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ data ->
                                    realm.executeTransactionAsync({
                                        it.copyToRealmOrUpdate(data)
                                    }, {
                                        onSuccess?.invoke()
                                    }, {
                                        onRealmError?.invoke(it)
                                    })
                                }, {
                                    onError?.invoke(it)
                                })
                    }
                }

                WeatherProvider.APIXU -> {
                    callApixu = WeatherClient(context).service.getApixuData(latitude.toString() + "," + longitude.toString())
                    callApixu.let {
                        it.subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ data ->
                                    realm.executeTransactionAsync({
                                        it.copyToRealmOrUpdate(data)
                                    }, {
                                        onSuccess?.invoke()
                                    }, {
                                        onRealmError?.invoke(it)
                                    })
                                }, {
                                    onError?.invoke(it)
                                })
                    }
                }

                WeatherProvider.YAHOO -> {
                    Utils.LocationHelper.getLocationName(latitude ?: 0.0, longitude ?: 0.0, {
                        callYahoo = WeatherClient(context).service.getYahooData("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"$it\")")
                        callYahoo?.let {
                            it.subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({ data ->
                                        realm.executeTransactionAsync({
                                            it.copyToRealmOrUpdate(data)
                                        }, {
                                            onSuccess?.invoke()
                                        }, {
                                            onRealmError?.invoke(it)
                                        })
                                    }, {
                                        onError?.invoke(it)
                                    })
                        }
                    })
                }
            }
        }
    }
}
