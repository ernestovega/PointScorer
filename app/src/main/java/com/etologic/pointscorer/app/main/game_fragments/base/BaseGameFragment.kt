package com.etologic.pointscorer.app.main.game_fragments.base

import android.R.animator
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.app.main.MainActivityViewModel
import com.etologic.pointscorer.app.main.MainActivityViewModelFactory
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.player.PlayerFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseGameFragment : BaseMainFragment() {
    
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
