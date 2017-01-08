package com.dev.nicola.allweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.nicola.allweather.adapter.ForecastHourAdapter;
import com.dev.nicola.allweather.model.ForecastHour;
import com.dev.nicola.allweather.utils.PreferencesUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nicola on 24/03/2016.
 */
public class DailyFragment extends Fragment {

    private static String TAG = DailyFragment.class.getSimpleName();

    @BindView(R.id.location_daily_fragment)
    TextView location;
    @BindView(R.id.image_daily_fragment)
    ImageView image;
    @BindView(R.id.condition_daily_fragment)
    TextView condition;
    @BindView(R.id.temperature_daily_fragment)
    TextView temperature;
    @BindView(R.id.wind_daily_fragment)
    TextView wind;
    @BindView(R.id.humidity_daily_fragment)
    TextView humidity;
    @BindView(R.id.pressure_daily_fragment)
    TextView pressure;
    @BindView(R.id.sunrise_daily_fragment)
    TextView sunrise;
    @BindView(R.id.sunset_daily_fragment)
    TextView sunset;
    @BindView(R.id.forecast_hour_recycle_view)
    RecyclerView mRecyclerView;

    private ProviderData mProviderData;
    private String argument;
    private String prefProvider;

    public static DailyFragment newInstance(String argument) {
        Bundle args = new Bundle();
        args.putString("ARGUMENT", argument);
        DailyFragment dailyFragment = new DailyFragment();
        dailyFragment.setArguments(args);
        return dailyFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        argument = getArguments().getString("ARGUMENT");
        argument = PreferencesUtils.getPreferences(getContext(), "lastJSONObject", null);
        mProviderData = new ProviderData(getContext(), getResources());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_fragment, container, false);

        ButterKnife.bind(this, view);

        prefProvider = PreferencesUtils.getDefaultPreferences(getContext(), getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider));

        mProviderData.elaborateData(prefProvider, argument);

        if (!prefProvider.equals("yahoo")) {
            List<ForecastHour> forecastHourList;
            forecastHourList = mProviderData.getForecastHourList();
            ForecastHourAdapter forecastHourAdapter = new ForecastHourAdapter(forecastHourList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(forecastHourAdapter);
        }

        setText();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mRecyclerView != null)
            mRecyclerView.removeAllViews();
    }

    private void setText() {
        location.setText(mProviderData.getLocation());
        Picasso.with(getContext()).load(mProviderData.getImage()).into(image); // FIXME: 18/09/2016 non devo cambiare immagine ad ogni richiesta
        condition.setText(mProviderData.getCondition());
        temperature.setText(mProviderData.getTemperature());
        wind.setText(mProviderData.getWind());
        humidity.setText(mProviderData.getHumidity());
        pressure.setText(mProviderData.getPressure());
        sunrise.setText(mProviderData.getSunrise());
        sunset.setText(mProviderData.getSunset());
    }
}