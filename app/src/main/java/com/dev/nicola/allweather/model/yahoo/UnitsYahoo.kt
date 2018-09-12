package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class UnitsYahoo: RealmObject() {
    var distance: String? = null
    var pressure: String? = null
    var speed: String? = null
    var temperature: String? = null
}