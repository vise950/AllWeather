package com.dev.nicola.allweather.weatherProvider.Yahoo.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ForecastaYahoo : RealmObject() {

    @SerializedName("code")
    var code: String? = null
    @SerializedName("date")
    var date: String? = null
    @SerializedName("day")
    var day: String? = null
    @SerializedName("high")
    var high: String? = null
    @SerializedName("low")
    var low: String? = null
    @SerializedName("text")
    var text: String? = null
}