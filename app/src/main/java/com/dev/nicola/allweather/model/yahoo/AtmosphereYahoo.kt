package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class AtmosphereYahoo : RealmObject(){
    var humidity: String? = null
    var pressure: String? = null
    var rising: String? = null
    var visibility: String? = null
}