package com.dev.nicola.allweather.model

import com.google.gson.annotations.SerializedName

class Prediction {
    val predictions: List<Description>? = null


    inner class Description {
        val description: String? = null
        val id: String? = null
    }
}