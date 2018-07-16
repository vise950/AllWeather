package com.dev.nicola.allweather.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FavoritePlace : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var name: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
}