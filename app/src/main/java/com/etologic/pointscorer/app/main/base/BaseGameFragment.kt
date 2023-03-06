package com.etologic.pointscorer.app.main.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Screens.GAME_INTERSTITIAL_COUNTDOWN
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment

abstract class BaseGameFragment : BaseMainFragmentWithAds() {

    companion object {
        private const val ONE_MINUTE_IN_MILLIS = 1 * 1000L
        private const val TOKEN_HANDLER_GAME_AD = "TOKEN_HANDLER_GAME_AD"
    }

    private val gameAdHandler: Handler = Handler(Looper.getMainLooper())
    private var gameAdHandlerRunnable: Runnable? = null

    protected fun initPlayerFragment(
        playerId: Int,
        frameLayout: Int,
        nameSize: Int,
        nameMarginTop: Int,
        pointsSize: Int
    ) {
        val bundle = Bundle()
        bundle.putInt(PlayerFragment.KEY_PLAYER_ID, playerId)
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_SIZE, nameSize)
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_MARGIN_TOP, nameMarginTop)
        bundle.putInt(PlayerFragment.KEY_PLAYER_POINTS_SIZE, pointsSize)

        val fragment = PlayerFragment().apply {
            arguments = bundle
            setOnPointsSavedCallback { restartCountDownToShowAd() }
        }

        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .add(frameLayout, fragment)
            .commit()
    }

    private fun restartCountDownToShowAd() {
        gameAdHandler.removeCallbacksAndMessages(TOKEN_HANDLER_GAME_AD)
        gameAdHandlerRunnable = gameAdHandler.postDelayed(
            ONE_MINUTE_IN_MILLIS,
            TOKEN_HANDLER_GAME_AD
        ) { activityViewModel.navigateTo(GAME_INTERSTITIAL_COUNTDOWN) }
    }

    override fun onDestroy() {
        gameAdHandler.removeCallbacksAndMessages(TOKEN_HANDLER_GAME_AD)
        super.onDestroy()
    }
}
