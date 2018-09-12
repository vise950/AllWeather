package com.dev.nicola.allweather.model.apixu

import io.realm.RealmObject

open class DayApixu : RealmObject() {
    var maxtemp_c: Double? = null
    var mintemp_c: Double? = null
    var avgtemp_c: Double? = null
    var totalprecip_mm: Double? = null
    var condition: ForecastConditionApixu? = null
}