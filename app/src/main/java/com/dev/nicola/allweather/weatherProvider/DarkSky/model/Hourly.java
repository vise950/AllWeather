package com.dev.nicola.allweather.weatherProvider.DarkSky.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings({"UnusedDeclaration"})
class Hourly {

    @SerializedName("summary")
    private String summary;
    @SerializedName("icon")
    private String icon;
    @SerializedName("data")
    private List<HourlyData> data = null;

    public Hourly() {}

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public List<HourlyData> getData() {
        return data;
    }
}
