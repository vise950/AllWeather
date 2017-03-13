package com.dev.nicola.allweather.weatherProvider.DarkSky.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings({"UnusedDeclaration"})
class HourlyData {

    @SerializedName("time")
    private Integer time;
    @SerializedName("summary")
    private String summary;
    @SerializedName("icon")
    private String icon;
    @SerializedName("precipIntensity")
    private Double precipIntensity;
    @SerializedName("precipProbability")
    private Double precipProbability;
    @SerializedName("precipType")
    private String precipType;
    @SerializedName("temperature")
    private Double temperature;
    @SerializedName("apparentTemperature")
    private Double apparentTemperature;
    @SerializedName("dewPoint")
    private Double dewPoint;
    @SerializedName("humidity")
    private Double humidity;
    @SerializedName("windSpeed")
    private Double windSpeed;
    @SerializedName("windBearing")
    private Integer windBearing;
    @SerializedName("visibility")
    private Double visibility;
    @SerializedName("cloudCover")
    private Double cloudCover;
    @SerializedName("pressure")
    private Double pressure;
    @SerializedName("ozone")
    private Double ozone;

    public HourlyData() {}

    public Integer getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public Double getPrecipIntensity() {
        return precipIntensity;
    }

    public Double getPrecipProbability() {
        return precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Integer getWindBearing() {
        return windBearing;
    }

    public Double getVisibility() {
        return visibility;
    }

    public Double getCloudCover() {
        return cloudCover;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getOzone() {
        return ozone;
    }
}
