package com.dev.nicola.allweather.model.darkSky

data class CurrentlyDarkSky(
        var time: Int,
        var summary: String,
        var icon: String,
        var precipIntensity: Double,
        var precipProbability: Double,
        var temperature: Double,
        var dewPoint: Double,
        var humidity: Double,
        var windSpeed: Double,
        var windBearing: Int,
        var visibility: Double,
        var cloudCover: Double,
        var pressure: Double)