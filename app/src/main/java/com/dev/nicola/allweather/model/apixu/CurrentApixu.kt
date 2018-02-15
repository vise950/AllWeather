package com.dev.nicola.allweather.model.apixu

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class CurrentApixu : RealmObject() {
    @SerializedName("temp_c")
    var tempC: Double? = null
    @SerializedName("is_day")
    var isDay: Int? = null
    @SerializedName("condition")
    var currentConditionApixu: CurrentConditionApixu? = null
    @SerializedName("wind_mph")
    var windMph: Double? = null
    @SerializedName("wind_degree")
    var windDegree: Int? = null
    @SerializedName("wind_dir")
    var windDir: String? = null
    @SerializedName("pressure_mb")
    var pressureMb: Double? = null
    @SerializedName("precip_mm")
    var precipMm: Double? = null
    @SerializedName("humidity")
    var humidity: Int? = null
    @SerializedName("cloud")
    var cloud: Int? = null
}