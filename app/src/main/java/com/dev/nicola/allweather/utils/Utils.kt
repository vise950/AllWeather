package com.dev.nicola.allweather.utils

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatDelegate
import com.dev.nicola.allweather.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.text.DecimalFormat
import java.util.*


class Utils {

    //todo calculate sunrise and sunset https://www.programcreek.com/java-api-examples/index.php?source_dir=SunriseSunset-master/library/src/main/java/ca/rmen/sunrisesunset/SunriseSunset.java

    companion object {

        fun isLocationEnable(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        fun isConnectedToInternet(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnectedOrConnecting
        }

        fun changeTheme(context: Context) {
            when (PreferencesHelper.getDefaultPreferences(context, PreferencesHelper.KEY_PREF_THEME, PreferencesHelper.DEFAULT_PREF_THEME)) {
                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        fun trimString(s: String): String {
            val index = s.indexOf(',')
            val lastIndex = s.lastIndexOf(',')
            if (index != lastIndex) {
                return s.substring(0, index) + s.substring(lastIndex, s.length)
            }
            return s
        }

        fun getHourImage(context: Context, sunset: Long?, sunrise: Long?): String {
            val imageUrl: String
            val time = Utils.TimeHelper.localTimeMillis

            val sunriseWall = context.resources.getStringArray(R.array.sunrise_wallpaper)
            val dayWall = context.resources.getStringArray(R.array.day_wallpaper)
            val sunsetWall = context.resources.getStringArray(R.array.sunset_wallpaper)
            val nightWall = context.resources.getStringArray(R.array.night_wallpaper)

            val random = (Math.random() * sunriseWall.size).toInt()

            if (time >= sunrise ?: 0L - 1800L && time <= sunrise ?: 0L + 1800L) {
                imageUrl = sunriseWall[random]
            } else {
                if (time > sunrise ?: 0L + 1800L && time < sunset ?: 0L - 1800L) {
                    imageUrl = dayWall[random]
                } else {
                    if (time >= sunset ?: 0L - 1800L && time <= sunset ?: 0L + 1800L) {
                        imageUrl = sunsetWall[random]
                    } else {
                        imageUrl = nightWall[random]
                    }
                }
            }
            return imageUrl
        }

        fun getMonthImage(context: Context):String{
            val monthWall=context.resources.getStringArray(R.array.months_wallpaper)
            val month = Calendar.getInstance().get(Calendar.MONTH)
            return monthWall[month]
        }
    }

    object TimeHelper {

        fun formatTime(time: Long, pref: String): String? {
            var dtf: DateTimeFormatter? = null
            val dateTime = DateTime(time * 1000L, DateTimeZone.forTimeZone(TimeZone.getDefault()))
            when (pref) {
                "12" -> dtf = DateTimeFormat.forPattern("h:mm a")
                "24" -> dtf = DateTimeFormat.forPattern("H:mm")
            }
            return dtf?.print(dateTime)
        }

        fun getOfflineTime(time: Long?): String? {
            val dtf = DateTimeFormat.forPattern("dd/MM H:mm")
            return dtf.print(time ?: 0L)
        }

        fun getHour(pref: String, i: Int): String? {
            val dt = DateTime().plusHours(i)
            var dtf: DateTimeFormatter? = null
            when (pref) {
                "12" -> dtf = DateTimeFormat.forPattern("h:00 a")
                "24" -> dtf = DateTimeFormat.forPattern("H:00")
            }
            return dtf?.print(dt)
        }

        fun getDate(context: Context, i: Int): String {
            val n = (86400000 * i).toLong() // n = 24h in millis * giorno  ex. 24h * 2 = dopodomani
            val days = context.resources.getStringArray(R.array.days)
            val months = context.resources.getStringArray(R.array.months)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = calendar.timeInMillis + n //aggiungo n giorni ad oggi
            calendar.timeZone = TimeZone.getDefault()

            val dayIndex = calendar.get(Calendar.DAY_OF_WEEK)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            return days[dayIndex - 1] + " " + day + " " + months[month]
        }

        val today: Int
            get() {
                return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            }

        val localTimeHour: Int
            get() {
                return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            }

        val localTimeMillis: Long
            get() {
                return Calendar.getInstance().timeInMillis
            }
    }


    object ConverterHelper {
        fun temperature(temperature: Double, pref: String, default: String? = null): String {
            when (pref) {
                "celsius" -> {
                    if (default == pref) {
                        return DecimalFormat("#").format((temperature)) + "°"
                    } else {
                        return DecimalFormat("#").format((temperature - 32) * 5 / 9) + "°"
                    }
                }
                "kelvin" -> return DecimalFormat("#").format((temperature + 459.67) * 5 / 9) + "°"
                else -> return DecimalFormat("#").format((temperature)) + "°"
            }
        }

        fun speed(speed: Double, units: String): String {
            when (units) {
                "ms" -> return DecimalFormat("#.##").format(speed * 0.44704) + " m/s"
                "kmh" -> return DecimalFormat("#.##").format(speed * 1.609344) + " Km/h"
                else -> return DecimalFormat("#.##").format(speed) + " mph"
            }
        }

        fun windDirection(degrees: Int): String {
            if (degrees != -1) {
                val cardinal = arrayOf("N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW")
                val n = (degrees / 22.5 + 0.5).toInt()
                return cardinal[n % cardinal.size]
            } else {
                return "error"
            }
        }

        fun weatherIcon(condition: String): Int {
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

    }
}