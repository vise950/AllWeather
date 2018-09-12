package com.dev.nicola.allweather.model.apixu

import io.realm.RealmObject

open class HourApixu : RealmObject(){
    var time_epoch: Long? = null
    var time: String? = null
    var temp_c: Double? = null
    var is_day: Int? = null
    var condition: HourConditionApixu? = null
}