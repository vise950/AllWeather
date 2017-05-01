package com.dev.nicola.allweather.model.GoolgeResponse

import com.google.gson.annotations.SerializedName

class Coordinates {
    @SerializedName("results")
    val result: List<Result>? = null
    @SerializedName("status")
    val status: String? = null

    inner class Result {
        @SerializedName("geometry")
        val geometry: Geometry? = null
    }

    inner class Geometry {
        @SerializedName("location")
        val location: Location? = null
    }

    inner class Location {
        @SerializedName("lat")
        val lat: Double? = null
        @SerializedName("lng")
        val lng: Double? = null
    }
}




