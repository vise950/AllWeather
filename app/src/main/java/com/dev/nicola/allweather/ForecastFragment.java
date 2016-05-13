package com.dev.nicola.allweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.nicola.allweather.Provider.ForecastIO.ForecastIOData;
import com.dev.nicola.allweather.Util.Forecast;
import com.dev.nicola.allweather.Util.ForecastAdapter;
import com.dev.nicola.allweather.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicola on 24/03/2016.
 */
public class ForecastFragment extends Fragment {

    private static String TAG = ForecastFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private List<Forecast> mForecastList = new ArrayList<>();

    private Utils mUtils;
    private ForecastIOData mData;
    private Gson mGson;

    private String dataObject;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUtils = new Utils(getContext());
        mData = new ForecastIOData();
        mGson = new GsonBuilder().create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mInflateView = inflater.inflate(R.layout.forecast_fragment, container, false);

//        dataObject = getArguments().getString("JsonObject");
//        Log.d(TAG, "object forecast fragment" + dataObject);
//        mData = mGson.fromJson(dataObject, ForecastIOData.class);

        mRecyclerView = (RecyclerView) mInflateView.findViewById(R.id.forecast_recycle_view);
        mForecastAdapter = new ForecastAdapter(mForecastList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mForecastAdapter);

//        prepareForecastData();

        return mInflateView;
    }

    private void prepareForecastData() {
        Forecast forecast;

        for (int i = 1; i < 8; i++) {
            forecast = new Forecast(mUtils.getDayFormat(mData.getDaily().getData().get(i).getTime())
                    , mData.getDaily().getData().get(i).getSummary()
                    , mData.getDaily().getData().get(i).getTemperatureMin()
                    , mUtils.getIcon(mData.getDaily().getData().get(i).getIcon()));
            mForecastList.add(forecast);
        }
        mForecastAdapter.notifyDataSetChanged();


    }
}
