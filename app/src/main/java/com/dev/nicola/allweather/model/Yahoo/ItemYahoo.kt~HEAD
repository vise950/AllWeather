package com.dev.nicola.allweather.model.Yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ItemYahoo : RealmObject() {

    @SerializedName("title")
    var title: String? = null
    @SerializedName("lat")
    var lat: String? = null
    @SerializedName("long")
    var long: String? = null
    @SerializedName("link")
    var link: String? = null
    @SerializedName("pubDate")
    var pubDate: String? = null
    @SerializedName("condition")
    var condition: ConditionYahoo? = null
    @SerializedName("forecast")
    var forecast: RealmList<ForecastYahoo>? = null
}