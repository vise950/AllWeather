package com.dev.nicola.allweather.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.dao.DarkSkyDao
import com.dev.nicola.allweather.dao.FavoritePlaceDao
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import kotlinx.coroutines.experimental.launch

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

inline fun delay(time: Long = 1500, crossinline block: () -> Unit = {}) {
    launch {
        kotlinx.coroutines.experimental.delay(time)
        block.invoke()
    }
}


/* REALM */
fun <T : RealmModel> RealmResults<T>.asLiveData() = RealmLiveData(this)

fun Realm.favoritePlaceDao(): FavoritePlaceDao = FavoritePlaceDao(this)
fun Realm.darkSkyDao(): DarkSkyDao = DarkSkyDao(this)