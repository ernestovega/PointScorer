package com.etologic.pointscorer.app.common.utils

import android.content.res.Resources
import android.util.TypedValue

fun Int.dpToPx(res: Resources): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), res.displayMetrics).toInt()