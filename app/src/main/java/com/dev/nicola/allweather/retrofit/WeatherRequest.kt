package com.dev.nicola.allweather.retrofit

import android.content.Context
import com.dev.nicola.allweather.model.Apixu.RootApixu
import com.dev.nicola.allweather.model.DarkSky.RootDarkSky
import com.dev.nicola.allweather.model.Yahoo.RootYahoo
import io.reactivex.Observable
import io.realm.Realm

class WeatherRequest {

    companion object {

        //todo bisogna ridurre il codice utilizzando una sola variable call con cast
        fun getWeatherData(context: Context, realm: Realm, latitude: Double?, longitude: Double?, onSuccess: (() -> Unit)?, onRealmError: (() -> Unit)?, onError: (() -> Unit)?) {
//            var call: Observable<Any>? = null
//            when (PreferencesHelper.getWeatherProvider(context)) {
//                WeatherProvider.DARK_SKY -> call = WeatherClient(context).service.getDarkSkyData(latitude!!, longitude!!) as Observable<RootDarkSky>
//                WeatherProvider.APIXU -> call = WeatherClient(context).service.getApixuData(latitude.toString() + "," + longitude.toString()) as Observable<RootApixu>
//                WeatherProvider.YAHOO -> call = WeatherClient(context).service.getYahooData("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"$name\")")as Observable<RootYahoo>
//            }

            val callDarkSky: Observable<RootDarkSky>?
            val callApixu: Observable<RootApixu>?
            var callYahoo: Observable<RootYahoo>?

//            when (PreferencesHelper.getWeatherProvider(context)) {
//                WeatherProvider.DARK_SKY -> {
//                    callDarkSky = WeatherClient(context).service.getDarkSkyData(latitude ?: 0.0, longitude ?: 0.0)
//                    callDarkSky.subscribeOn(Schedulers.newThread())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe({ data ->
//                                realm.executeTransactionAsync({
//                                    it.copyToRealmOrUpdate(data)
//                                }, {
//                                    onSuccess?.invoke()
//                                }, {
//                                    onRealmError?.invoke()
//                                })
//                            }, {
//                                onError?.invoke()
//                            })
//                }
//
//                WeatherProvider.APIXU -> {
//                    callApixu = WeatherClient(context).service.getApixuData(latitude.toString() + "," + longitude.toString())
//                    callApixu.subscribeOn(Schedulers.newThread())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe({ data ->
//                                realm.executeTransactionAsync({
//                                    it.copyToRealmOrUpdate(data)
//                                }, {
//                                    onSuccess?.invoke()
//                                }, {
//                                    onRealmError?.invoke()
//                                })
//                            }, {
//                                onError?.invoke()
//                            })
//                }
//
//                WeatherProvider.YAHOO -> {
//                    Utils.LocationHelper.getLocationName(latitude ?: 0.0, longitude ?: 0.0, {
//                        callYahoo = WeatherClient(context).service.getYahooData("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"$it\")")
//                        callYahoo!!.subscribeOn(Schedulers.newThread())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe({ data ->
//                                    realm.executeTransactionAsync({
//                                        it.copyToRealmOrUpdate(data)
//                                    }, {
//                                        onSuccess?.invoke()
//                                    }, {
//                                        onRealmError?.invoke()
//                                    })
//                                }, {
//                                    onError?.invoke()
//                                })
//
//                    })
//                }
//            }
        }
    }
}
