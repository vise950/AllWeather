package com.dev.nicola.allweather.model.apixu

import io.realm.RealmList
import io.realm.RealmObject

open class ForecastDayApixu : RealmObject() {
    var date: String? = null
    var day: DayApixu? = null
    var hour: RealmList<HourApixu>? = RealmList()
}