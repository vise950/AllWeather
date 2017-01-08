package com.dev.nicola.allweather.provider.Yahoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Atmosphere {

    @SerializedName("humidity")
    @Expose
    private String humidity;
    @SerializedName("pressure")
    @Expose
    private String pressure;
    @SerializedName("rising")
    @Expose
    private String rising;
    @SerializedName("visibility")
    @Expose
    private String visibility;


    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getRising() {
        return rising;
    }

    public String getVisibility() {
        return visibility;
    }

}
