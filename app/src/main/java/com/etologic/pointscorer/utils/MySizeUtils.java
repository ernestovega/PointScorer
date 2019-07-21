package com.etologic.pointscorer.utils;

import android.content.Context;

public class MySizeUtils {

    public static int dp2Px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }
}
