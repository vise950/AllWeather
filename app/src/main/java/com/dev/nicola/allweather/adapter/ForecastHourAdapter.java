//package com.dev.nicola.allweather.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.dev.nicola.allweather.R;
//import com.dev.nicola.allweather.model.ForecastHour;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by Nicola on 14/08/2016.
// */
//
///**
// * Adapter for hourly forecast weather
// */
//public class ForecastHourAdapter extends RecyclerView.Adapter<ForecastHourAdapter.ForecastViewHolder> {
//
//    private List<ForecastHour> mForecastHourList;
//
//    public ForecastHourAdapter(List<ForecastHour> forecastHourList) {
//        this.mForecastHourList = forecastHourList;
//    }
//
//    @Override
//    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_forecast_hour, parent, false);
//
//        return new ForecastViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(ForecastViewHolder holder, int position) {
//        ForecastHour forecastHour = mForecastHourList.get(position);
//        holder.hour.setText(forecastHour.getHour());
//        holder.icon.setImageResource(forecastHour.getIcon());
//        holder.temperature.setText(String.valueOf(forecastHour.getTemperature()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mForecastHourList.size();
//    }
//
//    public class ForecastViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.hour_forecast_hour)
//        TextView hour;
//        @BindView(R.id.icon_forecast_hour)
//        ImageView icon;
//        @BindView(R.id.temperature_forecast_hour)
//        TextView temperature;
//
//
//        ForecastViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }
//}
