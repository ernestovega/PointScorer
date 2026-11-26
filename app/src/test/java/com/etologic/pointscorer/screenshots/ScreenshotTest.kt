package com.etologic.pointscorer.screenshots

import android.view.View
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivity
import com.etologic.pointscorer.app.main.fragments.MenuFragment
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment
import com.etologic.pointscorer.app.main.fragments.player.PlayerSettingsDialogFragment
import com.etologic.pointscorer.testutils.finishAnimations
import com.etologic.pointscorer.testutils.playerFragments
import com.etologic.pointscorer.testutils.waitForPlayersLoaded
import com.etologic.pointscorer.testutils.waitUntil
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.captureScreenRoboImage
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Screenshot tests rendered on the JVM with Robolectric native graphics and compared by Roborazzi.
 * Reference images live in app/src/test/screenshots.
 *
 *   ./gradlew recordRoborazziDebug   (after an intended UI change)
 *   ./gradlew verifyRoborazziDebug   (in CI)
 */
@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel7)
class ScreenshotTest {

    private var scenario: ActivityScenario<MainActivity>? = null

    @After
    fun tearDown() {
        scenario?.close()
    }

    private fun launchMenu(): ActivityScenario<MainActivity> =
        ActivityScenario.launch(MainActivity::class.java).also { scenario = it }.apply {
            waitUntil {
                var ready = false
                onActivity {
                    ready = it.supportFragmentManager.findFragmentById(R.id.flMain) is MenuFragment &&
                        it.findViewById<EditText>(R.id.etMainInitialPoints).text.isNotEmpty()
                }
                ready
            }
            finishAnimations()
        }

    private fun launchGame(button: Int, players: Int): ActivityScenario<MainActivity> =
        launchMenu().apply {
            onActivity { it.findViewById<View>(button).performClick() }
            onActivity { it.waitForPlayersLoaded(players) }
            finishAnimations()
        }

    private fun capture(name: String) = onView(isRoot()).captureRoboImage("src/test/screenshots/$name.png")

    @Test
    fun menu() {
        launchMenu()
        capture("menu")
    }

    @Test
    @Config(qualifiers = "+land")
    fun menu_landscape() {
        launchMenu()
        capture("menu_landscape")
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.MediumTablet)
    fun menu_tablet() {
        launchMenu()
        capture("menu_tablet")
    }

    @Test
    fun game_1_player() {
        launchGame(R.id.btMain1Player, 1)
        capture("game_1_player")
    }

    @Test
    fun game_2_players() {
        launchGame(R.id.btMain2Player, 2)
        capture("game_2_players")
    }

    @Test
    fun game_3_players() {
        launchGame(R.id.btMain3Player, 3)
        capture("game_3_players")
    }

    @Test
    fun game_4_players() {
        launchGame(R.id.btMain4Player, 4)
        capture("game_4_players")
    }

    @Test
    fun game_5_players() {
        launchGame(R.id.btMain5Player, 5)
        capture("game_5_players")
    }

    @Test
    fun game_6_players() {
        launchGame(R.id.btMain6Player, 6)
        capture("game_6_players")
    }

    @Test
    fun game_7_players() {
        launchGame(R.id.btMain7Player, 7)
        capture("game_7_players")
    }

    @Test
    fun game_8_players() {
        launchGame(R.id.btMain8Player, 8)
        capture("game_8_players")
    }

    @Test
    @Config(qualifiers = "+land")
    fun game_4_players_landscape() {
        launchGame(R.id.btMain4Player, 4)
        capture("game_4_players_landscape")
    }

    @Test
    fun game_2_players_after_scoring() {
        launchGame(R.id.btMain2Player, 2).apply {
            fun player(index: Int): PlayerFragment {
                var player: PlayerFragment? = null
                onActivity { activity ->
                    player = activity.playerFragments().sortedBy { it.requireArguments().getInt(PlayerFragment.KEY_PLAYER_ID) }[index]
                }
                return player!!
            }
            fun PlayerFragment.points(): String {
                var points = ""
                onActivity { points = requireView().findViewById<android.widget.TextView>(R.id.tvPointsPlayer).text.toString() }
                return points
            }
            fun PlayerFragment.click(id: Int) = onActivity { requireView().findViewById<View>(id).performClick() }

            // One click at a time: each one is saved before the next is read
            listOf("101", "102", "103").forEach { expected ->
                player(0).click(R.id.btUp)
                waitUntil(describe = { "points=${player(0).points()} expected=$expected" }) { player(0).points() == expected }
            }
            player(1).click(R.id.btDown)
            waitUntil { player(1).points() == "99" }
        }
        // Captured before the 5 s fade out so the +3 / -1 counters are visible
        capture("game_2_players_after_scoring")
    }

    @OptIn(ExperimentalRoborazziApi::class)
    @Test
    fun player_settings_dialog() {
        launchGame(R.id.btMain1Player, 1).apply {
            onActivity { activity ->
                PlayerSettingsDialogFragment().apply {
                    arguments = android.os.Bundle().apply {
                        putInt(PlayerSettingsDialogFragment.KEY_INITIAL_COLOR, activity.getColor(R.color.green))
                        putString(PlayerSettingsDialogFragment.KEY_INITIAL_NAME, "Ernesto")
                    }
                }.show(activity.supportFragmentManager, PlayerSettingsDialogFragment.TAG)
            }
            finishAnimations()
        }
        captureScreenRoboImage("src/test/screenshots/player_settings_dialog.png")
    }
}
