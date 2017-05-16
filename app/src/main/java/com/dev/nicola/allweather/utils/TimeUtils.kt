package com.dev.nicola.allweather.utils

import android.content.res.Resources
import com.dev.nicola.allweather.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Nicola on 18/08/2016.
 */
object TimeUtils {

    fun getHourFormat(time: Long, sTime: String?, units: String): String {
        var date: Date? = null
        if (sTime != null) {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val d = df.format(c.time)
            val dt = SimpleDateFormat("dd-MMM-yyyy h:mm a", Locale.getDefault())
            try {
                date = dt.parse(d + " " + sTime)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        } else {
            date = Date(time * 1000L)
        }

        var hour: SimpleDateFormat? = null

        /*  h is used for AM/PM times (1-12).
            H is used for 24 hour times (1-24).
            a is the AM/PM marker
            m is minute in hour
            Two h's will print a leading zero: 01:13 PM. One h will print without the leading zero: 1:13 PM.
        */
        when (units) {
            "12" -> hour = SimpleDateFormat("h:mm a", Locale.getDefault())
            "24" -> hour = SimpleDateFormat("H:mm", Locale.getDefault())
        }
        assert(hour != null)
        return hour!!.format(date)
    }


    fun getDay(resources: Resources, i: Int): String {
        val n = (86400000 * i).toLong() // n = 24h in millis * giorno  ex. 24h * 2 = dopodomani
        val days = resources.getStringArray(R.array.days)
        val months = resources.getStringArray(R.array.months)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = calendar.timeInMillis + n //aggiungo n giorni ad oggi
        calendar.timeZone = TimeZone.getDefault()

        val dayIndex = calendar.get(Calendar.DAY_OF_WEEK)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        return days[dayIndex - 1] + " " + day + " " + months[month]

    }


    val localTimeHour: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.HOUR_OF_DAY)
        }

    val localTimeMillis: Long
        get() {
            val calendar = Calendar.getInstance()
            return calendar.timeInMillis
        }
}
