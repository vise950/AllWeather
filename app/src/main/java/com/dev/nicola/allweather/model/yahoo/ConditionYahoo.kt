package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class ConditionYahoo: RealmObject() {
    var code: String? = null
    var date: String? = null
    var temp: String? = null
    var text: String? = null
}