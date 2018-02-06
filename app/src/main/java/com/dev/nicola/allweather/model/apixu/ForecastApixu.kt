package com.dev.nicola.allweather.model.apixu

import io.realm.RealmList
import io.realm.RealmObject

open class ForecastApixu : RealmObject() {
    var forecastday: RealmList<ForecastDayApixu>? = RealmList()
}