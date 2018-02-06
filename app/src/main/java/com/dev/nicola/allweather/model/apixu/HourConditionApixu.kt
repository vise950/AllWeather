package com.dev.nicola.allweather.model.apixu

import io.realm.RealmObject

open class HourConditionApixu : RealmObject() {
    var text: String? = null
    var icon: String? = null
    var code: Int? = null
}