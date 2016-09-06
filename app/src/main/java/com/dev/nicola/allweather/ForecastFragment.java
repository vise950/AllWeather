package com.dev.nicola.allweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.nicola.allweather.adapter.ForecastDayAdapter;
import com.dev.nicola.allweather.model.ForecastDay;
import com.dev.nicola.allweather.utils.DividerItemDecoration;
import com.dev.nicola.allweather.utils.PreferencesUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nicola on 24/03/2016.
 */
public class ForecastFragment extends Fragment {

    private static String TAG = ForecastFragment.class.getSimpleName();

    @BindView(R.id.forecast_day_recycle_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.adView)
    AdView mAdView;
    private ProviderData mProviderData;
    private String argument;

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
        MobileAds.initialize(getContext(), "ca-app-pub-5053914526798733~3075057204");
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mAdView != null)
            mAdView.pause();
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mAdView != null)
            mAdView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mAdView != null)
            mAdView.destroy();

        if (mRecyclerView != null)
            mRecyclerView.removeAllViews();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forecast_fragment, container, false);

        ButterKnife.bind(this, view);

        String prefProvider = PreferencesUtils.getPreferences(getContext(), getResources().getString(R.string.key_pref_provider), getResources().getString(R.string.default_pref_provider));

        mProviderData.elaborateData(prefProvider, argument);

        List<ForecastDay> forecastDayList;
        forecastDayList = mProviderData.getForecastDayList();

        ForecastDayAdapter forecastDayAdapter = new ForecastDayAdapter(forecastDayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setAdapter(forecastDayAdapter);

        argument = null;

        AdRequest adRequest = new AdRequest.Builder().build();
        if (mAdView != null)
            mAdView.loadAd(adRequest);

        return view;
    }
}
