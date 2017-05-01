package com.dev.nicola.allweather.model.Yahoo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ImageYahoo : RealmObject() {

    @SerializedName("title")
    var title: String? = null
    @SerializedName("width")
    var width: String? = null
    @SerializedName("height")
    var height: String? = null
    @SerializedName("link")
    var link: String? = null
    @SerializedName("url")
    var url: String? = null
}