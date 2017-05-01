package com.dev.nicola.allweather.model.Apixu

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ForecastDayApixu : RealmObject() {

    @SerializedName("date")
    var date: String? = null
    @SerializedName("date_epoch")
    var dateEpoch: Int? = null
    @SerializedName("day")
    var day: DayApixu? = null
    @SerializedName("astro")
    var astroApixu: AstroApixu? = null
    @SerializedName("hour")
    var hour: RealmList<HourApixu>? = null
}