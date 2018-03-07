package com.dev.nicola.allweather.utils

import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
import com.dev.nicola.allweather.R

fun RecyclerView.layoutAnimation() {
    this.layoutAnimation = AnimationUtils.loadLayoutAnimation(this.context, R.anim.layout_animation_fall_down)
    this.adapter.notifyDataSetChanged()
    this.scheduleLayoutAnimation()
}