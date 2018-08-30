package com.dev.nicola.allweather.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.model.ForecastHour

class ForecastHourAdapter(private val context: Context, private val mForecastHourList: ArrayList<ForecastHour>) : RecyclerView.Adapter<ForecastHourAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var hour_txt = view.findViewById(R.id.hour_forecast_hour) as TextView
        var icon_img = view.findViewById(R.id.icon_forecast_hour) as ImageView
        var temperature_txt = view.findViewById(R.id.temperature_forecast_hour) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_forecast_hour, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastHour = mForecastHourList[position]
        holder.hour_txt.text = forecastHour.hour
        Glide.with(context).load(forecastHour.icon).into(holder.icon_img)
        holder.temperature_txt.text = forecastHour.temperature.toString()
    }

    override fun getItemCount(): Int {
        return mForecastHourList.size
    }
}
