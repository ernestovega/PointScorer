package com.etologic.pointscorer.app.utils

import android.view.animation.*
import android.view.animation.Animation.AnimationListener
import android.view.animation.Animation.RELATIVE_TO_SELF

object MyAnimationUtils {
    
    private val animationSet: AnimationSet
        get() {
            val alphaAnimation = AlphaAnimation(0f, 1f)
            alphaAnimation.duration = 1000
            val scaleAnimation = ScaleAnimation(4f, 1f, 4f, 1f, RELATIVE_TO_SELF, .5f, RELATIVE_TO_SELF, .3f)
            alphaAnimation.duration = 1000
            val animationSet = AnimationSet(true)
            animationSet.addAnimation(alphaAnimation)
            animationSet.addAnimation(scaleAnimation)
            animationSet.duration = 500
            animationSet.interpolator = DecelerateInterpolator()
            return animationSet
        }
    val shieldAnimation: Animation
        get() = animationSet
    
    fun getAuxPointsFadeOutAnimation(endAction: Runnable?): Animation {
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = 5000
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