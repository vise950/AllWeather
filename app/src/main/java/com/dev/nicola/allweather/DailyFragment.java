package com.dev.nicola.allweather;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.nicola.allweather.Util.ProviderData;

/**
 * Created by Nicola on 24/03/2016.
 */
public class DailyFragment extends Fragment {

    private static String TAG = DailyFragment.class.getSimpleName();

    private TextView location;
    private ImageView image;
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

    private ProviderData mProviderData;
    private String argument;
    private String prefProvider;

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
        mProviderData = new ProviderData(getContext(), getResources());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_fragment, container, false);

        prefProvider = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getResources().getString(R.string.key_pref_provider), "ForecastIO");

        mProviderData.elaborateData(prefProvider, argument);
        mProviderData.pullDailyData(prefProvider);

        setUpLayout(view);
        setText();

        return view;

    }


    private void setUpLayout(View v) {
        location = (TextView) v.findViewById(R.id.location_daily_fragment);
        image = (ImageView) v.findViewById(R.id.image_daily_fragment);
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
        location.setText(mProviderData.getLocation());
        image.setImageResource(mProviderData.getImage());
        condition.setText(mProviderData.getCondition());
        temperature.setText(mProviderData.getTemperature());
        wind.setText(mProviderData.getWind());
        humidity.setText(mProviderData.getHumidity());
        sunrise.setText(mProviderData.getSunrise());
        sunset.setText(mProviderData.getSunset());

        firstHour.setText(mProviderData.getFirstHour());
        firstIcon.setImageResource(mProviderData.getFirstIcon());
        firstTemperature.setText(mProviderData.getFirstTemperature());

        secondHour.setText(mProviderData.getSecondHour());
        secondIcon.setImageResource(mProviderData.getSecondIcon());
        secondTemperature.setText(mProviderData.getSecondTemperature());

        thirdHour.setText(mProviderData.getThirdHour());
        thirdIcon.setImageResource(mProviderData.getThirdIcon());
        thirdTemperature.setText(mProviderData.getThirdTemperature());

        forthHour.setText(mProviderData.getForthHour());
        forthIcon.setImageResource(mProviderData.getForthIcon());
        forthTemperature.setText(mProviderData.getForthTemperature());
    }
}
