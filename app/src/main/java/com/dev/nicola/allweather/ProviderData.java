package com.dev.nicola.allweather;

import android.content.Context;
import android.content.res.Resources;

import com.dev.nicola.allweather.model.ForecastDay;
import com.dev.nicola.allweather.model.ForecastHour;
import com.dev.nicola.allweather.provider.Apixu.model.ApixuData;
import com.dev.nicola.allweather.provider.ForecastIO.model.ForecastIOData;
import com.dev.nicola.allweather.provider.ProviderRequest;
import com.dev.nicola.allweather.utils.ImageUtils;
import com.dev.nicola.allweather.utils.LocationUtils;
import com.dev.nicola.allweather.utils.PreferencesUtils;
import com.dev.nicola.allweather.utils.TimeUtils;
import com.dev.nicola.allweather.utils.UnitConverterUtils;
import com.dev.nicola.allweather.utils.WeatherUtils;
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
    private Context mContext;
    private ForecastIOData mForecastIOData;
    private ApixuData mApixuData;
    private String location;
    private String image;
    private String condition;
    private String temperature;
    private String wind;
    private String humidity;
    private String pressure;
    private String sunrise;
    private String sunset;
    private List<ForecastDay> mForecastDayList;
    private List<ForecastHour> mForecastHourList;
    private String tempUnits;
    private String windUnits;
    private String timeUnits;

    public ProviderData(Context context, Resources resources) {
        this.mContext = context;
        this.mResources = resources;
        tempUnits = PreferencesUtils.getPreferences(context, resources.getString(R.string.key_pref_temperature), resources.getString(R.string.default_pref_temperature));
        windUnits = PreferencesUtils.getPreferences(context, resources.getString(R.string.key_pref_speed), resources.getString(R.string.default_pref_speed));
        timeUnits = PreferencesUtils.getPreferences(context, resources.getString(R.string.key_pref_time), resources.getString(R.string.default_pref_time));
    }

    public JSONObject getProviderData(String provider, double latitude, double longitude, String location) {
        ProviderRequest providerRequest = new ProviderRequest();
        return providerRequest.getData(provider, latitude, longitude);
    }


    public void elaborateData(String provider, String argument) {
        Gson gson = new GsonBuilder().create();

        switch (provider) {

            case "forecastIO":
                mForecastIOData = new ForecastIOData();
                mForecastIOData = gson.fromJson(argument, ForecastIOData.class);
                pullDailyData(provider);
                pullForecastHourData(provider);
                pullForecastDayData(provider);
                break;

            case "apixu":
                mApixuData = new ApixuData();
                mApixuData = gson.fromJson(argument, ApixuData.class);
                pullDailyData(provider);
                pullForecastHourData(provider);
                pullForecastDayData(provider);
                break;
        }
    }

    public void pullDailyData(String provider) {

        switch (provider) {

            case "forecastIO":
                location = LocationUtils.getLocationName(mContext,
                        mForecastIOData.getLatitude(),
                        mForecastIOData.getLongitude());
                image = ImageUtils.getImage(mResources,
                        mForecastIOData.getDaily().getData().get(0).getSunriseTime(), mForecastIOData.getDaily().getData().get(0).getSunsetTime(),
                        mForecastIOData.getCurrently().getTime(),
                        null,
                        null);
                condition = mForecastIOData.getCurrently().getSummary();
                temperature = String.format(mResources.getString(R.string.temperature),
                        UnitConverterUtils.CelsiusToFahrenheitOrKelvin(mForecastIOData.getCurrently().getTemperature(), tempUnits));
                wind = String.format(mResources.getString(R.string.wind),
                        WeatherUtils.getWindDirection(mForecastIOData.getCurrently().getWindBearing()),
                        UnitConverterUtils.MsToKmhOrKph(mForecastIOData.getCurrently().getWindSpeed(), windUnits));
                humidity = String.format(mResources.getString(R.string.humidity),
                        mForecastIOData.getCurrently().getHumidity());
                pressure = String.format(mResources.getString(R.string.pressure),
                        mForecastIOData.getCurrently().getPressure());
                sunrise = TimeUtils.getHourFormat(mForecastIOData.getDaily().getData().get(0).getSunriseTime(),
                        null,
                        timeUnits);
                sunset = TimeUtils.getHourFormat(mForecastIOData.getDaily().getData().get(0).getSunsetTime(),
                        null,
                        timeUnits);

                break;

            case "apixu":
                location = LocationUtils.getLocationName(mContext,
                        mApixuData.getLocation().getLat(),
                        mApixuData.getLocation().getLon());
                image = ImageUtils.getImage(mResources,
                        0,
                        0,
                        mApixuData.getLocation().getLocaltimeEpoch(),
                        mApixuData.getForecast().getForecastday().get(0).getAstro().getSunrise(),
                        mApixuData.getForecast().getForecastday().get(0).getAstro().getSunset());
                condition = mApixuData.getCurrent().getCurrentCondition().getText();
                temperature = String.format(mResources.getString(R.string.temperature),
                        UnitConverterUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getCurrent().getTempC(), tempUnits));
                wind = String.format(mResources.getString(R.string.wind),
                        mApixuData.getCurrent().getWindDir(),
                        UnitConverterUtils.MsToKmhOrKph(mApixuData.getCurrent().getWindKph(), windUnits));
                humidity = String.format(mResources.getString(R.string.humidity),
                        String.valueOf(mApixuData.getCurrent().getHumidity()));
                pressure = String.format(mResources.getString(R.string.pressure),
                        String.valueOf(mApixuData.getCurrent().getPressureMb()));
                sunrise = TimeUtils.getHourFormat(0,
                        mApixuData.getForecast().getForecastday().get(0).getAstro().getSunrise(),
                        timeUnits);
                sunset = TimeUtils.getHourFormat(0,
                        mApixuData.getForecast().getForecastday().get(0).getAstro().getSunset(),
                        timeUnits);

                break;
        }
    }

    public void pullForecastHourData(String provider) {
        mForecastHourList = new ArrayList<>();
        ForecastHour forecastHour;

        switch (provider) {

            case "forecastIO":
                for (int i = 1; i < 25; i++) {
                    forecastHour = new ForecastHour(TimeUtils.getHourFormat(mForecastIOData.getHourly().getData().get(i).getTime(), null, timeUnits),
                            WeatherUtils.getWeatherIcon(mForecastIOData.getHourly().getData().get(i).getIcon()),
                            String.format(mResources.getString(R.string.temperature), UnitConverterUtils.CelsiusToFahrenheitOrKelvin(mForecastIOData.getHourly().getData().get(i).getTemperature(), tempUnits)));
                    mForecastHourList.add(forecastHour);
                }
                break;

            case "apixu":
                int indexHour = TimeUtils.getLocalTime();
                int indexDay = 0;
                for (int i = 0; i < 24; i++) {
                    forecastHour = new ForecastHour(TimeUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(indexDay).getHour().get(indexHour).getTimeEpoch(), null, timeUnits),
                            WeatherUtils.getWeatherIcon(mApixuData.getForecast().getForecastday().get(indexDay).getHour().get(indexHour).getCondition().getCode().toString()),
                            String.format(mResources.getString(R.string.temperature), UnitConverterUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(indexDay).getHour().get(indexHour).getTempC().intValue(), tempUnits)));
                    mForecastHourList.add(forecastHour);

                    if (indexHour >= 23) {
                        indexHour = 0;
                        indexDay++;
                    } else
                        indexHour++;
                }
                break;
        }
    }

    public void pullForecastDayData(String provider) {
        mForecastDayList = new ArrayList<>();
        ForecastDay forecastDay;
        int days;

        switch (provider) {

            case "forecastIO":
                days = mForecastIOData.getDaily().getData().size();
                for (int i = 1; i < days; i++) {
                    forecastDay = new ForecastDay(TimeUtils.getDayFormat(mResources, mForecastIOData.getDaily().getData().get(i).getTime()),
                            mForecastIOData.getDaily().getData().get(i).getSummary(),
                            UnitConverterUtils.CelsiusToFahrenheitOrKelvin((mForecastIOData.getDaily().getData().get(i).getTemperatureMin() + mForecastIOData.getDaily().getData().get(i).getTemperatureMax()) / 2, tempUnits),
                            WeatherUtils.getWeatherIcon(mForecastIOData.getDaily().getData().get(i).getIcon()));
                    mForecastDayList.add(forecastDay);
                }
                break;

            case "apixu":
                days = mApixuData.getForecast().getForecastday().size();
                for (int i = 1; i < days; i++) {
                    forecastDay = new ForecastDay(TimeUtils.getDayFormat(mResources, mApixuData.getForecast().getForecastday().get(i).getDateEpoch()),
                            mApixuData.getForecast().getForecastday().get(i).getDay().getCondition().getText(),
                            UnitConverterUtils.CelsiusToFahrenheitOrKelvin(mApixuData.getForecast().getForecastday().get(i).getDay().getAvgtempC(), tempUnits),
                            WeatherUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(i).getDay().getCondition().getCode())));
                    mForecastDayList.add(forecastDay);
                }
                break;
        }
    }


    public String getLocation() {
        return location;
    }

    public String getImage() {
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

    public String getPressure() {
        return pressure;
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
