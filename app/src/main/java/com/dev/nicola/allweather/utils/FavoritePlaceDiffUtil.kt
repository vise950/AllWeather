package com.dev.nicola.allweather.utils

import android.support.v7.util.DiffUtil
import com.dev.nicola.allweather.model.FavoritePlace

class FavoritePlaceDiffUtil(private val oldData: List<FavoritePlace>, private val newData: List<FavoritePlace>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition].id == newData[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition] == newData[newItemPosition]

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? =
            super.getChangePayload(oldItemPosition, newItemPosition)
}