package com.dev.nicola.allweather.model.darkSky

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RootDarkSky : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var timezone: String? = null
    var currently: CurrentlyDarkSky? = null
    var daily: DailyDarkSky? = null
    var hourly: HourlyDarkSky? = null
}