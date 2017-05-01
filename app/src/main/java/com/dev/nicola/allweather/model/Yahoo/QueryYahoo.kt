package com.dev.nicola.allweather.model.Yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class QueryYahoo : RealmObject() {

    @SerializedName("count")
    var count: Int? = null
    @SerializedName("created")
    var created: String? = null
    @SerializedName("lang")
    var lang: String? = null
    @SerializedName("results")
    var results: ResultsYahoo? = null
}