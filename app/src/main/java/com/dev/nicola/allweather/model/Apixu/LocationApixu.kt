package com.dev.nicola.allweather.model.Apixu

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class LocationApixu : RealmObject() {

    @SerializedName("name")
    var name: String? = null
    @SerializedName("region")
    var region: String? = null
    @SerializedName("country")
    var country: String? = null
    @SerializedName("lat")
    var lat: Double? = null
    @SerializedName("lon")
    var lon: Double? = null
    @SerializedName("tz_id")
    var tzId: String? = null
    @SerializedName("localtime_epoch")
    var localtimeEpoch: Int? = null
    @SerializedName("localtime")
    var localtime: String? = null
}