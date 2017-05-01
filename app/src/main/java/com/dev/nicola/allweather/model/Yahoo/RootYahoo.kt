package com.dev.nicola.allweather.model.Yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RootYahoo : RealmObject() {

    @PrimaryKey
    var id: String? = null
    @SerializedName("query")
    var query: QueryYahoo? = null
}