package com.dev.nicola.allweather.weatherProvider.Apixu.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RootApixu : RealmObject() {

    @PrimaryKey
    var id: String? = null
    @SerializedName("location")
    var location: LocationApixu? = null
    @SerializedName("current")
    var current: CurrentApixu? = null
    @SerializedName("forecast")
    var forecast: ForecastApixu? = null
}