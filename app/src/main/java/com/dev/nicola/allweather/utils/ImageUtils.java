package com.dev.nicola.allweather.utils;

import android.content.res.Resources;

import com.dev.nicola.allweather.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nicola on 18/08/2016.
 */
public class ImageUtils {

    public static String getImage(Resources resources, long sunrise, long sunset, long time, String sSunrise, String sSunset) {
        String imageUrl;

//        Log.d(TAG,"date time "+time);
        if (sSunrise != null && sSunset != null) {
//            Log.d(TAG,"string sunrise "+sSunrise);
//            Log.d(TAG,"string sunset "+sSunset);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String date = df.format(c.getTime());
            SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy h:mm a", Locale.getDefault());
            try {
                Date d = dt.parse(date + " " + sSunrise);
//                Log.d(TAG,"date "+d);
                sunrise = d.getTime();
//                Log.d(TAG,"date millisecond "+sunrise);
                Date d1 = dt.parse(date + " " + sSunset);
//                Log.d(TAG,"date1 "+d1);
                sunset = d1.getTime();
//                Log.d(TAG,"date1 millisecond "+sunset);
                time = time * 1000L;
//                Log.d(TAG,"date time in millisecond "+time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int random = (int) (Math.random() * 9);
        if (time >= sunrise - 1800L && time <= sunrise + 1800L) {
            String sunriseWall[] = resources.getStringArray(R.array.sunrise_wallpaper);
            imageUrl = sunriseWall[random];
        } else {
            if (time > sunrise + 1800L && time < sunset - 1800L) {
                String dayWall[] = resources.getStringArray(R.array.day_wallpaper);
                imageUrl = dayWall[random];
            } else {
                if (time >= sunset - 1800L && time <= sunset + 1800L) {
                    String sunsetWall[] = resources.getStringArray(R.array.sunset_wallpaper);
                    imageUrl = sunsetWall[random];
                } else {
                    String nightWall[] = resources.getStringArray(R.array.night_wallpaper);
                    imageUrl = nightWall[random];
                }
            }
        }

        return imageUrl;
    }
}
