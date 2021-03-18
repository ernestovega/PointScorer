package com.etologic.pointscorer.screens

import android.R.animator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    
    protected fun initPlayerFragment(
        playerId: Int,
        frameLayout: Int,
        nameSize: Int,
        nameMarginTop: Int,
        pointsSize: Int
    ) {
        val fragment = PlayerFragment()
        fragment.arguments = buildBundle(playerId, nameSize, nameMarginTop, pointsSize)
        startFragment(frameLayout, fragment)
    }
    
    private fun startFragment(frameLayout: Int, fragment: PlayerFragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(animator.fade_in, animator.fade_out)
            .add(frameLayout, fragment)
            .commit()
    }
    
    private fun buildBundle(
        playerId: Int,
        nameSize: Int,
        playerNameMarginTop: Int,
        pointsSize: Int
    ): Bundle {
        val bundle = Bundle()
        bundle.putInt(PlayerFragment.KEY_PLAYER_ID, playerId)
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_SIZE, nameSize)
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_MARGIN_TOP, playerNameMarginTop)
        bundle.putInt(PlayerFragment.KEY_PLAYER_POINTS_SIZE, pointsSize)
        return bundle
    }
}