package com.etologic.pointscorer.app.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
class MyConversionUtilsTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    @Test
    @Config(qualifiers = "mdpi")
    fun `on mdpi one dp is one pixel`() {
        assertThat(MyConversionUtils.dpToPx(context, 10)).isEqualTo(10)
    }

    @Test
    @Config(qualifiers = "xxhdpi")
    fun `on xxhdpi one dp is three pixels`() {
        assertThat(MyConversionUtils.dpToPx(context, 10)).isEqualTo(30)
    }

    @Test
    @Config(qualifiers = "hdpi")
    fun `rounds to the nearest pixel`() {
        // 1.5 density: 3dp = 4.5px -> 5px
        assertThat(MyConversionUtils.dpToPx(context, 3)).isEqualTo(5)
        assertThat(MyConversionUtils.dpToPx(context, 0)).isEqualTo(0)
    }
}
