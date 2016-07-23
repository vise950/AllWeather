package com.dev.nicola.allweather.Util;

import android.content.Context;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dev.nicola.allweather.Provider.Apixu.ApixuData;
import com.dev.nicola.allweather.Provider.Apixu.ApixuRequest;
import com.dev.nicola.allweather.Provider.ForecastIO.ForecastIOData;
import com.dev.nicola.allweather.Provider.ForecastIO.ForecastIORequest;
import com.dev.nicola.allweather.Provider.Yahoo.YahooRequest;
import com.dev.nicola.allweather.R;
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

    private Context mContext;
    private Resources mResources;

    private ForecastIORequest mForecastIORequest;
    private ApixuRequest mApixuRequest;

    private ForecastIOData mForecastIOData;
    private ApixuData mApixuData;

    private YahooRequest mYahooRequest;

    private Gson mGson;
    private Utils mUtils;
    private UnitsConverter mConverter;

    private String location;
    private int image;
    private String condition;
    private String temperature;
    private String wind;
    private String humidity;
    private String sunrise;
    private String sunset;
    private String firstHour;
    private int firstIcon;
    private String firstTemperature;
    private String secondHour;
    private int secondIcon;
    private String secondTemperature;
    private String thirdHour;
    private int thirdIcon;
    private String thirdTemperature;
    private String forthHour;
    private int forthIcon;
    private String forthTemperature;
    private List<Forecast> ForecastList;

    public ProviderData(Context context, Resources resources) {
        this.mContext = context;
        this.mResources = resources;
        mUtils = new Utils(context, resources);
        mConverter = new UnitsConverter(context);
    }

    public JSONObject getProviderCall(String provider, double latitude, double longitude, String location) {
        JSONObject jsonObject = null;
        String url = setUrl(provider, latitude, longitude, location);

        switch (provider) {

            case "ForecastIO":
                jsonObject = mForecastIORequest.getData(url);
                break;

            case "Apixu":
                jsonObject = mApixuRequest.getData(url);
                break;

            case "Yahoo":
                jsonObject = mYahooRequest.getData(url);
                break;

        }
        return jsonObject;
    }


    private String setUrl(String provider, double latitude, double longitude, String location) {
        String url = null;

        switch (provider) {

            case "ForecastIO":
                mForecastIORequest = new ForecastIORequest();
                url = mForecastIORequest.setUrl(latitude, longitude);
                break;

            case "Apixu":
                mApixuRequest = new ApixuRequest();
                url = mApixuRequest.setUrl(latitude, longitude);
                break;

            case "Yahoo":
                mYahooRequest = new YahooRequest();
                url = mYahooRequest.setUrl(location);
                break;
        }
        return url;
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
        String tempUnits = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mResources.getString(R.string.key_pref_temperature), "1");
        String windUnits = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mResources.getString(R.string.key_pref_speed), "3");
        String timeUnits = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mResources.getString(R.string.key_pref_time), "2");
        switch (provider) {

            case "ForecastIO":
                location = mUtils.getLocationName(mForecastIOData.getLatitude(), mForecastIOData.getLongitude());
                image = mUtils.getImage(mForecastIOData.getDaily().getData().get(0).getSunriseTime(), mForecastIOData.getDaily().getData().get(0).getSunsetTime(), mForecastIOData.getCurrently().getTime());
                condition = mForecastIOData.getCurrently().getSummary();
                humidity = String.format(mResources.getString(R.string.humidity), mForecastIOData.getCurrently().getHumidity());

                firstIcon = mUtils.getWeatherIcon(mForecastIOData.getHourly().getData().get(2).getIcon());
                secondIcon = mUtils.getWeatherIcon(mForecastIOData.getHourly().getData().get(4).getIcon());
                thirdIcon = mUtils.getWeatherIcon(mForecastIOData.getHourly().getData().get(6).getIcon());
                forthIcon = mUtils.getWeatherIcon(mForecastIOData.getHourly().getData().get(8).getIcon());

                switch (tempUnits) {
                    case "2":
                        temperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToFahrenheit(mForecastIOData.getCurrently().getTemperature()));
                        firstTemperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToFahrenheit(mForecastIOData.getHourly().getData().get(2).getTemperature()));
                        secondTemperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToFahrenheit(mForecastIOData.getHourly().getData().get(4).getTemperature()));
                        thirdTemperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToFahrenheit(mForecastIOData.getHourly().getData().get(6).getTemperature()));
                        forthTemperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToFahrenheit(mForecastIOData.getHourly().getData().get(8).getTemperature()));
                        break;

                    case "3":
                        temperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToKelvin(mForecastIOData.getCurrently().getTemperature()));
                        firstTemperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToKelvin(mForecastIOData.getHourly().getData().get(2).getTemperature()));
                        secondTemperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToKelvin(mForecastIOData.getHourly().getData().get(4).getTemperature()));
                        thirdTemperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToKelvin(mForecastIOData.getHourly().getData().get(6).getTemperature()));
                        forthTemperature = String.format(mResources.getString(R.string.temperature), mConverter.CelsiusToKelvin(mForecastIOData.getHourly().getData().get(8).getTemperature()));
                        break;

                    default:
                        temperature = String.format(mResources.getString(R.string.temperature), mForecastIOData.getCurrently().getTemperature());
                        firstTemperature = String.format(mResources.getString(R.string.temperature), mForecastIOData.getHourly().getData().get(2).getTemperature());
                        secondTemperature = String.format(mResources.getString(R.string.temperature), mForecastIOData.getHourly().getData().get(4).getTemperature());
                        thirdTemperature = String.format(mResources.getString(R.string.temperature), mForecastIOData.getHourly().getData().get(6).getTemperature());
                        forthTemperature = String.format(mResources.getString(R.string.temperature), mForecastIOData.getHourly().getData().get(8).getTemperature());
                        break;
                }

                switch (windUnits) {
                    case "1":
                        wind = String.format(mResources.getString(R.string.wind), mUtils.getWindDirection(mForecastIOData.getCurrently().getWindBearing()), mConverter.MsToKph(mForecastIOData.getCurrently().getWindSpeed()));
                        break;

                    case "2":
                        wind = String.format(mResources.getString(R.string.wind), mUtils.getWindDirection(mForecastIOData.getCurrently().getWindBearing()), mConverter.MsToKmh(mForecastIOData.getCurrently().getWindSpeed()));
                        break;

                    default:
                        wind = String.format(mResources.getString(R.string.wind), mUtils.getWindDirection(mForecastIOData.getCurrently().getWindBearing()), mForecastIOData.getCurrently().getWindSpeed() + " m/s");
                        break;
                }

                switch (timeUnits) {
                    case "1":
                        sunrise = mUtils.getHourFormat(mForecastIOData.getDaily().getData().get(0).getSunriseTime(), mForecastIOData.getTimezone(), 1);
                        sunset = mUtils.getHourFormat(mForecastIOData.getDaily().getData().get(0).getSunsetTime(), mForecastIOData.getTimezone(), 1);
                        firstHour = mUtils.getHourFormat(mForecastIOData.getHourly().getData().get(2).getTime(), mForecastIOData.getTimezone(), 1);
                        secondHour = mUtils.getHourFormat(mForecastIOData.getHourly().getData().get(4).getTime(), mForecastIOData.getTimezone(), 1);
                        thirdHour = mUtils.getHourFormat(mForecastIOData.getHourly().getData().get(6).getTime(), mForecastIOData.getTimezone(), 1);
                        forthHour = mUtils.getHourFormat(mForecastIOData.getHourly().getData().get(8).getTime(), mForecastIOData.getTimezone(), 1);
                        break;

                    default:
                        sunrise = mUtils.getHourFormat(mForecastIOData.getDaily().getData().get(0).getSunriseTime(), mForecastIOData.getTimezone(), 0);
                        sunset = mUtils.getHourFormat(mForecastIOData.getDaily().getData().get(0).getSunsetTime(), mForecastIOData.getTimezone(), 0);
                        firstHour = mUtils.getHourFormat(mForecastIOData.getHourly().getData().get(2).getTime(), mForecastIOData.getTimezone(), 0);
                        secondHour = mUtils.getHourFormat(mForecastIOData.getHourly().getData().get(4).getTime(), mForecastIOData.getTimezone(), 0);
                        thirdHour = mUtils.getHourFormat(mForecastIOData.getHourly().getData().get(6).getTime(), mForecastIOData.getTimezone(), 0);
                        forthHour = mUtils.getHourFormat(mForecastIOData.getHourly().getData().get(8).getTime(), mForecastIOData.getTimezone(), 0);
                        break;
                }

                break;

            case "Apixu":
                location = mUtils.getLocationName(mApixuData.getLocation().getLat(), mApixuData.getLocation().getLon());
//                image=mUtils.getImage(mApixuData.getForecast().getForecastday().get(0).getAstro().getSunrise(),mApixuData.getForecast().getForecastday().get(0).getAstro().getSunset(),mApixuData.getLocation().getLocaltimeEpoch());
                condition = mApixuData.getCurrent().getCurrentCondition().getText();
                temperature = String.format(mResources.getString(R.string.temperature), mApixuData.getCurrent().getTempC());
                wind = String.format(mResources.getString(R.string.wind), mApixuData.getCurrent().getWindDir(), mApixuData.getCurrent().getWindKph());
                humidity = String.valueOf(mApixuData.getCurrent().getHumidity());
                sunrise = mApixuData.getForecast().getForecastday().get(0).getAstro().getSunrise();
                sunset = mApixuData.getForecast().getForecastday().get(0).getAstro().getSunset();

                int index = mUtils.getLocalTime();
                Log.d(TAG, "index " + mUtils.getLocalTime());
                firstHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTimeEpoch(), mApixuData.getLocation().getTzId(), 0);
                firstIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getCondition().getCode()));
                firstTemperature = String.format(mResources.getString(R.string.temperature), mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTempC());

                index += 2;
                if (index <= 23) {
                    secondHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTimeEpoch(), mApixuData.getLocation().getTzId(), 0);
                    secondIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getCondition().getCode()));
                    secondTemperature = String.format(mResources.getString(R.string.temperature), mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTempC());
                } else {
                    if (index >= 24)
                        index = 1;
                    else
                        index = 0;
                    secondHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTimeEpoch(), mApixuData.getLocation().getTzId(), 0);
                    secondIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getCondition().getCode()));
                    secondTemperature = String.format(mResources.getString(R.string.temperature), mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTempC());
                }

                index += 2;
                if (index <= 23) {
                    thirdHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTimeEpoch(), mApixuData.getLocation().getTzId(), 0);
                    thirdIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getCondition().getCode()));
                    thirdTemperature = String.format(mResources.getString(R.string.temperature), mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTempC());
                } else {
                    if (index >= 24)
                        index = 1;
                    else
                        index = 0;
                    thirdHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTimeEpoch(), mApixuData.getLocation().getTzId(), 0);
                    thirdIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getCondition().getCode()));
                    thirdTemperature = String.format(mResources.getString(R.string.temperature), mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTempC());
                }

                index += 2;
                if (index <= 23) {
                    forthHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTimeEpoch(), mApixuData.getLocation().getTzId(), 0);
                    forthIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getCondition().getCode()));
                    forthTemperature = String.format(mResources.getString(R.string.temperature), mApixuData.getForecast().getForecastday().get(0).getHour().get(index).getTempC());
                } else {
                    if (index >= 24)
                        index = 1;
                    else
                        index = 0;
                    forthHour = mUtils.getHourFormat(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTimeEpoch(), mApixuData.getLocation().getTzId(), 0);
                    forthIcon = mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getCondition().getCode()));
                    forthTemperature = String.format(mResources.getString(R.string.temperature), mApixuData.getForecast().getForecastday().get(1).getHour().get(index).getTempC());
                }

                break;
        }
    }

    public void pullForecastData(String provider) {
        ForecastList = new ArrayList<>();
        Forecast forecast;
        int days;

        switch (provider) {

            case "ForecastIO":
                days = mForecastIOData.getDaily().getData().size();
                for (int i = 1; i < days; i++) {
                    forecast = new Forecast(mUtils.getDayFormat(mForecastIOData.getDaily().getData().get(i).getTime())
                            , mForecastIOData.getDaily().getData().get(i).getSummary()
                            , (int) (mForecastIOData.getDaily().getData().get(i).getTemperatureMin() + mForecastIOData.getDaily().getData().get(i).getTemperatureMax()) / 2
                            , mUtils.getWeatherIcon(mForecastIOData.getDaily().getData().get(i).getIcon()));
                    ForecastList.add(forecast);
                }
                break;

            case "Apixu":
                days = mApixuData.getForecast().getForecastday().size();
                for (int i = 1; i < days; i++) {
                    forecast = new Forecast(mUtils.getDayFormat(mApixuData.getForecast().getForecastday().get(i).getDateEpoch())
                            , mApixuData.getForecast().getForecastday().get(i).getDay().getCondition().getText()
                            , mApixuData.getForecast().getForecastday().get(i).getDay().getAvgtempC()
                            , mUtils.getWeatherIcon(String.valueOf(mApixuData.getForecast().getForecastday().get(i).getDay().getCondition().getCode())));
                    ForecastList.add(forecast);
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

    public String getFirstHour() {
        return firstHour;
    }

    public int getFirstIcon() {
        return firstIcon;
    }

    public String getFirstTemperature() {
        return firstTemperature;
    }

    public String getSecondHour() {
        return secondHour;
    }

    public int getSecondIcon() {
        return secondIcon;
    }

    public String getSecondTemperature() {
        return secondTemperature;
    }

    public String getThirdHour() {
        return thirdHour;
    }

    public int getThirdIcon() {
        return thirdIcon;
    }

    public String getThirdTemperature() {
        return thirdTemperature;
    }

    public String getForthHour() {
        return forthHour;
    }

    public int getForthIcon() {
        return forthIcon;
    }

    public String getForthTemperature() {
        return forthTemperature;
    }

    public List<Forecast> getForecastList() {
        return ForecastList;
    }
}
