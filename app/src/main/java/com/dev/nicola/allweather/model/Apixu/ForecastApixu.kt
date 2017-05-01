package com.dev.nicola.allweather.model.Apixu

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ForecastApixu : RealmObject() {

    @SerializedName("forecastday")
    var forecastday: RealmList<ForecastDayApixu>? = null
}