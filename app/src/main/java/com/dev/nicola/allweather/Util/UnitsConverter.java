package com.dev.nicola.allweather.Util;

import android.content.Context;

import java.text.DecimalFormat;

/**
 * Created by Nicola on 01/07/2016.
 */
public class UnitsConverter {

    private static String TAG = Utils.class.getSimpleName();
    private Context mContext;

    public UnitsConverter(Context context) {
        this.mContext = context;
    }

    public int CelsiusToFahrenheit(int temperature) {
        return (temperature * 9 / 5) + 32;
    }

    public int CelsiusToKelvin(int temperature) {
        return (int) (temperature + 273.15);
    }

    //coverter meter per second to kilometer per hour
    public String MsToKmh(double speed) {
        double n = speed * 3.6;
        String s = new DecimalFormat("#.##").format(n);
        return s + " Km/h";
    }

    // converter meter per second to miles per hour
    public String MsToKph(double speed) {
        double n = speed / 0.44704;
        String s = new DecimalFormat("#.##").format(n);
        return s + " mph";
    }

}
