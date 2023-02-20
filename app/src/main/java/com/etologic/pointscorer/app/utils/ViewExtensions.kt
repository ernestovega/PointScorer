package com.etologic.pointscorer.app.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

object ViewExtensions {

    fun View.hideKeyboard() {
        (rootView.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(rootView.windowToken, 0)
    }

    fun View.showKeyboard() {
        (rootView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
    }
}