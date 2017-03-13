package com.dev.nicola.allweather.weatherProvider.DarkSky.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings({"UnusedDeclaration"})
class Daily {

    @SerializedName("summary")
    private String summary;
    @SerializedName("icon")
    private String icon;
    @SerializedName("data")
    private List<DailyData> data = null;

    public Daily() {}

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public List<DailyData> getData() {
        return data;
    }
}
