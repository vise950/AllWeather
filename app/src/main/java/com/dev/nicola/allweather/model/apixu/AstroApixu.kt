package com.dev.nicola.allweather.model.apixu

import io.realm.RealmObject

open class AstroApixu : RealmObject() {
    var sunrise: String? = null
    var sunset: String? = null
}