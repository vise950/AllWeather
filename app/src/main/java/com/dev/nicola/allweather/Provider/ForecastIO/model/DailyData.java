package com.dev.nicola.allweather.provider.ForecastIO.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyData {

    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("sunriseTime")
    @Expose
    private Integer sunriseTime;
    @SerializedName("sunsetTime")
    @Expose
    private Integer sunsetTime;
    @SerializedName("moonPhase")
    @Expose
    private Double moonPhase;
    @SerializedName("precipIntensity")
    @Expose
    private Double precipIntensity;
    @SerializedName("precipIntensityMax")
    @Expose
    private Double precipIntensityMax;
    @SerializedName("precipIntensityMaxTime")
    @Expose
    private Double precipIntensityMaxTime;
    @SerializedName("precipProbability")
    @Expose
    private Double precipProbability;
    @SerializedName("precipType")
    @Expose
    private String precipType;
    @SerializedName("temperatureMin")
    @Expose
    private Double temperatureMin;
    @SerializedName("temperatureMinTime")
    @Expose
    private Double temperatureMinTime;
    @SerializedName("temperatureMax")
    @Expose
    private Double temperatureMax;
    @SerializedName("temperatureMaxTime")
    @Expose
    private Double temperatureMaxTime;
    @SerializedName("apparentTemperatureMin")
    @Expose
    private Double apparentTemperatureMin;
    @SerializedName("apparentTemperatureMinTime")
    @Expose
    private Double apparentTemperatureMinTime;
    @SerializedName("apparentTemperatureMax")
    @Expose
    private Double apparentTemperatureMax;
    @SerializedName("apparentTemperatureMaxTime")
    @Expose
    private Double apparentTemperatureMaxTime;
    @SerializedName("dewPoint")
    @Expose
    private Double dewPoint;
    @SerializedName("humidity")
    @Expose
    private Double humidity;
    @SerializedName("windSpeed")
    @Expose
    private Double windSpeed;
    @SerializedName("windBearing")
    @Expose
    private Integer windBearing;
    @SerializedName("cloudCover")
    @Expose
    private Double cloudCover;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("ozone")
    @Expose
    private Double ozone;


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

    public Double getPrecipIntensityMaxTime() {
        return precipIntensityMaxTime;
    }

    public Double getPrecipProbability() {
        return precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public Integer getTemperatureMin() {
        return temperatureMin.intValue();
    }

    public Double getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public Integer getTemperatureMax() {
        return temperatureMax.intValue();
    }

    public Double getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public Double getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public Double getApparentTemperatureMinTime() {
        return apparentTemperatureMinTime;
    }

    public Double getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public Double getApparentTemperatureMaxTime() {
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
