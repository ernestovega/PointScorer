package com.etologic.pointscorer.app.utils

import android.content.Context

object MyConversionUtils {
    
    fun dpToPx(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}