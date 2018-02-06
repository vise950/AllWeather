package com.dev.nicola.allweather.model.apixu

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class CurrentApixu : RealmObject() {

    @SerializedName("last_updated_epoch")
    var lastUpdatedEpoch: Int? = null
    @SerializedName("last_updated")
    var lastUpdated: String? = null
    @SerializedName("temp_c")
    var tempC: Double? = null
    @SerializedName("temp_f")
    var tempF: Double? = null
    @SerializedName("is_day")
    var isDay: Int? = null
    @SerializedName("condition")
    var currentConditionApixu: CurrentConditionApixu? = null
    @SerializedName("wind_mph")
    var windMph: Double? = null
    @SerializedName("wind_kph")
    var windKph: Double? = null
    @SerializedName("wind_degree")
    var windDegree: Int? = null
    @SerializedName("wind_dir")
    var windDir: String? = null
    @SerializedName("pressure_mb")
    var pressureMb: Double? = null
    @SerializedName("pressure_in")
    var pressureIn: Double? = null
    @SerializedName("precip_mm")
    var precipMm: Double? = null
    @SerializedName("precip_in")
    var precipIn: Double? = null
    @SerializedName("humidity")
    var humidity: Int? = null
    @SerializedName("cloud")
    var cloud: Int? = null
    @SerializedName("feelslike_c")
    var feelslikeC: Double? = null
    @SerializedName("feelslike_f")
    var feelslikeF: Double? = null
}