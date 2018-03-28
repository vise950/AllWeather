package com.dev.nicola.allweather.model.apixu

import com.google.gson.annotations.SerializedName

open class DayApixu {

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