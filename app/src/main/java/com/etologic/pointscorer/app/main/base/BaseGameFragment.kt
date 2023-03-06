package com.etologic.pointscorer.app.main.base

import android.os.Bundle
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment

abstract class BaseGameFragment : BaseMainFragmentWithAds() {

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
            setOnPointsSavedCallback { activityViewModel.restartCountDownToShowGameAd() }
        }

        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .add(frameLayout, fragment)
            .commit()
    }
}
