package com.dev.nicola.allweather.model.yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class ImageYahoo : RealmObject() {
    var title: String? = null
    var width: String? = null
    var height: String? = null
    var link: String? = null
    var url: String? = null
}