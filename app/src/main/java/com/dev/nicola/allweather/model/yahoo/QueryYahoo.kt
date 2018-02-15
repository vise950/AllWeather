package com.dev.nicola.allweather.model.yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class QueryYahoo : RealmObject() {
    var count: Int? = null
    var created: String? = null
    var lang: String? = null
    var results: ResultsYahoo? = null
}