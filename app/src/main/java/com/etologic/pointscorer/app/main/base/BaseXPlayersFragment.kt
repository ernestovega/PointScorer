package com.etologic.pointscorer.app.main.base

import android.R.animator
import android.content.res.Configuration
import android.os.Bundle
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment

abstract class BaseXPlayersFragment : BaseMainFragment() {
    
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
        val fragment = PlayerFragment()
        fragment.arguments = bundle
        
        requireFragmentManager()
            .beginTransaction()
            .setCustomAnimations(animator.fade_in, animator.fade_out)
            .add(frameLayout, fragment)
            .commit()
    }
}
