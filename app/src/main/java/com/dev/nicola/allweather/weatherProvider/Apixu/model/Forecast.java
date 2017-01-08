package com.dev.nicola.allweather.provider.Apixu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast {

    @SerializedName("forecastday")
    @Expose
    private List<Forecastday> forecastday = new ArrayList<Forecastday>();


    public List<Forecastday> getForecastday() {
        return forecastday;
    }
}
