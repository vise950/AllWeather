package com.dev.nicola.allweather.weatherProvider.Apixu.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hour {

    @SerializedName("time_epoch")
    @Expose
    private Integer timeEpoch;
    @SerializedName("time")
    @Expose
    private String time;
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
    private HourCondition condition;
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
    @SerializedName("windchill_c")
    @Expose
    private Double windchillC;
    @SerializedName("windchill_f")
    @Expose
    private Double windchillF;
    @SerializedName("heatindex_c")
    @Expose
    private Double heatindexC;
    @SerializedName("heatindex_f")
    @Expose
    private Double heatindexF;
    @SerializedName("dewpoint_c")
    @Expose
    private Double dewpointC;
    @SerializedName("dewpoint_f")
    @Expose
    private Double dewpointF;
    @SerializedName("will_it_rain")
    @Expose
    private Integer willItRain;
    @SerializedName("will_it_snow")
    @Expose
    private Integer willItSnow;


    public Integer getTimeEpoch() {
        return timeEpoch;
    }

    public String getTime() {
        return time;
    }

    public Double getTempC() {
        return tempC;
    }

    public Double getTempF() {
        return tempF;
    }

    public Integer getIsDay() {
        return isDay;
    }

    public HourCondition getCondition() {
        return condition;
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

    public Double getWindchillC() {
        return windchillC;
    }

    public Double getWindchillF() {
        return windchillF;
    }

    public Double getHeatindexC() {
        return heatindexC;
    }

    public Double getHeatindexF() {
        return heatindexF;
    }

    public Double getDewpointC() {
        return dewpointC;
    }

    public Double getDewpointF() {
        return dewpointF;
    }

    public Integer getWillItRain() {
        return willItRain;
    }

    public Integer getWillItSnow() {
        return willItSnow;
    }
}
