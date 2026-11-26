package com.etologic.pointscorer.app.main.fragments.player

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.activity.MainActivity
import com.etologic.pointscorer.app.main.fragments.MenuFragment
import com.etologic.pointscorer.testutils.playerFragments
import com.etologic.pointscorer.testutils.waitForPlayersLoaded
import com.etologic.pointscorer.testutils.waitUntil
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/** Robolectric tests of the player shield inside a real 2 players game. */
@RunWith(AndroidJUnit4::class)
class PlayerFragmentTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        waitUntil { onActivity { supportFragmentManager.findFragmentById(R.id.flMain) is MenuFragment } }
        scenario.onActivity { it.findViewById<View>(R.id.btMain2Player).performClick() }
        onActivity { waitForPlayersLoaded(2) }
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    private fun <T> onActivity(block: MainActivity.() -> T): T {
        var result: T? = null
        scenario.onActivity { result = it.block() }
        @Suppress("UNCHECKED_CAST")
        return result as T
    }

    private fun player(index: Int): PlayerFragment =
        onActivity { playerFragments().sortedBy { it.requireArguments().getInt(PlayerFragment.KEY_PLAYER_ID) }[index] }

    private fun PlayerFragment.points() = onActivity { requireView().findViewById<TextView>(R.id.tvPointsPlayer).text.toString() }
    private fun PlayerFragment.upCount() = onActivity { requireView().findViewById<TextView>(R.id.tvUpCount).text.toString() }
    private fun PlayerFragment.downCount() = onActivity { requireView().findViewById<TextView>(R.id.tvDownCount).text.toString() }
    private fun PlayerFragment.click(id: Int) = scenario.onActivity { requireView().findViewById<View>(id).performClick() }

    @Test
    fun `each player gets its own id`() {
        assertThat(player(0).requireArguments().getInt(PlayerFragment.KEY_PLAYER_ID)).isEqualTo(21)
        assertThat(player(1).requireArguments().getInt(PlayerFragment.KEY_PLAYER_ID)).isEqualTo(22)
    }

    @Test
    fun `up adds a point and shows the +count`() {
        val first = player(0)

        first.click(R.id.btUp)
        waitUntil { first.points() == "101" }
        first.click(R.id.btUp)
        waitUntil { first.points() == "102" }

        assertThat(first.upCount()).isEqualTo("+2")
        assertThat(player(1).points()).isEqualTo("100")
    }

    @Test
    fun `down removes a point and shows the -count`() {
        val second = player(1)

        second.click(R.id.btDown)
        waitUntil { second.points() == "99" }

        assertThat(second.downCount()).isEqualTo("-1")
        assertThat(player(0).points()).isEqualTo("100")
    }

    @Test
    fun `the default name and white color are shown`() {
        val first = player(0)
        val context = first.requireContext()
        val name = onActivity { first.requireView().findViewById<EditText>(R.id.etName) }

        assertThat(name.text.toString()).isEqualTo(context.getString(R.string.player_name))
        assertThat(name.currentTextColor).isEqualTo(ContextCompat.getColor(context, R.color.white))
    }

    @Test
    fun `points survive leaving and reopening the game`() {
        player(0).click(R.id.btUp)
        waitUntil { player(0).points() == "101" }

        onActivity { onBackPressedDispatcher.onBackPressed() }
        waitUntil { onActivity { playerFragments().isEmpty() } }
        scenario.onActivity { it.findViewById<View>(R.id.btMain2Player).performClick() }
        onActivity { waitForPlayersLoaded(2) }

        assertThat(player(0).points()).isEqualTo("101")
        assertThat(player(1).points()).isEqualTo("100")
    }
}
