package com.etologic.pointscorer.app.main.fragments.players.base

import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseMainFragmentWithAds
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment
import com.etologic.pointscorer.common.Constants.A_MINUTE_IN_MILLIS

abstract class BaseGameXPlayersFragment : BaseMainFragmentWithAds() {

    abstract val gamePlayersNum: Int
    abstract val fragmentId: Int
    protected open var nameSize: Int? = null
    protected open var nameMarginTop: Int? = null
    protected open var pointsSize: Int? = null
    private val countDownDuration = if (BuildConfig.DEBUG) 5000L else A_MINUTE_IN_MILLIS
    private val adCountDownInterval = countDownDuration
    private val countDownTimer = object : CountDownTimer(countDownDuration, adCountDownInterval) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            if (activityViewModel.shouldShowGameInterstitialAd) {
                activityViewModel.shouldShowGameInterstitialAd = false
                findNavController().navigate(R.id.adWillBeShownDialogFragment)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onPause() {
        countDownTimer.cancel()
        super.onPause()
    }

    protected fun initPlayerFragment(
        playerId: Int,
        frameLayout: Int,
        nameSize: Int?,
        nameMarginTop: Int?,
        pointsSize: Int?
    ) {

        fun restartAdCountDown() {
            countDownTimer.cancel()
            if (activityViewModel.shouldShowGameInterstitialAd) {
                countDownTimer.start()
            }
        }

        val bundle = Bundle().apply {
            putInt(PlayerFragment.KEY_PLAYER_ID, playerId)
            putInt(PlayerFragment.KEY_FRAGMENT_ID, fragmentId)
            nameSize?.let { putInt(PlayerFragment.KEY_PLAYER_NAME_SIZE, it) }
            nameMarginTop?.let { putInt(PlayerFragment.KEY_PLAYER_NAME_MARGIN_TOP, it) }
            pointsSize?.let { putInt(PlayerFragment.KEY_PLAYER_POINTS_SIZE, it) }
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

    fun askConfirmRestoreAllPlayersPoints() {
        AlertDialog.Builder(requireContext(), R.style.Theme_AppCompat_Light_Dialog)
            .setTitle(R.string.are_you_sure)
            .setMessage(
                String.format(
                    getString(R.string.this_will_restore_everyone_points_in_this_screen_to_),
                    activityViewModel.initialPoints
                )
            )
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ok) { _, _ ->
                activityViewModel.restoreGamePoints(gamePlayersNum)
            }
            .create()
            .show()
    }
}
