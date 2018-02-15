package com.dev.nicola.allweather.model.yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class ResultsYahoo : RealmObject() {
    var channel: ChannelYahoo? = null
}