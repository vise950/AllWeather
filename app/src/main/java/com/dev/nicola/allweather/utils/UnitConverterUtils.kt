package com.dev.nicola.allweather.utils

import java.text.DecimalFormat

/**
 * Created by Nicola on 18/08/2016.
 */
object UnitConverterUtils {

    /**
     * @param temperature is temperature that will be converted
     * *                    temperature is in fahrenheit
     * *
     * @param units       is units take form Preferences of temperature
     * *
     * @return int temperature
     */
    fun temperatureConverter(temperature: Int, units: String): Int {
        val temp: Int
        when (units) {
            "celsius" -> temp = (temperature - 32) * 5 / 9
            "kelvin" -> temp = (temperature + 459.67).toInt() * 5 / 9
            else -> temp = temperature
        }
        return temp

    }


    /**
     * @param speed is speed that will be converted
     * *              speed is in mph
     * *
     * @param units
     * *
     * @return
     */
    fun speedConverter(speed: Double, units: String): String {
        var s: String
        val n: Double
        when (units) {
            "ms" -> {
                n = speed * 0.44704
                s = DecimalFormat("#.##").format(n)
                s += " m/s"
            }
            "kmh" -> {
                n = speed * 1.609344
                s = DecimalFormat("#.##").format(n)
                s += " Km/h"
            }
            else -> s = speed.toString() + " mph"
        }
        return s
    }
}
