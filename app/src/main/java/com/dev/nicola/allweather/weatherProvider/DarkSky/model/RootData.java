package com.dev.nicola.allweather.weatherProvider.DarkSky.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings({"UnusedDeclaration"})
public class RootData {

    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("offset")
    private Integer offset;
    @SerializedName("currently")
    private Currently currently;
    @SerializedName("hourly")
    private Hourly hourly;
    @SerializedName("daily")
    private Daily daily;

    public RootData() {}

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public Integer getOffset() {
        return offset;
    }

    public Currently getCurrently() {
        return currently;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public Daily getDaily() {
        return daily;
    }
}
