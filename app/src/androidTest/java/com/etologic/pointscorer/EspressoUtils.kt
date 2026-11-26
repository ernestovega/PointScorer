package com.etologic.pointscorer

import android.graphics.Rect
import android.os.SystemClock
import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
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

/**
 * Like click(), but taps the center of the view as it is drawn. The top players of every game sit
 * in a frame rotated 180 degrees, and Espresso's click() computes the center ignoring that
 * rotation, so it would tap a different button.
 */
fun tap(): ViewAction = GeneralClickAction(
    Tap.SINGLE,
    { view ->
        val visible = Rect()
        view.getGlobalVisibleRect(visible) // applies the parents' rotation
        val window = IntArray(2)
        view.rootView.getLocationOnScreen(window)
        floatArrayOf(window[0] + visible.exactCenterX(), window[1] + visible.exactCenterY())
    },
    Press.FINGER,
    InputDevice.SOURCE_UNKNOWN,
    MotionEvent.BUTTON_PRIMARY,
)
