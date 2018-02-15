package com.dev.nicola.allweather.model.yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class ItemYahoo : RealmObject() {
    var title: String? = null
    var lat: String? = null
    var long: String? = null
    var link: String? = null
    var pubDate: String? = null
    var condition: ConditionYahoo? = null
    var forecast: RealmList<ForecastYahoo>? = null
}