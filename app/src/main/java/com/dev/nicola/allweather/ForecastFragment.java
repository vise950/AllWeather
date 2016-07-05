package com.dev.nicola.allweather;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.nicola.allweather.Util.DividerItemDecoration;
import com.dev.nicola.allweather.Util.Forecast;
import com.dev.nicola.allweather.Util.ForecastAdapter;
import com.dev.nicola.allweather.Util.ProviderData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicola on 24/03/2016.
 */
public class ForecastFragment extends Fragment {

    private static String TAG = ForecastFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private List<Forecast> mForecastList;

    private ProviderData mProviderData;
    private String argument;
    private String prefProvider;

    public static ForecastFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString("ARGUMENT", argument);
        ForecastFragment forecastFragment = new ForecastFragment();
        forecastFragment.setArguments(bundle);
        return forecastFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        argument = getArguments().getString("ARGUMENT");
        mProviderData = new ProviderData(getContext(), getResources());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forecast_fragment, container, false);

        prefProvider = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("pref_provider", "ForecastIO");
        mProviderData.elaborateData(prefProvider, argument);
        mProviderData.pullForecastData(prefProvider);

        prepareForecastData();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.forecast_recycle_view);
        mForecastAdapter = new ForecastAdapter(mForecastList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(mForecastAdapter);

        return view;
    }

    private void prepareForecastData() {
        mForecastList = new ArrayList<>();
        mForecastList = mProviderData.getForecastList();
    }
}
