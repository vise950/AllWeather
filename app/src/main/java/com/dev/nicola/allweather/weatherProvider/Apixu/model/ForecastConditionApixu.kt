package com.dev.nicola.allweather.weatherProvider.Apixu.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ForecastConditionApixu : RealmObject() {

    @SerializedName("text")
    var text: String? = null
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("code")
    var code: Int? = null
}