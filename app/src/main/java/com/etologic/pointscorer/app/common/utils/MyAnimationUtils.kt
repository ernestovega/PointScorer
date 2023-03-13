package com.etologic.pointscorer.app.common.utils

import android.view.animation.*
import android.view.animation.Animation.AnimationListener
import android.view.animation.Animation.RELATIVE_TO_SELF

object MyAnimationUtils {

    private const val AUX_POINTS_FADE_OUT_ANIMATION_DURATION = 5000L

    fun getAuxPointsFadeOutAnimation(endAction: Runnable?): Animation {
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = AUX_POINTS_FADE_OUT_ANIMATION_DURATION
        alphaAnimation.fillAfter = true
        alphaAnimation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                endAction?.run()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        return alphaAnimation
    }

}