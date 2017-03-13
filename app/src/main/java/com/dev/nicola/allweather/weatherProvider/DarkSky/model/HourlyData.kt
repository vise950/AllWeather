package com.dev.nicola.allweather.weatherProvider.DarkSky.model

import com.google.gson.annotations.SerializedName

class HourlyData {

    @SerializedName("time")
    val time: Int? = null
    @SerializedName("summary")
    val summary: String? = null
    @SerializedName("icon")
    val icon: String? = null
    @SerializedName("precipIntensity")
    val precipIntensity: Double? = null
    @SerializedName("precipProbability")
    val precipProbability: Double? = null
    @SerializedName("precipType")
    val precipType: String? = null
    @SerializedName("temperature")
    val temperature: Double? = null
    @SerializedName("apparentTemperature")
    val apparentTemperature: Double? = null
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
