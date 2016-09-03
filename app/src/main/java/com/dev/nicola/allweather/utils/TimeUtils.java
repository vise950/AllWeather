package com.dev.nicola.allweather.utils;

import android.content.res.Resources;

import com.dev.nicola.allweather.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Nicola on 18/08/2016.
 */
public class TimeUtils {

    public static String getHourFormat(long time, String sTime, String units) {
        Date date = null;
        if (sTime != null) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String d = df.format(c.getTime());
            SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy h:mm a", Locale.getDefault());
            try {
                date = dt.parse(d + " " + sTime);
//                Log.d(TAG, "date " + date);
                time = date.getTime();
//                Log.d(TAG, "time " + time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            date = new Date(time * 1000L);
        }

        SimpleDateFormat hour = null;

        /*  h is used for AM/PM times (1-12).
            H is used for 24 hour times (1-24).
            a is the AM/PM marker
            m is minute in hour
            Two h's will print a leading zero: 01:13 PM. One h will print without the leading zero: 1:13 PM.
        */
        switch (units) {
            case "1":
                hour = new SimpleDateFormat("h:mm a", Locale.getDefault());
                break;
            case "2":
                hour = new SimpleDateFormat("H:mm", Locale.getDefault());
                break;
        }
        assert hour != null;
        return hour.format(date);
    }


    public static String getDayFormat(Resources resources, Integer time) {
        String t;
        String days[] = resources.getStringArray(R.array.days);
        String months[] = resources.getStringArray(R.array.months);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000L);
        calendar.setTimeZone(TimeZone.getDefault());

        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        t = days[dayIndex - 1] + " " + day + " " + months[month];
        return t;
    }


    public static int getLocalTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
