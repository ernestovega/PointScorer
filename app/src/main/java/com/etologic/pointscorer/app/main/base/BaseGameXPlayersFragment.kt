package com.etologic.pointscorer.app.main.base

import android.os.Bundle
import android.os.CountDownTimer
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment
import com.etologic.pointscorer.app.main.fragments.players.AdWillBeShownDialogFragment
import com.etologic.pointscorer.common.Constants

abstract class BaseGameXPlayersFragment : BaseMainFragmentWithAds() {

    companion object {
        private const val AD_COUNT_DOWN_DURATION = 60 * Constants.A_MINUTE_IN_MILLIS
        private const val AD_COUNT_DOWN_INTERVAL = AD_COUNT_DOWN_DURATION
    }

    private val countDownTimer = object : CountDownTimer(AD_COUNT_DOWN_DURATION, AD_COUNT_DOWN_INTERVAL) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            if (activityViewModel.shouldShowGameInterstitialAd) {
                activityViewModel.shouldShowGameInterstitialAd = false
                AdWillBeShownDialogFragment
                    .newInstance()
                    .show(requireActivity().supportFragmentManager, AdWillBeShownDialogFragment.TAG)
            }
        }
    }

    fun restartAdCountDown() {
        countDownTimer.cancel()
        if (activityViewModel.shouldShowGameInterstitialAd) {
            countDownTimer.start()
        }
    }

    override fun onPause() {
        countDownTimer.cancel()
        super.onPause()
    }

    protected fun initPlayerFragment(
        playerId: Int,
        frameLayout: Int,
        nameSize: Int,
        nameMarginTop: Int,
        pointsSize: Int
    ) {
        val bundle = Bundle().apply {
            putInt(PlayerFragment.KEY_PLAYER_ID, playerId)
            putInt(PlayerFragment.KEY_PLAYER_NAME_SIZE, nameSize)
            putInt(PlayerFragment.KEY_PLAYER_NAME_MARGIN_TOP, nameMarginTop)
            putInt(PlayerFragment.KEY_PLAYER_POINTS_SIZE, pointsSize)
        }
        val playerFragmentListener = object : PlayerFragment.PlayerFragmentListener {
            override fun onPointsTouched() {
                restartAdCountDown()
            }
        }
        val fragment = PlayerFragment.getNewInstance(bundle, playerFragmentListener)
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .add(frameLayout, fragment)
            .commit()
    }
}
