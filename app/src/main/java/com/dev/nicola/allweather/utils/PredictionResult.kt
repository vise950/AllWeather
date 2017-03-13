package com.dev.nicola.allweather.utils

import android.os.Parcel
import android.os.Parcelable

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

/**
 * Created by Nicola on 15/02/2017.
 */

internal class PredictionResult : SearchSuggestion {

    private var searchResult: String? = null
    private var isHistory = false

    constructor(s: String?) {
        this.searchResult = s
    }

    constructor(source: Parcel) {
        this.searchResult = source.readString()
        this.isHistory = source.readInt() != 0
    }

    override fun getBody(): String? {
        return searchResult
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(searchResult)
        dest.writeInt(if (isHistory) 1 else 0)
    }

    companion object {

        val CREATOR: Parcelable.Creator<PredictionResult> = object : Parcelable.Creator<PredictionResult> {
            override fun createFromParcel(parcel: Parcel): PredictionResult {
                return PredictionResult(parcel)
            }

            override fun newArray(size: Int): Array<PredictionResult?> {
                return arrayOfNulls(size)
            }
        }
    }
}