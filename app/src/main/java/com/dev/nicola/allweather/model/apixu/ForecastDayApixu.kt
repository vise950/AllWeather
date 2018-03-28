package com.dev.nicola.allweather.model.apixu

open class ForecastDayApixu {
    var date: String? = null
    var day: DayApixu? = null
    var hour: List<HourApixu>? = listOf()
}