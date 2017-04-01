package com.dev.nicola.allweather.utils

enum class WeatherProvider(val value: String) {
    DARK_SKY("darkSky"),
    APIXU("apixu"),
    YAHOO("yahoo");

    companion object {
        fun fromString(value: String?) = values().filter { it.value == value }.firstOrNull() ?: DARK_SKY
    }
}
