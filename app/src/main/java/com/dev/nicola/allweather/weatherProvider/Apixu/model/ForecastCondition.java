package com.dev.nicola.allweather.weatherProvider.Apixu.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastCondition {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("code")
    @Expose
    private Integer code;


    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getCode() {
        return code;
    }
}
