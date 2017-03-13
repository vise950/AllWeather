package com.dev.nicola.allweather.weatherProvider.DarkSky.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings({"UnusedDeclaration"})
class DailyData {

    @SerializedName("time")
    private Integer time;
    @SerializedName("summary")
    private String summary;
    @SerializedName("icon")
    private String icon;
    @SerializedName("sunriseTime")
    private Integer sunriseTime;
    @SerializedName("sunsetTime")
    private Integer sunsetTime;
    @SerializedName("moonPhase")
    private Double moonPhase;
    @SerializedName("precipIntensity")
    private Double precipIntensity;
    @SerializedName("precipIntensityMax")
    private Double precipIntensityMax;
    @SerializedName("precipIntensityMaxTime")
    private Integer precipIntensityMaxTime;
    @SerializedName("precipProbability")
    private Double precipProbability;
    @SerializedName("precipType")
    private String precipType;
    @SerializedName("temperatureMin")
    private Double temperatureMin;
    @SerializedName("temperatureMinTime")
    private Integer temperatureMinTime;
    @SerializedName("temperatureMax")
    private Double temperatureMax;
    @SerializedName("temperatureMaxTime")
    private Integer temperatureMaxTime;
    @SerializedName("apparentTemperatureMin")
    private Double apparentTemperatureMin;
    @SerializedName("apparentTemperatureMinTime")
    private Integer apparentTemperatureMinTime;
    @SerializedName("apparentTemperatureMax")
    private Double apparentTemperatureMax;
    @SerializedName("apparentTemperatureMaxTime")
    private Integer apparentTemperatureMaxTime;
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

    public DailyData() {}

    public Integer getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getSunriseTime() {
        return sunriseTime;
    }

    public Integer getSunsetTime() {
        return sunsetTime;
    }

    public Double getMoonPhase() {
        return moonPhase;
    }

    public Double getPrecipIntensity() {
        return precipIntensity;
    }

    public Double getPrecipIntensityMax() {
        return precipIntensityMax;
    }

    public Integer getPrecipIntensityMaxTime() {
        return precipIntensityMaxTime;
    }

    public Double getPrecipProbability() {
        return precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public Integer getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public Integer getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public Double getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public Integer getApparentTemperatureMinTime() {
        return apparentTemperatureMinTime;
    }

    public Double getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public Integer getApparentTemperatureMaxTime() {
        return apparentTemperatureMaxTime;
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
