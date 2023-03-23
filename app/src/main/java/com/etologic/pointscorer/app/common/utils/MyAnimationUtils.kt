package com.etologic.pointscorer.app.common.utils

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import com.etologic.pointscorer.common.Constants.A_SECOND_IN_MILLIS

object MyAnimationUtils {

    fun getAuxPointsFadeOutAnimation(endAction: (() -> Unit)?): Animation {
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = 5 * A_SECOND_IN_MILLIS
        alphaAnimation.fillAfter = true
        alphaAnimation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                endAction?.invoke()
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
        return alphaAnimation
    }

}
