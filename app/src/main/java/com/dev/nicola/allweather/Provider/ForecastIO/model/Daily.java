package com.dev.nicola.allweather.Provider.ForecastIO.model;

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

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @return The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @return The data
     */
    public List<DailyData> getData() {
        return data;
    }
}
