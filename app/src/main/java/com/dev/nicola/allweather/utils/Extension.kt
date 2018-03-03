package com.dev.nicola.allweather.utils

import android.support.v7.widget.RecyclerView
import android.view.animation.LayoutAnimationController

fun RecyclerView.layoutAnimation(controller: LayoutAnimationController) {
    this.layoutAnimation = controller
    this.adapter.notifyDataSetChanged()
    this.scheduleLayoutAnimation()
}