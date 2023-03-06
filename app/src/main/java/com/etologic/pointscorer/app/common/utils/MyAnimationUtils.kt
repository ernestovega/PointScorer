package com.etologic.pointscorer.app.common.utils

import android.view.animation.*
import android.view.animation.Animation.AnimationListener
import android.view.animation.Animation.RELATIVE_TO_SELF

object MyAnimationUtils {

    private const val SHIELD_ANIMATION_DURATION = 1000L
    const val AUX_POINTS_FADE_OUT_ANIMATION_DURATION = 5000L

    fun getShieldAnimation(): AnimationSet {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = SHIELD_ANIMATION_DURATION
        val scaleAnimation =
            ScaleAnimation(4f, 1f, 4f, 1f, RELATIVE_TO_SELF, .5f, RELATIVE_TO_SELF, .3f)
        alphaAnimation.duration = SHIELD_ANIMATION_DURATION
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(scaleAnimation)
        animationSet.duration = SHIELD_ANIMATION_DURATION / 2
        animationSet.interpolator = DecelerateInterpolator()
        return animationSet
    }

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