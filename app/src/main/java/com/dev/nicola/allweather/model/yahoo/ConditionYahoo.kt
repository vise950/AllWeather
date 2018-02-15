package com.dev.nicola.allweather.model.yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class ConditionYahoo : RealmObject() {
    var code: String? = null
    var date: String? = null
    var temp: String? = null
    var text: String? = null
}