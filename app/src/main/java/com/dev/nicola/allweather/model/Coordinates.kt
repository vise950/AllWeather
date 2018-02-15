package com.dev.nicola.allweather.model

import com.google.gson.annotations.SerializedName

class Coordinates {
    val result: List<Result>? = null

    inner class Result {
        val geometry: Geometry? = null
    }

    inner class Geometry {
        val location: Location? = null
    }

    inner class Location {
        val lat: Double? = null
        val lng: Double? = null
    }
}