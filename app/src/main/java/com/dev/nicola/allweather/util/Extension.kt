package com.dev.nicola.allweather.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.dao.DarkSkyDao
import com.dev.nicola.allweather.dao.FavoritePlaceDao
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import java.util.*


/* COMMON */
inline fun <reified E> Context.goto(block: (Intent) -> Unit = {}) {
    val i = Intent(this, E::class.java)
    block.invoke(i)
    startActivity(i)
}

inline fun <reified E> Activity.gotoWithFinish(block: (Intent) -> Unit = {}) {
    val i = Intent(this, E::class.java)
    block.invoke(i)
    startActivity(i)
    this.finish()
}

fun RecyclerView.layoutAnimation() {
    this.layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.layout_animation_fall_down)
    this.adapter?.notifyDataSetChanged()
    this.scheduleLayoutAnimation()
}

fun isDebug(): Boolean = BuildConfig.DEBUG

fun uuid(): String = UUID.randomUUID().toString()

fun Location.getName(context: Context): String? {
    Geocoder(context, Locale.getDefault()).also {
        it.getFromLocation(this.latitude, this.longitude, 1)?.let {
            return it[0].locality
        }
    }
    return null
}

fun <T1: Any, T2: Any, R: Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2)->R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}


/* REALM */
fun <T : RealmModel> RealmResults<T>.asLiveData() = RealmLiveData(this)

fun Realm.favoritePlaceDao(): FavoritePlaceDao = FavoritePlaceDao(this)
fun Realm.darkSkyDao(): DarkSkyDao = DarkSkyDao(this)