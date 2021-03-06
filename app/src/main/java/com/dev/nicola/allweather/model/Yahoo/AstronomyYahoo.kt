package com.dev.nicola.allweather.model.Yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class AstronomyYahoo : RealmObject() {
    @SerializedName("sunrise")
    var sunrise: String? = null
    @SerializedName("sunset")
    var sunset: String? = null
}