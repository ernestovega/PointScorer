package com.etologic.pointscorer.app.common.utils

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

object ViewExtensions {

    fun View.hideKeyboard() {
        (rootView.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(rootView.windowToken, 0)
    }

}