package com.dev.nicola.allweather.provider.ForecastIO.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyData {

    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("precipIntensity")
    @Expose
    private Double precipIntensity;
    @SerializedName("precipProbability")
    @Expose
    private Double precipProbability;
    @SerializedName("temperature")
    @Expose
    private Double temperature;
    @SerializedName("apparentTemperature")
    @Expose
    private Double apparentTemperature;
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
    @SerializedName("precipType")
    @Expose
    private String precipType;

    /**
     * @return The time
     */
    public Integer getTime() {
        return time;
    }

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
     * @return The precipIntensity
     */
    public Double getPrecipIntensity() {
        return precipIntensity;
    }

    /**
     * @return The precipProbability
     */
    public Double getPrecipProbability() {
        return precipProbability;
    }

    /**
     * @return The temperature
     */
    public Integer getTemperature() {
        return temperature.intValue();
    }

    /**
     * @return The apparentTemperature
     */
    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    /**
     * @return The dewPoint
     */
    public Double getDewPoint() {
        return dewPoint;
    }

    /**
     * @return The humidity
     */
    public Double getHumidity() {
        return humidity;
    }

    /**
     * @return The windSpeed
     */
    public Double getWindSpeed() {
        return windSpeed;
    }

    /**
     * @return The windBearing
     */
    public Integer getWindBearing() {
        return windBearing;
    }

    /**
     * @return The cloudCover
     */
    public Double getCloudCover() {
        return cloudCover;
    }

    /**
     * @return The pressure
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     * @return The ozone
     */
    public Double getOzone() {
        return ozone;
    }

    /**
     * @return The precipType
     */
    public String getPrecipType() {
        return precipType;
    }
}
