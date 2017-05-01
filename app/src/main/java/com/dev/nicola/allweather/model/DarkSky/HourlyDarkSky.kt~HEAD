package com.dev.nicola.allweather.model.DarkSky

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class HourlyDarkSky : RealmObject() {

    @SerializedName("summary")
    var summary: String? = null
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("data")
    var data: RealmList<HourlyDataDarkSky>? = null
}
