package com.etologic.pointscorer.app.main.activity

import android.view.View
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import android.widget.EditText
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.fragments.*
import com.etologic.pointscorer.testutils.playerFragments
import com.etologic.pointscorer.testutils.waitForPlayersLoaded
import com.etologic.pointscorer.testutils.waitUntil
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Fast (JVM) checks of the real activity, Dagger graph and fragments, run with Robolectric.
 * The same flows are covered on a device by the Espresso tests in src/androidTest.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        waitUntil { currentFragment() is MenuFragment }
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    private fun currentFragment() =
        withActivity { supportFragmentManager.findFragmentById(R.id.flMain) }

    private fun <T> withActivity(block: MainActivity.() -> T): T {
        var result: T? = null
        scenario.onActivity { result = it.block() }
        @Suppress("UNCHECKED_CAST")
        return result as T
    }

    private fun click(id: Int) = scenario.onActivity { it.findViewById<View>(id).performClick() }

    private fun keepsScreenOn() = withActivity { window.attributes.flags and FLAG_KEEP_SCREEN_ON != 0 }

    @Test
    fun `the dagger graph builds and the menu is shown`() {
        assertThat(currentFragment()).isInstanceOf(MenuFragment::class.java)
        assertThat(keepsScreenOn()).isFalse()
    }

    @Test
    fun `the menu shows the default initial points`() {
        waitUntil { withActivity { findViewById<EditText>(R.id.etMainInitialPoints).text.toString() } == "100" }
    }

    @Test
    fun `every menu button opens its game with the right number of players`() {
        val games = listOf(
            R.id.btMain1Player to Game1PlayerFragment::class.java,
            R.id.btMain2Player to Game2PlayersFragment::class.java,
            R.id.btMain3Player to Game3PlayersFragment::class.java,
            R.id.btMain4Player to Game4PlayersFragment::class.java,
            R.id.btMain5Player to Game5PlayersFragment::class.java,
            R.id.btMain6Player to Game6PlayersFragment::class.java,
            R.id.btMain7Player to Game7PlayersFragment::class.java,
            R.id.btMain8Player to Game8PlayersFragment::class.java,
        )
        games.forEachIndexed { index, (button, gameClass) ->
            click(button)
            waitUntil { gameClass.isInstance(currentFragment()) }
            withActivity { waitForPlayersLoaded(index + 1) }
            assertThat(keepsScreenOn()).isTrue()

            withActivity { onBackPressedDispatcher.onBackPressed() }
            waitUntil { currentFragment() is MenuFragment && withActivity { playerFragments().isEmpty() } }
            assertThat(keepsScreenOn()).isFalse()
        }
    }

    @Test
    fun `players start with the initial points chosen in the menu`() {
        waitUntil { withActivity { findViewById<EditText>(R.id.etMainInitialPoints).text.isNotEmpty() } }
        scenario.onActivity { it.findViewById<EditText>(R.id.etMainInitialPoints).setText("40") }

        click(R.id.btMain2Player)
        withActivity { waitForPlayersLoaded(2) }

        val points = withActivity {
            playerFragments().map { it.requireView().findViewById<TextView>(R.id.tvPointsPlayer).text.toString() }
        }
        assertThat(points).containsExactly("40", "40")
    }

    @Test
    fun `back on the menu is left to the system`() {
        // The callback is disabled on the menu so Android can play the predictive back-to-home animation
        assertThat(withActivity { onBackPressedDispatcher.hasEnabledCallbacks() }).isFalse()
    }

    @Test
    fun `back from a game goes to the menu and back again closes the app`() {
        click(R.id.btMain1Player)
        waitUntil { currentFragment() is Game1PlayerFragment }
        assertThat(withActivity { onBackPressedDispatcher.hasEnabledCallbacks() }).isTrue()

        withActivity { onBackPressedDispatcher.onBackPressed() }
        waitUntil { currentFragment() is MenuFragment }

        val activity = withActivity { this }
        scenario.onActivity { it.viewModelForTest().navigateBack() }
        waitUntil { activity.isFinishing }
    }

    private fun MainActivity.viewModelForTest(): MainActivityViewModel =
        androidx.lifecycle.ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
}
