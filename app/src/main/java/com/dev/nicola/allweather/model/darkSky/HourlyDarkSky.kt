package com.dev.nicola.allweather.model.darkSky

import io.realm.RealmList
import io.realm.RealmObject

open class HourlyDarkSky : RealmObject() {
    var summary: String? = null
    var icon: String? = null
    var data: RealmList<HourlyDataDarkSky> = RealmList()
}