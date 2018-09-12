package com.dev.nicola.allweather.model.apixu

import io.realm.RealmObject

open class CurrentApixu : RealmObject() {
    var temp_c: Double? = null
    var is_day: Int? = null
    var condition: CurrentConditionApixu? = null
    var wind_mph: Double? = null
    var wind_degree: Int? = null
    var wind_dir: String? = null
    var pressure_mb: Double? = null
    var precip_mm: Double? = null
    var humidity: Int? = null
    var cloud: Int? = null
}