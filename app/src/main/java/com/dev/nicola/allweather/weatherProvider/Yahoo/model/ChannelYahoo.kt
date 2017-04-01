package com.dev.nicola.allweather.weatherProvider.Yahoo.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ChannelYahoo : RealmObject() {

    @SerializedName("units")
    var units: UnitsYahoo? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("link")
    var link: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("language")
    var language: String? = null
    @SerializedName("lastBuildDate")
    var lastBuildDate: String? = null
    @SerializedName("ttl")
    var ttl: String? = null
    @SerializedName("location")
    var location: LocationYahoo? = null
    @SerializedName("wind")
    var wind: WindYahoo? = null
    @SerializedName("atmosphereYahoo")
    var atmosphereYahoo: AtmosphereYahoo? = null
    @SerializedName("astronomyYahoo")
    var astronomyYahoo: AstronomyYahoo? = null
    @SerializedName("image")
    var image: ImageYahoo? = null
    @SerializedName("item")
    var item: ItemYahoo? = null
}