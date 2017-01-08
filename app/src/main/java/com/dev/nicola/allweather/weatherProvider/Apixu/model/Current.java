package com.dev.nicola.allweather.provider.Apixu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current {

    @SerializedName("last_updated_epoch")
    @Expose
    private Integer lastUpdatedEpoch;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("temp_c")
    @Expose
    private Double tempC;
    @SerializedName("temp_f")
    @Expose
    private Double tempF;
    @SerializedName("is_day")
    @Expose
    private Integer isDay;
    @SerializedName("condition")
    @Expose
    private CurrentCondition mCurrentCondition;
    @SerializedName("wind_mph")
    @Expose
    private Double windMph;
    @SerializedName("wind_kph")
    @Expose
    private Double windKph;
    @SerializedName("wind_degree")
    @Expose
    private Integer windDegree;
    @SerializedName("wind_dir")
    @Expose
    private String windDir;
    @SerializedName("pressure_mb")
    @Expose
    private Double pressureMb;
    @SerializedName("pressure_in")
    @Expose
    private Double pressureIn;
    @SerializedName("precip_mm")
    @Expose
    private Double precipMm;
    @SerializedName("precip_in")
    @Expose
    private Double precipIn;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("cloud")
    @Expose
    private Integer cloud;
    @SerializedName("feelslike_c")
    @Expose
    private Double feelslikeC;
    @SerializedName("feelslike_f")
    @Expose
    private Double feelslikeF;


    public Integer getLastUpdatedEpoch() {
        return lastUpdatedEpoch;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public Integer getTempC() {
        return tempC.intValue();
    }

    public Integer getTempF() {
        return tempF.intValue();
    }

    public Integer getIsDay() {
        return isDay;
    }

    public CurrentCondition getCurrentCondition() {
        return mCurrentCondition;
    }

    public Double getWindMph() {
        return windMph;
    }

    public Double getWindKph() {
        return windKph;
    }

    public Integer getWindDegree() {
        return windDegree;
    }

    public String getWindDir() {
        return windDir;
    }

    public Double getPressureMb() {
        return pressureMb;
    }

    public Double getPressureIn() {
        return pressureIn;
    }

    public Double getPrecipMm() {
        return precipMm;
    }

    public Double getPrecipIn() {
        return precipIn;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Integer getCloud() {
        return cloud;
    }

    public Double getFeelslikeC() {
        return feelslikeC;
    }

    public Double getFeelslikeF() {
        return feelslikeF;
    }
}
