package com.dev.nicola.allweather.model.yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class UnitsYahoo : RealmObject() {
    var distance: String? = null
    var pressure: String? = null
    var speed: String? = null
    var temperature: String? = null
}