package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class LocationYahoo: RealmObject() {
    var city: String? = null
    var country: String? = null
    var region: String? = null
}