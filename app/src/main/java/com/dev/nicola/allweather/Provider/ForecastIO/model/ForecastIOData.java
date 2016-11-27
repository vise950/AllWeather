package com.dev.nicola.allweather.provider.ForecastIO.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created with http://www.jsonschema2pojo.org/
 */

public class ForecastIOData {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("currently")
    @Expose
    private Currently currently;
    @SerializedName("hourly")
    @Expose
    private Hourly hourly;
    @SerializedName("daily")
    @Expose
    private Daily daily;


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