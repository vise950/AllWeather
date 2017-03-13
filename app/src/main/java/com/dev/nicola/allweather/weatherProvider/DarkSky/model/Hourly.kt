package com.dev.nicola.allweather.weatherProvider.DarkSky.model

import com.google.gson.annotations.SerializedName

class Hourly {

    @SerializedName("summary")
    val summary: String? = null
    @SerializedName("icon")
    val icon: String? = null
    @SerializedName("data")
    val data: List<HourlyData>? = null
}
