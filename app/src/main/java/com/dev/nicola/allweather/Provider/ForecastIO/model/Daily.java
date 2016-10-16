package com.dev.nicola.allweather.provider.ForecastIO.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Daily {

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("data")
    @Expose
    private List<DailyData> data = new ArrayList<>();


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
