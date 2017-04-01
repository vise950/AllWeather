package com.dev.nicola.allweather.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.model.ForecastDay

class ForecastDayAdapter(private val context: Context, private val mForecastDayList: ArrayList<ForecastDay>) : RecyclerView.Adapter<ForecastDayAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var date_txt = view.findViewById(R.id.date_forecast_day) as TextView
        var condition_txt = view.findViewById(R.id.condition_forecast_day) as TextView
        var temperature_txt = view.findViewById(R.id.temperature_forecast_day) as TextView
        var icon_img = view.findViewById(R.id.icon_forecast_day) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_forecast_day, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastDay = mForecastDayList[position]
        holder.date_txt.text = forecastDay.date
        holder.condition_txt.text = forecastDay.condition
        holder.temperature_txt.text = forecastDay.temperature.toString()
        Glide.with(context).load(forecastDay.icon).into(holder.icon_img)
    }

    override fun getItemCount(): Int {
        return mForecastDayList.size
    }
}
