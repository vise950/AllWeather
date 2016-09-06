package com.dev.nicola.allweather.utils;

import java.text.DecimalFormat;

/**
 * Created by Nicola on 18/08/2016.
 */
public class UnitConverterUtils {

    public static int CelsiusToFahrenheitOrKelvin(int temperature, String units) {
        int temp;
        switch (units) {
            case "fahrenheit":
                temp = (temperature * 9 / 5) + 32;
                break;
            case "kelvin":
                temp = (int) (temperature + 273.15);
                break;
            default:
                temp = temperature;
        }
        return temp;

    }


    //coverter meter per second to kilometer per hour
    public static String MsToKmhOrKph(double speed, String units) {
        String s;
        double n;
        switch (units) {
            case "mph":
                n = speed / 0.44704;
                s = new DecimalFormat("#.##").format(n);
                s = s + " mph";
                break;
            case "kmh":
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
