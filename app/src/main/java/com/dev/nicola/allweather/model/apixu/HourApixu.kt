package com.dev.nicola.allweather.model.apixu

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class HourApixu : RealmObject() {

    @SerializedName("time_epoch")
    var timeEpoch: Long? = null
    @SerializedName("time")
    var time: String? = null
    @SerializedName("temp_c")
    var tempC: Double? = null
    @SerializedName("is_day")
    var isDay: Int? = null
    @SerializedName("condition")
    var condition: HourConditionApixu? = null
}