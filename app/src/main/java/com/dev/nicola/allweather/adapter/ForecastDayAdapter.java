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
//import com.dev.nicola.allweather.model.ForecastDay;
//
//import java.util.List;
//
//
///**
// * Created by Nicola on 09/05/2016.
// */
//
///**
// * Adapter for forecast weather
// */
//public class ForecastDayAdapter extends RecyclerView.Adapter<ForecastDayAdapter.ForecastViewHolder> {
//
//    private List<ForecastDay> mForecastDayList;
//
//    public ForecastDayAdapter(List<ForecastDay> forecastDayList) {
//        this.mForecastDayList = forecastDayList;
//    }
//
//    @Override
//    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_forecast_day, parent, false);
//
//        return new ForecastViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(ForecastViewHolder holder, int position) {
//        ForecastDay forecastDay = mForecastDayList.get(position);
//        holder.date.setText(forecastDay.getDate());
//        holder.condition.setText(forecastDay.getCondition());
//        holder.temperature.setText(String.valueOf(forecastDay.getTemperature()));
//        holder.icon.setImageResource(forecastDay.getIcon());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mForecastDayList.size();
//    }
//
//
//    public class ForecastViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.date_forecast_day)
//        TextView date;
//        @BindView(R.id.condition_forecast_day)
//        TextView condition;
//        @BindView(R.id.temperature_forecast_day)
//        TextView temperature;
//        @BindView(R.id.icon_forecast_day)
//        ImageView icon;
//
//        ForecastViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }
//}
