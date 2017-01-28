package com.dev.nicola.allweather.weatherProvider.Apixu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created with http://www.jsonschema2pojo.org/
 */

public class ApixuData {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("current")
    @Expose
    private Current current;
    @SerializedName("forecast")
    @Expose
    private Forecast forecast;


    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }
}
