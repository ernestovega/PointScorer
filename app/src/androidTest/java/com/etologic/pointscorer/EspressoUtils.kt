package com.etologic.pointscorer

import android.os.SystemClock
import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

/**
 * Players' data is read from SharedPreferences on Dispatchers.IO, which Espresso does not track,
 * so assertions on loaded values are retried for a short while.
 */
fun ViewInteraction.checkEventually(assertion: ViewAssertion, timeoutMs: Long = 5_000): ViewInteraction {
    val end = SystemClock.uptimeMillis() + timeoutMs
    while (true) {
        try {
            return check(assertion)
        } catch (error: Throwable) {
            if (SystemClock.uptimeMillis() > end) throw error
            Thread.sleep(50)
        }
    }
}

fun ViewInteraction.hasTextEventually(text: String) = checkEventually(matches(withText(text)))

/** A view of the player shown in the given frame, e.g. [inPlayer] (R.id.flPlayer21, R.id.btUp). */
fun inPlayer(@IdRes playerFrame: Int, @IdRes viewId: Int): ViewInteraction =
    onView(playerView(playerFrame, viewId))

fun playerView(@IdRes playerFrame: Int, @IdRes viewId: Int): Matcher<View> =
    allOf(withId(viewId), isDescendantOfA(withId(playerFrame)))

/** Waits until the menu has loaded the stored initial points, so typing is not overwritten. */
fun waitForMenu(initialPoints: String = "100") {
    onView(withId(R.id.etMainInitialPoints)).hasTextEventually(initialPoints)
}
