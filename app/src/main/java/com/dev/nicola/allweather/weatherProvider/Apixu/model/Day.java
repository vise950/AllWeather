package com.dev.nicola.allweather.provider.Apixu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("maxtemp_c")
    @Expose
    private Double maxtempC;
    @SerializedName("maxtemp_f")
    @Expose
    private Double maxtempF;
    @SerializedName("mintemp_c")
    @Expose
    private Double mintempC;
    @SerializedName("mintemp_f")
    @Expose
    private Double mintempF;
    @SerializedName("avgtemp_c")
    @Expose
    private Double avgtempC;
    @SerializedName("avgtemp_f")
    @Expose
    private Double avgtempF;
    @SerializedName("maxwind_mph")
    @Expose
    private Double maxwindMph;
    @SerializedName("maxwind_kph")
    @Expose
    private Double maxwindKph;
    @SerializedName("totalprecip_mm")
    @Expose
    private Double totalprecipMm;
    @SerializedName("totalprecip_in")
    @Expose
    private Double totalprecipIn;
    @SerializedName("condition")
    @Expose
    private ForecastCondition condition;


    public Double getMaxtempC() {
        return maxtempC;
    }

    public Double getMaxtempF() {
        return maxtempF;
    }

    public Double getMintempC() {
        return mintempC;
    }

    public Double getMintempF() {
        return mintempF;
    }

    public Integer getAvgtempC() {
        return avgtempC.intValue();
    }

    public Double getAvgtempF() {
        return avgtempF;
    }

    public Double getMaxwindMph() {
        return maxwindMph;
    }

    public Double getMaxwindKph() {
        return maxwindKph;
    }

    public Double getTotalprecipMm() {
        return totalprecipMm;
    }

    public Double getTotalprecipIn() {
        return totalprecipIn;
    }

    public ForecastCondition getCondition() {
        return condition;
    }
}
