package com.etologic.pointscorer;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import java.util.Locale;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class MyAnimationUtils {

    public static Animation getUpdatePointsAnimation(TextView tvPoints, TextView tvPointsForAnimation, int points) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        ScaleAnimation scaleAnimation = new ScaleAnimation(4, 1, 4, 1, RELATIVE_TO_SELF, .5f, RELATIVE_TO_SELF, .3f);
        alphaAnimation.setDuration(1000);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(500);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {
                tvPointsForAnimation.setText(String.format(Locale.getDefault(), "%d", points));
            }
            @Override public void onAnimationEnd(Animation animation) {
                tvPoints.setText(String.format(Locale.getDefault(), "%d", points));
            }
            @Override public void onAnimationRepeat(Animation animation) {}
        });
        return animationSet;
    }
}
