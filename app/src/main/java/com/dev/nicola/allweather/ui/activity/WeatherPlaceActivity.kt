package com.dev.nicola.allweather.ui.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import com.dev.nicola.allweather.R


class WeatherPlaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_place)

//        enterAnimation()
    }

    private fun enterAnimation() {
        window.sharedElementEnterTransition
                .addListener(object : Transition.TransitionListener {
                    override fun onTransitionEnd(transition: Transition?) {}
                    override fun onTransitionResume(transition: Transition?) {}
                    override fun onTransitionPause(transition: Transition?) {}
                    override fun onTransitionCancel(transition: Transition?) {}

                    override fun onTransitionStart(transition: Transition) {
                        val animator = ObjectAnimator.ofFloat()
                        animator.duration = 250
                        animator.start()
                    }
                })
    }
}