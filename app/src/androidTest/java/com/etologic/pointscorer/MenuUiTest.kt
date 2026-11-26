package com.etologic.pointscorer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.etologic.pointscorer.app.main.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Espresso tests of the menu, run on a device or emulator. App data is wiped before each test. */
@RunWith(AndroidJUnit4::class)
class MenuUiTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun showsDefaultInitialPointsAndAllGames() {
        waitForMenu("100")
        listOf(
            R.id.btMain1Player, R.id.btMain2Player, R.id.btMain3Player, R.id.btMain4Player,
            R.id.btMain5Player, R.id.btMain6Player, R.id.btMain7Player, R.id.btMain8Player,
        ).forEach { onView(withId(it)).check(matches(isDisplayed())) }
    }

    @Test
    fun invalidInitialPointsShowAnError() {
        waitForMenu()
        onView(withId(R.id.etMainInitialPoints)).perform(replaceText(""), closeSoftKeyboard())

        onView(withId(R.id.etMainInitialPoints))
            .check(matches(hasErrorText(context.getString(R.string.error_initial_points))))
    }

    @Test
    fun newInitialPointsAreUsedInTheGames() {
        waitForMenu()
        onView(withId(R.id.etMainInitialPoints)).perform(replaceText("50"), closeSoftKeyboard())

        onView(withId(R.id.btMain1Player)).perform(click())

        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("50")
    }

    @Test
    fun initialPointsAreRememberedAfterLeavingAGame() {
        waitForMenu()
        onView(withId(R.id.etMainInitialPoints)).perform(replaceText("75"), closeSoftKeyboard())
        onView(withId(R.id.btMain1Player)).perform(click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("75")

        pressBack()

        waitForMenu("75")
    }

    @Test
    fun resetAllAsksForConfirmationAndRestoresEveryGame() {
        onView(withId(R.id.btMain1Player)).perform(click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("100")
        onView(withId(R.id.btUp)).perform(click(), click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("102")
        pressBack()

        onView(withId(R.id.acbMainResetAllPoints)).perform(click())
        onView(withText(R.string.are_you_sure)).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(withText(android.R.string.ok)).inRoot(isDialog()).perform(click())

        onView(withId(R.id.btMain1Player)).perform(click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("100")
    }

    @Test
    fun cancellingResetAllKeepsThePoints() {
        onView(withId(R.id.btMain1Player)).perform(click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("100")
        onView(withId(R.id.btUp)).perform(click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("101")
        pressBack()

        onView(withId(R.id.acbMainResetAllPoints)).perform(click())
        onView(withText(android.R.string.cancel)).inRoot(isDialog()).perform(click())

        onView(withId(R.id.btMain1Player)).perform(click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("101")
    }

    @Test
    fun backFromAGameReturnsToTheMenu() {
        onView(withId(R.id.btMain3Player)).perform(click())
        onView(withId(R.id.flPlayer31)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.btMain3Player)).check(matches(isDisplayed()))
        onView(withId(R.id.flPlayer31)).check(doesNotExist())
    }
}
