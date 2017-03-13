package com.dev.nicola.allweather.weatherProvider.DarkSky.model

import com.google.gson.annotations.SerializedName

class RootData {

    @SerializedName("latitude")
    val latitude: Double? = null
    @SerializedName("longitude")
    val longitude: Double? = null
    @SerializedName("timezone")
    val timezone: String? = null
    @SerializedName("offset")
    val offset: Int? = null
    @SerializedName("currently")
    val currently: Currently? = null
    @SerializedName("hourly")
    val hourly: Hourly? = null
    @SerializedName("daily")
    val daily: Daily? = null
}
