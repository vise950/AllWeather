package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class ImageYahoo : RealmObject(){
    var title: String? = null
    var width: String? = null
    var height: String? = null
    var link: String? = null
    var url: String? = null
}