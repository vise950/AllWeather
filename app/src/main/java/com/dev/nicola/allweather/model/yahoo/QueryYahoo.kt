package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class QueryYahoo : RealmObject(){
    var count: Int? = null
    var created: String? = null
    var lang: String? = null
    var results: ResultsYahoo? = null
}