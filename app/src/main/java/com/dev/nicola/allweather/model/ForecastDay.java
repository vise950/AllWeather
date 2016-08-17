package com.dev.nicola.allweather.model;

/**
 * Created by Nicola on 09/05/2016.
 */
public class ForecastDay {

    private String date;
    private String condition;
    private double temperature;
    private int icon;

    public ForecastDay(String date, String condition, double temperature, int icon) {
        this.date = date;
        this.condition = condition;
        this.temperature = temperature;
        this.icon = icon;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
