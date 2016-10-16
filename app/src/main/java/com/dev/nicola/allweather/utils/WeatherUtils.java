package com.dev.nicola.allweather.utils;

import com.dev.nicola.allweather.R;

/**
 * Created by Nicola on 17/08/2016.
 */
public class WeatherUtils {

    public static int getWeatherIcon(String condition) {
        int icon;

        switch (condition) {
            case "1000":
            case "clear-day":
            case "32":
                icon = R.drawable.clear_day;
                break;
            case "clear-night":
            case "31":
                icon = R.drawable.clear_night;
                break;
            case "1003":
            case "partly-cloudy-day":
            case "30":
            case "28":
                icon = R.drawable.partly_cloudy_day;
                break;
            case "partly-cloudy-night":
            case "29":
            case "27":
                icon = R.drawable.partly_cloudy_night;
                break;
            case "1087":
            case "1009":
            case "1006":
            case "cloudy":
            case "26":
                icon = R.drawable.cloud;
                break;
            case "1195":
            case "1192":
            case "1189":
            case "1186":
            case "1183":
            case "1063":
            case "rain":
            case "11":
            case "12":
                icon = R.drawable.rain;
                break;
            case "1225":
            case "1222":
            case "1219":
            case "1216":
            case "1213":
            case "1210":
            case "1114":
            case "1066":
            case "snow":
            case "16":
                icon = R.drawable.snow;
                break;
            case "1072":
            case "1069":
            case "sleet":
            case "18":
                icon = R.drawable.sleet;
                break;
            case "1117":
            case "wind":
            case "24":
                icon = R.drawable.wind;
                break;
            case "1147":
            case "1135":
            case "1030":
            case "fog":
            case "20":
                icon = R.drawable.fog;
                break;
            case "1273":
            case "1276":
            case "1279":
            case "1282":
            case "4":
            case "37":
            case "38":
            case "39":
            case "47":
                icon = R.drawable.storm;
                break;
            default:
                icon = R.drawable.unknown;
                break;
        }
        return icon;
    }


    public static String getWindDirection(int degrees) {
        String direction;
        String cardinal[] = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        int n = (int) ((degrees / 22.5) + 0.5);
        direction = cardinal[n % cardinal.length];
        return direction;
    }
}
