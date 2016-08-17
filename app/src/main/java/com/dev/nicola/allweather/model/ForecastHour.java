package com.dev.nicola.allweather.model;

/**
 * Created by Nicola on 14/08/2016.
 */
public class ForecastHour {

    private String hour;
    private int icon;
    private String temperature;

    public ForecastHour(String hour, int icon, String temperature) {
        this.hour = hour;
        this.icon = icon;
        this.temperature = temperature;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
