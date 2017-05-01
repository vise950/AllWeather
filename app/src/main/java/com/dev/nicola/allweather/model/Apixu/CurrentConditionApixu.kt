package com.dev.nicola.allweather.model.Apixu

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class CurrentConditionApixu : RealmObject() {

    @SerializedName("text")
    var text: String? = null
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("code")
    var code: Int? = null
}