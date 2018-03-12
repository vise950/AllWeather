package com.dev.nicola.allweather.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.eggon.eggoid.extension.error
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.utils.FavoritePlaceDiffUtil


class FavoritePlaceAdapter(private val context: Context, private var data: List<FavoritePlace>) : RecyclerView.Adapter<FavoritePlaceAdapter.FavoritePlaceViewHolder>() {

    var onItemClicked: ((String) -> Unit)? = null
    var onItemLongClicked: (() -> Unit)? = null

    var selectedItem = arrayListOf<String>()
    private var selectedItemPosition = arrayListOf<Int>()
    var isActionModeActive = false

    inner class FavoritePlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var container: CardView = view.findViewById(R.id.card_container)
        var placeBackground: ImageView = view.findViewById(R.id.place_weather_bg)
        var placeName: TextView = view.findViewById(R.id.place_name_tv)
        var placeSummary: TextView = view.findViewById(R.id.place_weather_summary_tv)
        var placeTemperature: TextView = view.findViewById(R.id.place_temperature_tv)

        init {
            view.setOnClickListener {
                if (isActionModeActive) {
                    selectItem(adapterPosition, container)
                    onItemLongClicked?.invoke()
                } else {
                    onItemClicked?.invoke(data[adapterPosition].id)
                }
            }
            view.setOnLongClickListener {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                selectItem(adapterPosition, container)
                onItemLongClicked?.invoke()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePlaceViewHolder =
            FavoritePlaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false))

    override fun onBindViewHolder(holder: FavoritePlaceViewHolder, position: Int) {

        data[position].let {
            holder.apply {
                //                placeBackground
                container.isSelected = false
                placeName.text = it.name
                placeSummary.text = "Test"
                placeTemperature.text = "28"
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun updateData(data: List<FavoritePlace>) {
        val diffResult = DiffUtil.calculateDiff(FavoritePlaceDiffUtil(this.data, data), true)
        diffResult.dispatchUpdatesTo(this)
        this.data = data
    }

    fun fakeUpdateData(data: List<FavoritePlace>) {
        val fakeData = arrayListOf<FavoritePlace>().apply { addAll(data) }
        selectedItemPosition.forEach { fakeData.removeAt(it) }

        this.data.size.error("this data")
        data.size.error("data")
        fakeData.size.error("data")
        val diffResult = DiffUtil.calculateDiff(FavoritePlaceDiffUtil(this.data, fakeData), true)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun selectItem(position: Int, view: View) {
        if (view.isSelected) {
            view.isSelected = false
            selectedItem.remove(data[position].id)
            selectedItemPosition.remove(position)
        } else {
            view.isSelected = true
            selectedItem.add(data[position].id)
            selectedItemPosition.add(position)
        }
    }

    fun clearSelection() {
        notifyDataSetChanged()
        selectedItem.clear()
        isActionModeActive = false
    }
}