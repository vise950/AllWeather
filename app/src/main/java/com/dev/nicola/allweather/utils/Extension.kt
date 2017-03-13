package com.dev.nicola.allweather.utils

import kotlin.reflect.KClass

/**
 * Created by Nicola on 01/03/2017.
 */

fun Any?.log(obj: Any? = null) {
    val logger = when(obj){
        is KClass<*> -> obj.java.simpleName
        is Class<*> -> obj.simpleName
        is String -> obj
        null -> "Default"
        else -> obj.javaClass.simpleName
    }

    val message = when(this){
        is String? -> if(this == null) "NullString" else this
        is Int? -> if(this == null) "NullInt" else "Int: $this"
        is Float? -> if(this == null) "NullFloat" else "Float: $this"
        is Double? -> if(this == null) "NullDouble" else "Double: $this"
        else -> if(this == null) "NullValue" else "Value: $this"
    }
    android.util.Log.e(logger, message)
}