package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class ForecastYahoo : RealmObject(){
    var code: String? = null
    var date: String? = null
    var day: String? = null
    var high: String? = null
    var low: String? = null
    var text: String? = null
}