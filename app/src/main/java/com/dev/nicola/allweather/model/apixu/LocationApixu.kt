package com.dev.nicola.allweather.model.apixu

import io.realm.RealmObject

open class LocationApixu : RealmObject() {
    var name: String? = null
    var region: String? = null
    var country: String? = null
    var lat: Double? = null
    var lon: Double? = null
    var tz_id: String? = null
    var localtime_epoch: Int? = null
    var localtime: String? = null
}