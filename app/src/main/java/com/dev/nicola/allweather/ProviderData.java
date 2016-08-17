package com.dev.nicola.allweather;

import android.content.Context;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.dev.nicola.allweather.model.ForecastDay;
import com.dev.nicola.allweather.model.ForecastHour;
import com.dev.nicola.allweather.provider.Apixu.model.ApixuData;
import com.dev.nicola.allweather.provider.ForecastIO.model.ForecastIOData;
import com.dev.nicola.allweather.provider.ProviderRequest;
import com.dev.nicola.allweather.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicola on 07/06/2016.
 */
public class ProviderData {

    private static String TAG = ProviderData.class.getSimpleName();

    private Resources mResources;

    private ProviderRequest mProviderRequest;
    private ForecastIOData mForecastIOData;
    private ApixuData mApixuData;

    private Gson mGson;
    private Utils mUtils;

    private String location;
    private int image;
    private String condition;
    private String temperature;
    private String wind;
    private String humidity;
    private String sunrise;
    private String sunset;
    private List<ForecastDay> mForecastDayList;
    private List<ForecastHour> mForecastHourList;

    private String tempUnits;
    private String windUnits;
    private String timeUnits;

    public ProviderData(Context context, Resources resources) {
        this.mResources = resources;
        mUtils = new Utils(context, resources);
        tempUnits = PreferenceManager.getDefaultSharedPreferences(context).getString(resources.getString(R.string.key_pref_temperature), "1");
        windUnits = PreferenceManager.getDefaultSharedPreferences(context).getString(resources.getString(R.string.key_pref_speed), "3");
        timeUnits = PreferenceManager.getDefaultSharedPreferences(context).getString(resources.getString(R.string.key_pref_time), "2");
    }

    public JSONObject getProviderData(String provider, double latitude, double longitude, String location) {
        mProviderRequest = new ProviderRequest();
        return mProviderRequest.getData(provider, latitude, longitude);
    }


    public void elaborateData(String provider, String argument) {
        mGson = new GsonBuilder().create();

        switch (provider) {

            case "ForecastIO":
                mForecastIOData = new ForecastIOData();
                mForecastIOData = mGson.fromJson(argument, ForecastIOData.class);
                break;

            case "Apixu":
                mApixuData = new ApixuData();
                mApixuData = mGson.fromJson(argument, ApixuData.class);
                break;
        }
    }

    public void pullDailyData(String provider) {

        switch (provider) {

            case "ForecastIO":
                location = mUtils.getLocationName(mForecastIOData.getLatitude(), mForecastIOData.getLongitude());
                image = mUtils.getImage(mForecastIOData.getDaily().getData().get(0).getSunriseTime(), mForecastIOData.getDaily().getData().get(0).getSunsetTime(), mForecastIOData.getCurrently().getTime(), null, null);
                condition = mForecastIOData.getCurrently().getSummary();
                temperature = String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mForecastIOData.getCurrently().getTemperature(), tempUnits));
                wind = String.format(mResources.getString(R.string.wind), mUtils.getWindDirection(mForecastIOData.getCurrently().getWindBearing()), mUtils.MsToKmhOrKph(mForecastIOData.getCurrently().getWindSpeed(), windUnits));
                humidity = String.format(mResources.getString(R.string.humidity), mForecastIOData.getCurrently().getHumidity());
                sunrise = mUtils.getHourFormat(mForecastIOData.getDaily().getData().get(0).getSunriseTime(), null, timeUnits);
                sunset = mUtils.getHourFormat(mForecastIOData.getDaily().getData().get(0).getSunsetTime(), null, timeUnits);

                break;

            case "Apixu":
                location = mUtils.getLocationName(mApixuData.getLocation().getLat(), mApixuData.getLocation().getLon());
                image = mUtils.getImage(0, 0, mApixuData.getLocation().getLocaltimeEpoch(), mApixuData.getForecast().getForecastday().get(0).getAstro().getSunrise(), mApixuData.getForecast().getForecastday().get(0).getAstro().getSunset());
                condition = mApixuData.getCurrent().getCurrentCondition().getText();
                temperature = String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getCurrent().getTempC(), tempUnits));
                wind = String.format(mResources.getString(R.string.wind), mApixuData.getCurrent().getWindDir(), mUtils.MsToKmhOrKph(mApixuData.getCurrent().getWindKph(), windUnits));
                humidity = String.format(mResources.getString(R.string.humidity), String.valueOf(mApixuData.getCurrent().getHumidity()));
                sunrise = mUtils.getHourFormat(0, mApixuData.getForecast().getForecastday().get(0).getAstro().getSunrise(), timeUnits);
                sunset = mUtils.getHourFormat(0, mApixuData.getForecast().getForecastday().get(0).getAstro().getSunset(), timeUnits);

//                int index = mUtils.getLocalTime();
//                firstHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTimeEpoch(), null, timeUnits);
//                firstIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getCondition().getCode()));
//                firstTemperature = String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTempC().intValue(), tempUnits));
//
//                index += 2;
//                if (index <= 23) {
//                    secondHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTimeEpoch(), null, timeUnits);
//                    secondIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getCondition().getCode()));
//                    secondTemperature = String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTempC().intValue(), tempUnits));
//                } else {
//                    if (index >= 24)
//                        index = 1;
//                    else
//                        index = 0;
//                    secondHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTimeEpoch(), null, timeUnits);
//                    secondIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getCondition().getCode()));
//                    secondTemperature = String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTempC().intValue(), tempUnits));
//                }
//
//                index += 2;
//                if (index <= 23) {
//                    thirdHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTimeEpoch(), null, timeUnits);
//                    thirdIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getCondition().getCode()));
//                    thirdTemperature = String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTempC().intValue(), tempUnits));
//                } else {
//                    if (index >= 24)
//                        index = 1;
//                    else
//                        index = 0;
//                    thirdHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTimeEpoch(), null, timeUnits);
//                    thirdIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getCondition().getCode()));
//                    thirdTemperature = String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTempC().intValue(), tempUnits));
//                }
//
//                index += 2;
//                if (index <= 23) {
//                    forthHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTimeEpoch(), null, timeUnits);
//                    forthIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getCondition().getCode()));
//                    forthTemperature = String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTempC().intValue(), tempUnits));
//                } else {
//                    if (index >= 24)
//                        index = 1;
//                    else
//                        index = 0;
//                    forthHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTimeEpoch(), null, timeUnits);
//                    forthIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getCondition().getCode()));
//                    forthTemperature = String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTempC().intValue(), tempUnits));
//                }

                break;
        }
    }

    public void pullForecastHourData(String provider) {
        mForecastHourList = new ArrayList<>();
        ForecastHour forecastHour;

        switch (provider) {

            case "ForecastIO":
                for (int i = 1; i < 25; i++) {
                    forecastHour = new ForecastHour(mUtils.getHourFormat(mForecastIOData.getHourly().getData().get(i).getTime(), null, timeUnits)
                            , mUtils.getWeatherIcon(mForecastIOData.getHourly().getData().get(i).getIcon())
                            , String.format(mResources.getString(R.string.temperature), mUtils.CelsiusToFahrenheitOrKelvin(mForecastIOData.getHourly().getData().get(i).getTemperature(), tempUnits)));
                    mForecastHourList.add(forecastHour);
                }
                break;

            case "Apixu":
                break;
        }
    }

    public void pullForecastDayData(String provider) {
        mForecastDayList = new ArrayList<>();
        ForecastDay forecastDay;
        int days;

        switch (provider) {

            case "ForecastIO":
                days = mForecastIOData.getDaily().getData().size();
                for (int i = 1; i < days; i++) {
                    forecastDay = new ForecastDay(mUtils.getDayFormat(mForecastIOData.getDaily().getData().get(i).getTime())
                            , mForecastIOData.getDaily().getData().get(i).getSummary()
                            , mUtils.CelsiusToFahrenheitOrKelvin((mForecastIOData.getDaily().getData().get(i).getTemperatureMin() + mForecastIOData.getDaily().getData().get(i).getTemperatureMax()) / 2, tempUnits)
                            , mUtils.getWeatherIcon(mForecastIOData.getDaily().getData().get(i).getIcon()));
                    mForecastDayList.add(forecastDay);
                }
                break;

            case "Apixu":
                days = mApixuData.getForecast().getForecastday().size();
                for (int i = 1; i < days; i++) {
                    forecastDay = new ForecastDay(mUtils.getDayFormat(mApixuData.getForecast().getForecastday().get(i).getDateEpoch())
                            , mApixuData.getForecast().getForecastday().get(i).getDay().getCondition().getText()
                            , mUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(i).getDay().getAvgtempC(), tempUnits)
                            , mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(i).getDay().getCondition().getCode())));
                    mForecastDayList.add(forecastDay);
                }
                break;
        }
    }


    public String getLocation() {
        return location;
    }

    public int getImage() {
        return image;
    }

    public String getCondition() {
        return condition;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWind() {
        return wind;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public List<ForecastDay> getForecastDayList() {
        return mForecastDayList;
    }

    public List<ForecastHour> getForecastHourList() {
        return mForecastHourList;
    }

}
