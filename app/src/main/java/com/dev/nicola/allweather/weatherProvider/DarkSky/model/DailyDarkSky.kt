package com.dev.nicola.allweather.weatherProvider.DarkSky.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class DailyDarkSky : RealmObject() {

    @SerializedName("summary")
    var summary: String? = null
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("data")
    var data: RealmList<DailyDataDarkSky>? = null
}
