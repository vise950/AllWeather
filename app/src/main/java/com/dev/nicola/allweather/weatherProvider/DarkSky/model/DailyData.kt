package com.dev.nicola.allweather.weatherProvider.DarkSky.model

import com.google.gson.annotations.SerializedName

class DailyData {

    @SerializedName("time")
    val time: Int? = null
    @SerializedName("summary")
    val summary: String? = null
    @SerializedName("icon")
    val icon: String? = null
    @SerializedName("sunriseTime")
    val sunriseTime: Int? = null
    @SerializedName("sunsetTime")
    val sunsetTime: Int? = null
    @SerializedName("moonPhase")
    val moonPhase: Double? = null
    @SerializedName("precipIntensity")
    val precipIntensity: Double? = null
    @SerializedName("precipIntensityMax")
    val precipIntensityMax: Double? = null
    @SerializedName("precipIntensityMaxTime")
    val precipIntensityMaxTime: Int? = null
    @SerializedName("precipProbability")
    val precipProbability: Double? = null
    @SerializedName("precipType")
    val precipType: String? = null
    @SerializedName("temperatureMin")
    val temperatureMin: Double? = null
    @SerializedName("temperatureMinTime")
    val temperatureMinTime: Int? = null
    @SerializedName("temperatureMax")
    val temperatureMax: Double? = null
    @SerializedName("temperatureMaxTime")
    val temperatureMaxTime: Int? = null
    @SerializedName("apparentTemperatureMin")
    val apparentTemperatureMin: Double? = null
    @SerializedName("apparentTemperatureMinTime")
    val apparentTemperatureMinTime: Int? = null
    @SerializedName("apparentTemperatureMax")
    val apparentTemperatureMax: Double? = null
    @SerializedName("apparentTemperatureMaxTime")
    val apparentTemperatureMaxTime: Int? = null
    @SerializedName("dewPoint")
    val dewPoint: Double? = null
    @SerializedName("humidity")
    val humidity: Double? = null
    @SerializedName("windSpeed")
    val windSpeed: Double? = null
    @SerializedName("windBearing")
    val windBearing: Int? = null
    @SerializedName("visibility")
    val visibility: Double? = null
    @SerializedName("cloudCover")
    val cloudCover: Double? = null
    @SerializedName("pressure")
    val pressure: Double? = null
    @SerializedName("ozone")
    val ozone: Double? = null
}
