package com.dev.nicola.allweather.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.nicola.allweather.Model.Forecast;
import com.dev.nicola.allweather.R;

import java.util.List;

/**
 * Created by Nicola on 09/05/2016.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<Forecast> mForecastList;

    public ForecastAdapter(List<Forecast> forecastList) {
        this.mForecastList = forecastList;
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);

        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        Forecast forecast = mForecastList.get(position);
        holder.date.setText(forecast.getDate());
        holder.condition.setText(forecast.getCondition());
        holder.temperature.setText(String.valueOf(forecast.getTemperature()) + "°");
//        holder.temperature.setText(String.format(Resources.getSystem().getString(R.string.temperature),forecast.getTemperature()));
        holder.icon.setImageResource(forecast.getIcon());
    }

    @Override
    public int getItemCount() {
        return mForecastList.size();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView condition;
        public TextView temperature;
        public ImageView icon;

        public ForecastViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date_forecast_fragment);
            condition = (TextView) view.findViewById(R.id.condition_forecast_fragment);
            temperature = (TextView) view.findViewById(R.id.temperature_forecast_fragment);
            icon = (ImageView) view.findViewById(R.id.icon_forecast_fragment);
        }
    }
}
