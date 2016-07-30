package com.dev.nicola.allweather.Util;

import android.content.Context;

import java.text.DecimalFormat;

/**
 * Created by Nicola on 01/07/2016.
 */
public class UnitsConverter {

    private Context mContext;

    public UnitsConverter(Context context) {
        this.mContext = context;
    }

    public int CelsiusToFahrenheitOrKelvin(int temperature, String units) {
        int temp;
        switch (units) {
            case "2":
                temp = (temperature * 9 / 5) + 32;
                break;
            case "3":
                temp = (int) (temperature + 273.15);
                break;
            default:
                temp = temperature;
        }
        return temp;

    }

    //coverter meter per second to kilometer per hour
    public String MsToKmhOrKph(double speed, String units) {
        String s;
        double n;
        switch (units) {
            case "1":
                n = speed / 0.44704;
                s = new DecimalFormat("#.##").format(n);
                s = s + " mph";
                break;
            case "2":
                n = speed * 3.6;
                s = new DecimalFormat("#.##").format(n);
                s = s + " Km/h";
                break;
            default:
                s = speed + " m/s";

        }
        return s;
    }
}
