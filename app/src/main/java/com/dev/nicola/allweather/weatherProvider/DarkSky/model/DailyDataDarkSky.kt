package com.dev.nicola.allweather.weatherProvider.DarkSky.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class DailyDataDarkSky : RealmObject() {

    @SerializedName("time")
    var time: Long? = null
    @SerializedName("summary")
    var summary: String? = null
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("sunriseTime")
    var sunriseTime: Long? = null
    @SerializedName("sunsetTime")
    var sunsetTime: Long? = null
    @SerializedName("moonPhase")
    var moonPhase: Double? = null
    @SerializedName("precipIntensity")
    var precipIntensity: Double? = null
    @SerializedName("precipIntensityMax")
    var precipIntensityMax: Double? = null
    @SerializedName("precipIntensityMaxTime")
    var precipIntensityMaxTime: Int? = null
    @SerializedName("precipProbability")
    var precipProbability: Double? = null
    @SerializedName("precipType")
    var precipType: String? = null
    @SerializedName("temperatureMin")
    var temperatureMin: Double? = null
    @SerializedName("temperatureMinTime")
    var temperatureMinTime: Int? = null
    @SerializedName("temperatureMax")
    var temperatureMax: Double? = null
    @SerializedName("temperatureMaxTime")
    var temperatureMaxTime: Int? = null
    @SerializedName("apparentTemperatureMin")
    var apparentTemperatureMin: Double? = null
    @SerializedName("apparentTemperatureMinTime")
    var apparentTemperatureMinTime: Int? = null
    @SerializedName("apparentTemperatureMax")
    var apparentTemperatureMax: Double? = null
    @SerializedName("apparentTemperatureMaxTime")
    var apparentTemperatureMaxTime: Int? = null
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
