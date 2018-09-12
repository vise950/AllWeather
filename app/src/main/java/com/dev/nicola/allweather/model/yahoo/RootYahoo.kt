package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RootYahoo : RealmObject(){
    @PrimaryKey
    var id: String? = null
    var query: QueryYahoo? = null
}