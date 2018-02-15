package com.dev.nicola.allweather.model.yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class AtmosphereYahoo : RealmObject() {
    var humidity: String? = null
    var pressure: String? = null
    var rising: String? = null
    var visibility: String? = null
}