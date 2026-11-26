package com.etologic.pointscorer

import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.etologic.pointscorer.app.main.activity.MainActivity
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Espresso tests of the game screens and the player shield, run on a device or emulator. */
@RunWith(AndroidJUnit4::class)
class GameUiTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    private fun openGame(button: Int) {
        waitForMenu()
        onView(withId(button)).perform(click())
    }

    private fun openPlayerMenu(playerFrame: Int, item: Int) {
        inPlayer(playerFrame, R.id.ibMenu).perform(tap())
        onView(withText(item)).inRoot(isPlatformPopup()).perform(click())
    }

    private fun confirmDialog() {
        onView(withText(android.R.string.ok)).inRoot(isDialog()).perform(click())
    }

    @Test
    fun upAndDownChangeThePoints() {
        openGame(R.id.btMain1Player)
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("100")

        onView(withId(R.id.btUp)).perform(click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("101")
        onView(withId(R.id.tvUpCount)).check(matches(withText("+1")))

        onView(withId(R.id.btDown)).perform(click(), click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("99")
    }

    @Test
    fun everyPlayerKeepsItsOwnPoints() {
        openGame(R.id.btMain2Player)
        inPlayer(R.id.flPlayer21, R.id.tvPointsPlayer).hasTextEventually("100")
        inPlayer(R.id.flPlayer22, R.id.tvPointsPlayer).hasTextEventually("100")

        inPlayer(R.id.flPlayer21, R.id.btUp).perform(tap(), tap(), tap())
        inPlayer(R.id.flPlayer22, R.id.btDown).perform(tap())

        inPlayer(R.id.flPlayer21, R.id.tvPointsPlayer).hasTextEventually("103")
        inPlayer(R.id.flPlayer22, R.id.tvPointsPlayer).hasTextEventually("99")
    }

    @Test
    fun everyGameShowsAllItsPlayers() {
        val games = listOf(
            R.id.btMain1Player to listOf(R.id.flPlayer11),
            R.id.btMain4Player to listOf(R.id.flPlayer41, R.id.flPlayer42, R.id.flPlayer43, R.id.flPlayer44),
            R.id.btMain8Player to listOf(
                R.id.flPlayer81, R.id.flPlayer82, R.id.flPlayer83, R.id.flPlayer84,
                R.id.flPlayer85, R.id.flPlayer86, R.id.flPlayer87, R.id.flPlayer88,
            ),
        )
        games.forEach { (button, frames) ->
            openGame(button)
            frames.forEach { inPlayer(it, R.id.tvPointsPlayer).hasTextEventually("100") }
            androidx.test.espresso.Espresso.pressBack()
        }
    }

    @Test
    fun pointsSurviveARotationOrRecreation() {
        openGame(R.id.btMain2Player)
        inPlayer(R.id.flPlayer21, R.id.tvPointsPlayer).hasTextEventually("100")
        inPlayer(R.id.flPlayer21, R.id.btUp).perform(tap())
        inPlayer(R.id.flPlayer21, R.id.tvPointsPlayer).hasTextEventually("101")

        activityRule.scenario.recreate()

        inPlayer(R.id.flPlayer21, R.id.tvPointsPlayer).hasTextEventually("101")
        inPlayer(R.id.flPlayer22, R.id.tvPointsPlayer).hasTextEventually("100")
    }

    @Test
    fun cannotGoOverTheMaximum() {
        waitForMenu()
        onView(withId(R.id.etMainInitialPoints)).perform(replaceText("9999"), closeSoftKeyboard())
        onView(withId(R.id.btMain1Player)).perform(click())
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("9999")

        onView(withId(R.id.btUp)).perform(click())

        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("9999")
        onView(withId(R.id.tvUpCount)).check(matches(withText("")))
    }

    @Test
    fun settingsChangeTheNameAndColor() {
        openGame(R.id.btMain1Player)
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("100")

        openPlayerMenu(R.id.flPlayer11, R.string.settings)
        onView(withId(R.id.etName)).inRoot(isDialog()).perform(replaceText("Ernesto"), closeSoftKeyboard())
        onView(withId(R.id.vColorRed)).inRoot(isDialog()).perform(click())
        onView(withId(R.id.btOk)).inRoot(isDialog()).perform(click())

        onView(withId(R.id.etName)).hasTextEventually("Ernesto")
        val red = ContextCompat.getColor(context, R.color.red)
        onView(withId(R.id.etName)).check { view, _ -> assertThat((view as EditText).currentTextColor).isEqualTo(red) }
        onView(withId(R.id.tvPointsPlayer)).check { view, _ ->
            assertThat((view as android.widget.TextView).currentTextColor).isEqualTo(red)
        }
    }

    @Test
    fun nameAndColorAreRememberedWhenReopeningTheGame() {
        openGame(R.id.btMain1Player)
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("100")
        openPlayerMenu(R.id.flPlayer11, R.string.settings)
        onView(withId(R.id.etName)).inRoot(isDialog()).perform(replaceText("Lola"), closeSoftKeyboard())
        onView(withId(R.id.btOk)).inRoot(isDialog()).perform(click())
        onView(withId(R.id.etName)).hasTextEventually("Lola")

        androidx.test.espresso.Espresso.pressBack()
        onView(withId(R.id.btMain1Player)).perform(click())

        onView(withId(R.id.etName)).hasTextEventually("Lola")
    }

    @Test
    fun restorePointsResetsOnlyThatPlayer() {
        openGame(R.id.btMain2Player)
        inPlayer(R.id.flPlayer21, R.id.tvPointsPlayer).hasTextEventually("100")
        inPlayer(R.id.flPlayer21, R.id.btUp).perform(tap(), tap())
        inPlayer(R.id.flPlayer22, R.id.btUp).perform(tap())
        inPlayer(R.id.flPlayer21, R.id.tvPointsPlayer).hasTextEventually("102")
        inPlayer(R.id.flPlayer22, R.id.tvPointsPlayer).hasTextEventually("101")

        openPlayerMenu(R.id.flPlayer21, R.string.restore_points)
        confirmDialog()

        inPlayer(R.id.flPlayer21, R.id.tvPointsPlayer).hasTextEventually("100")
        inPlayer(R.id.flPlayer22, R.id.tvPointsPlayer).hasTextEventually("101")
    }

    @Test
    fun restoreAllPointsResetsEveryPlayerOfTheGame() {
        openGame(R.id.btMain3Player)
        listOf(R.id.flPlayer31, R.id.flPlayer32, R.id.flPlayer33).forEach {
            inPlayer(it, R.id.tvPointsPlayer).hasTextEventually("100")
            inPlayer(it, R.id.btDown).perform(tap())
            inPlayer(it, R.id.tvPointsPlayer).hasTextEventually("99")
        }

        openPlayerMenu(R.id.flPlayer32, R.string.restore_all_points)
        confirmDialog()

        listOf(R.id.flPlayer31, R.id.flPlayer32, R.id.flPlayer33).forEach {
            inPlayer(it, R.id.tvPointsPlayer).hasTextEventually("100")
        }
    }

    @Test
    fun onePlayerGameHasNoRestoreAllPointsOption() {
        openGame(R.id.btMain1Player)
        onView(withId(R.id.tvPointsPlayer)).hasTextEventually("100")

        onView(withId(R.id.ibMenu)).perform(click())

        onView(withText(R.string.restore_points)).inRoot(isPlatformPopup()).check(matches(isDisplayed()))
        onView(withText(R.string.restore_all_points)).inRoot(isPlatformPopup()).check(doesNotExist())
    }
}
