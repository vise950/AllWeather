package com.dev.nicola.allweather.Provider.ForecastIO.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    /**
     * @return The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @return The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * @return The offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @return The currently
     */
    public Currently getCurrently() {
        return currently;
    }

    /**
     * @return The hourly
     */
    public Hourly getHourly() {
        return hourly;
    }

    /**
     * @return The daily
     */
    public Daily getDaily() {
        return daily;
    }
}