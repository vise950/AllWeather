package com.dev.nicola.allweather.utils;

import java.text.DecimalFormat;

/**
 * Created by Nicola on 18/08/2016.
 */
public class UnitConverterUtils {

    public static int temperatureConverter(int temperature, String units) { //temperature è sempre fahrenheit
        int temp;
        switch (units) {
            case "celsius":
                temp = (temperature - 32) * 5 / 9;
                break;
            case "kelvin":
                temp = (int) (temperature + 459.67) * 5 / 9;
                break;
            default:
                temp = temperature;
        }
        return temp;

    }


    //coverter meter per second to kilometer per hour
    public static String speedConverter(double speed, String units) {
        String s;
        double n;
        switch (units) {
            case "ms":
                n = speed * 0.44704;
                s = new DecimalFormat("#.##").format(n);
                s = s + " m/s";
                break;
            case "kmh":
                n = speed * 1.609344;
                s = new DecimalFormat("#.##").format(n);
                s = s + " Km/h";
                break;
            default:
                s = speed + " mph";
        }
        return s;
    }
}
