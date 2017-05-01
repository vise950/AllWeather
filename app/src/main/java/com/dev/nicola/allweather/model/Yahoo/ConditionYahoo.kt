package com.dev.nicola.allweather.model.Yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ConditionYahoo : RealmObject() {

    @SerializedName("code")
    var code: String? = null
    @SerializedName("date")
    var date: String? = null
    @SerializedName("temp")
    var temp: String? = null
    @SerializedName("text")
    var text: String? = null
}