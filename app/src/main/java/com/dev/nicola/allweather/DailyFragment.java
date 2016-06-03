package com.dev.nicola.allweather;

import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.nicola.allweather.Provider.ForecastIO.ForecastIOData;
import com.dev.nicola.allweather.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Nicola on 24/03/2016.
 */
public class DailyFragment extends Fragment {

    private static String TAG = DailyFragment.class.getSimpleName();

    private TextView location;
    private ImageView weatherIcon;
    private TextView condition;
    private TextView temperature;
    private TextView wind;
    private TextView humidity;
    private TextView sunrise;
    private TextView sunset;

    private TextView firstHour;
    private ImageView firstIcon;
    private TextView firstTemperature;
    private TextView secondHour;
    private ImageView secondIcon;
    private TextView secondTemperature;
    private TextView thirdHour;
    private ImageView thirdIcon;
    private TextView thirdTemperature;
    private TextView forthHour;
    private ImageView forthIcon;
    private TextView forthTemperature;

    private Utils mUtils;
    private ForecastIOData mData;
    private Gson mGson;
    private Resources mResources;

    private String argument;


    public static DailyFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString("ARGUMENT", argument);
        DailyFragment dailyFragment = new DailyFragment();
        dailyFragment.setArguments(bundle);
        return dailyFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        argument = getArguments().getString("ARGUMENT");

        mUtils = new Utils(getContext(), getResources());
        mData = new ForecastIOData();
        mGson = new GsonBuilder().create();
        mResources = getResources();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_fragment, container, false);

        mData = mGson.fromJson(argument, ForecastIOData.class);

        setUpLayout(view);
        setText();

        return view;
    }


    private void setUpLayout(View v) {
        location = (TextView) v.findViewById(R.id.location_daily_fragment);
//        weatherIcon = (ImageView) v.findViewById(R.id.imageDaily);
        condition = (TextView) v.findViewById(R.id.condition_daily_fragment);
        temperature = (TextView) v.findViewById(R.id.temperature);
        wind = (TextView) v.findViewById(R.id.wind);
        humidity = (TextView) v.findViewById(R.id.humidity);
        sunrise = (TextView) v.findViewById(R.id.sunrise);
        sunset = (TextView) v.findViewById(R.id.sunset);

        firstHour = (TextView) v.findViewById(R.id.first_hour_forecast_daily);
        firstIcon = (ImageView) v.findViewById(R.id.first_icon_forecast_daily);
        firstTemperature = (TextView) v.findViewById(R.id.first_temperature_forecast_daily);

        secondHour = (TextView) v.findViewById(R.id.second_hour_forecast_daily);
        secondIcon = (ImageView) v.findViewById(R.id.second_icon_forecast_daily);
        secondTemperature = (TextView) v.findViewById(R.id.second_temperature_forecast_daily);

        thirdHour = (TextView) v.findViewById(R.id.third_hour_forecast_daily);
        thirdIcon = (ImageView) v.findViewById(R.id.third_icon_forecast_daily);
        thirdTemperature = (TextView) v.findViewById(R.id.third_temperature_forecast_daily);

        forthHour = (TextView) v.findViewById(R.id.forth_hour_forecast_daily);
        forthIcon = (ImageView) v.findViewById(R.id.forth_icon_forecast_daily);
        forthTemperature = (TextView) v.findViewById(R.id.forth_temperature_forecast_daily);
    }


    private void setText() {
        location.setText(mUtils.getLocationName(mData.getLatitude(), mData.getLongitude()));
//        weatherIcon.setImageResource(mUtils.getIcon(mOpenWeatherMap.getDailyIcon()));
        condition.setText(String.valueOf(mData.getCurrently().getSummary()));
        temperature.setText(String.format(mResources.getString(R.string.temperature), mData.getCurrently().getTemperature()));
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getString("systemUnit", "1").equals("1"))
            wind.setText(String.format(mResources.getString(R.string.wind_metrical), mUtils.getWindDirection(mData.getCurrently().getWindBearing()), mData.getCurrently().getWindSpeed()));
        else
            wind.setText(String.format(mResources.getString(R.string.wind_imperial), mUtils.getWindDirection(mData.getCurrently().getWindBearing()), mData.getCurrently().getWindSpeed()));
        humidity.setText(String.format(mResources.getString(R.string.humidity), mData.getCurrently().getHumidity()));
        sunrise.setText(mUtils.getHourFormat(mData.getDaily().getData().get(0).getSunriseTime()));
        sunset.setText(mUtils.getHourFormat(mData.getDaily().getData().get(0).getSunsetTime()));

        firstHour.setText(mUtils.getHourFormat(mData.getHourly().getData().get(2).getTime()));
        firstIcon.setImageResource(mUtils.getIcon(mData.getHourly().getData().get(2).getIcon()));
        firstTemperature.setText(String.format(mResources.getString(R.string.temperature), mData.getHourly().getData().get(2).getTemperature()));

        secondHour.setText(mUtils.getHourFormat(mData.getHourly().getData().get(4).getTime()));
        secondIcon.setImageResource(mUtils.getIcon(mData.getHourly().getData().get(4).getIcon()));
        secondTemperature.setText(String.format(mResources.getString(R.string.temperature), mData.getHourly().getData().get(4).getTemperature()));

        thirdHour.setText(mUtils.getHourFormat(mData.getHourly().getData().get(6).getTime()));
        thirdIcon.setImageResource(mUtils.getIcon(mData.getHourly().getData().get(6).getIcon()));
        thirdTemperature.setText(String.format(mResources.getString(R.string.temperature), mData.getHourly().getData().get(6).getTemperature()));

        forthHour.setText(mUtils.getHourFormat(mData.getHourly().getData().get(8).getTime()));
        forthIcon.setImageResource(mUtils.getIcon(mData.getHourly().getData().get(8).getIcon()));
        forthTemperature.setText(String.format(mResources.getString(R.string.temperature), mData.getHourly().getData().get(8).getTemperature()));
    }
}
