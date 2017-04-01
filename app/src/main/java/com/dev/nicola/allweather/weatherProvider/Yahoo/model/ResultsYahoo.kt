package com.dev.nicola.allweather.weatherProvider.Yahoo.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ResultsYahoo : RealmObject() {

    @SerializedName("channel")
    var channel: ChannelYahoo? = null
}