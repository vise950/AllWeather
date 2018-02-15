package com.dev.nicola.allweather.model.yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

open class RootYahoo : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var query: QueryYahoo? = null
}