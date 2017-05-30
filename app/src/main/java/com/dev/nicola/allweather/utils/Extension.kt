package com.dev.nicola.allweather.utils

import com.dev.nicola.allweather.model.Apixu.RootApixu
import com.dev.nicola.allweather.model.DarkSky.RootDarkSky
import com.dev.nicola.allweather.model.Yahoo.RootYahoo
import io.realm.Realm
import kotlin.reflect.KClass


fun Any?.log(obj: Any? = null) {
    val logger = when (obj) {
        is KClass<*> -> obj.java.simpleName
        is Class<*> -> obj.simpleName
        is String -> obj
        null -> "Default"
        else -> obj.javaClass.simpleName
    }

    val message = when (this) {
        is String? -> if (this == null) "NullString" else this
        is Int? -> if (this == null) "NullInt" else "Int: $this"
        is Float? -> if (this == null) "NullFloat" else "Float: $this"
        is Double? -> if (this == null) "NullDouble" else "Double: $this"
        else -> if (this == null) "NullValue" else "Value: $this"
    }
    android.util.Log.e(logger, message)
}

fun Realm?.fetchDarkSky(): RootDarkSky? {
    return this?.where(RootDarkSky::class.java)?.findFirst()
}

fun Realm?.fetchApixu(): RootApixu? {
    return this?.where(RootApixu::class.java)?.findFirst()
}

fun Realm?.fetchYahoo(): RootYahoo? {
    return this?.where(RootYahoo::class.java)?.findFirst()
}