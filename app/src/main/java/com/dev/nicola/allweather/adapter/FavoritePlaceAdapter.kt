package com.dev.nicola.allweather.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.model.FavoritePlace


class FavoritePlaceAdapter(private val ctx: Context, private var data: List<FavoritePlace>) : RecyclerView.Adapter<FavoritePlaceAdapter.FavoritePlaceViewHolder>() {

    var onItemClicked: ((String) -> Unit)? = null

    inner class FavoritePlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var placeBackground: ImageView = view.findViewById(R.id.place_weather_bg)
        var placeName: TextView = view.findViewById(R.id.place_name_tv)
        var placeSummary: TextView = view.findViewById(R.id.place_weather_summary_tv)
        var placeTemperature: TextView = view.findViewById(R.id.place_temperature_tv)

        init {
            view.setOnClickListener { onItemClicked?.invoke(data[adapterPosition].id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePlaceViewHolder =
            FavoritePlaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false))

    override fun onBindViewHolder(holder: FavoritePlaceViewHolder, position: Int) {
        data[position].let {
            holder.apply {
                //                placeBackground
                placeName.text = it.name
                placeSummary.text = "Test"
                placeTemperature.text = "28"
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun updateData(data: List<FavoritePlace>) {
        this.data = data
        notifyDataSetChanged()
    }
}