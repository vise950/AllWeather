package com.dev.nicola.allweather.utils

import com.dev.nicola.allweather.R

/**
 * Created by Nicola on 17/08/2016.
 */
object WeatherUtils {

    fun getWeatherIcon(condition: String): Int {
        val icon: Int

        when (condition) {
            "1000", "clear-day", "32" -> icon = R.drawable.clear_day
            "clear-night", "31" -> icon = R.drawable.clear_night
            "1003", "partly-cloudy-day", "30", "28" -> icon = R.drawable.partly_cloudy_day
            "partly-cloudy-night", "29", "27" -> icon = R.drawable.partly_cloudy_night
            "1087", "1009", "1006", "cloudy", "26" -> icon = R.drawable.cloud
            "1195", "1192", "1189", "1186", "1183", "1063", "rain", "11", "12" -> icon = R.drawable.rain
            "1225", "1222", "1219", "1216", "1213", "1210", "1114", "1066", "snow", "16" -> icon = R.drawable.snow
            "1072", "1069", "sleet", "18" -> icon = R.drawable.sleet
            "1117", "wind", "24" -> icon = R.drawable.wind
            "1147", "1135", "1030", "fog", "20" -> icon = R.drawable.fog
            "1273", "1276", "1279", "1282", "4", "37", "38", "39", "47" -> icon = R.drawable.storm
            else -> icon = R.drawable.unknown
        }
        return icon
    }


    fun getWindDirection(degrees: Int): String {
        val direction: String
        val cardinal = arrayOf("N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW")
        val n = (degrees / 22.5 + 0.5).toInt()
        direction = cardinal[n % cardinal.size]
        return direction
    }
}
