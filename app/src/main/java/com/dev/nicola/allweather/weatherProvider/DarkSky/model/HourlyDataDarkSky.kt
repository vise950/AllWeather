package com.dev.nicola.allweather.weatherProvider.DarkSky.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class HourlyDataDarkSky : RealmObject() {

    @SerializedName("time")
    var time: Long? = null
    @SerializedName("summary")
    var summary: String? = null
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("precipIntensity")
    var precipIntensity: Double? = null
    @SerializedName("precipProbability")
    var precipProbability: Double? = null
    @SerializedName("precipType")
    var precipType: String? = null
    @SerializedName("temperature")
    var temperature: Double? = null
    @SerializedName("apparentTemperature")
    var apparentTemperature: Double? = null
    @SerializedName("dewPoint")
    var dewPoint: Double? = null
    @SerializedName("humidity")
    var humidity: Double? = null
    @SerializedName("windSpeed")
    var windSpeed: Double? = null
    @SerializedName("windBearing")
    var windBearing: Int? = null
    @SerializedName("visibility")
    var visibility: Double? = null
    @SerializedName("cloudCover")
    var cloudCover: Double? = null
    @SerializedName("pressure")
    var pressure: Double? = null
    @SerializedName("ozone")
    var ozone: Double? = null
}
