package com.dev.nicola.allweather.weatherProvider.Yahoo.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class AtmosphereYahoo : RealmObject() {

    @SerializedName("humidity")
    var humidity: String? = null
    @SerializedName("pressure")
    var pressure: String? = null
    @SerializedName("rising")
    var rising: String? = null
    @SerializedName("visibility")
    var visibility: String? = null
}