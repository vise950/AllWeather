package com.dev.nicola.allweather.model.yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class AstronomyYahoo : RealmObject() {
    var sunrise: String? = null
    var sunset: String? = null
}