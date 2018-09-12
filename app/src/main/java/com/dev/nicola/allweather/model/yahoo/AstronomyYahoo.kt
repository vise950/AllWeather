package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class AstronomyYahoo:RealmObject() {
    var sunrise: String? = null
    var sunset: String? = null
}