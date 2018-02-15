package com.dev.nicola.allweather.model.apixu

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class DayApixu : RealmObject() {

    @SerializedName("maxtemp_c")
    var maxtempC: Double? = null
    @SerializedName("mintemp_c")
    var mintempC: Double? = null
    @SerializedName("avgtemp_c")
    var avgtempC: Double? = null
    @SerializedName("totalprecip_mm")
    var totalprecipMm: Double? = null
    var condition: ForecastConditionApixu? = null
}