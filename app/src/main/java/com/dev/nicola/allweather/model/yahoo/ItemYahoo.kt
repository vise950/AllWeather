package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmList
import io.realm.RealmObject

open class ItemYahoo: RealmObject() {
    var title: String? = null
    var lat: String? = null
    var long: String? = null
    var link: String? = null
    var pubDate: String? = null
    var condition: ConditionYahoo? = null
    var forecast: RealmList<ForecastYahoo>? = RealmList()
}