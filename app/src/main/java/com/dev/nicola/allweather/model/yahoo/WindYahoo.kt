package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class WindYahoo : RealmObject() {
    var chill: String? = null
    var direction: String? = null
    var speed: String? = null
}