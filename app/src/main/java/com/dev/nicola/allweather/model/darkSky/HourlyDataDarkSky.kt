package com.dev.nicola.allweather.model.darkSky

import io.realm.RealmObject

open class HourlyDataDarkSky : RealmObject() {
    var latitude: Double? = null
    var longitude: Double? = null
    var time: Long? = null
    var summary: String? = null
    var icon: String? = null
    var precipIntensity: Double? = null
    var temperature: Double? = null
    var dewPoint: Double? = null
    var humidity: Double? = null
    var windSpeed: Double? = null
    var windBearing: Int? = null
    var visibility: Double? = null
    var cloudCover: Double? = null
    var pressure: Double? = null
}