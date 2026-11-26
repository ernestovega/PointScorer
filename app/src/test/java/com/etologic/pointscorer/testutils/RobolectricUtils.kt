package com.etologic.pointscorer.testutils

import android.os.Looper
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment
import org.robolectric.Shadows.shadowOf
import java.time.Duration

/**
 * The repository reads SharedPreferences on Dispatchers.IO (real threads) and posts back to the
 * main looper, which Robolectric keeps paused. This idles the main looper until [condition] holds.
 */
fun waitUntil(timeoutMs: Long = 5_000, describe: () -> String = { "" }, condition: () -> Boolean) {
    val end = System.currentTimeMillis() + timeoutMs
    while (System.currentTimeMillis() < end) {
        shadowOf(Looper.getMainLooper()).idle()
        if (condition()) return
        Thread.sleep(10)
    }
    throw AssertionError("Condition not met within ${timeoutMs}ms ${describe()}")
}

/** Lets the shield / fade-out view animations of the game screens finish. */
fun finishAnimations() {
    shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(6))
}

/** Every PlayerFragment alive in the activity, at any depth (they are children of the game fragment). */
fun FragmentActivity.playerFragments(): List<PlayerFragment> {
    fun Fragment.withChildren(): List<Fragment> = listOf(this) + childFragmentManager.fragments.flatMap { it.withChildren() }
    return supportFragmentManager.fragments.flatMap { it.withChildren() }.filterIsInstance<PlayerFragment>()
}

/** Waits until [playersNum] player fragments are on screen with their points loaded. */
fun FragmentActivity.waitForPlayersLoaded(playersNum: Int) {
    waitUntil {
        val players = playerFragments()
        players.size == playersNum && players.all {
            it.view?.findViewById<TextView>(R.id.tvPointsPlayer)?.text?.isNotEmpty() == true
        }
    }
}
