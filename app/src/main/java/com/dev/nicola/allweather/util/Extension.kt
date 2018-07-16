package com.dev.nicola.allweather.util

import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.db.dao.FavoritePlaceDao
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

fun RecyclerView.layoutAnimation() {
    this.layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.layout_animation_fall_down)
    this.adapter?.notifyDataSetChanged()
    this.scheduleLayoutAnimation()
}

fun isDebug(): Boolean = BuildConfig.DEBUG


/* REALM */
fun <T : RealmModel> RealmResults<T>.asLiveData() = RealmLiveData(this)

fun Realm.favoritePlaceDao(): FavoritePlaceDao = FavoritePlaceDao(this)