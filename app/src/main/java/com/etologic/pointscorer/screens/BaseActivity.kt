package com.etologic.pointscorer.screens

import android.R.animator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.etologic.pointscorer.screens.PlayerFragment

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected fun initPlayer(playerId: Int, frameLayout: Int, nameSize: Int, playerNameMarginTop: Int, pointsSize: Int) {
        val fragment = PlayerFragment()
        val bundle = Bundle()
        bundle.putInt(PlayerFragment.KEY_PLAYER_ID, playerId)
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_SIZE, nameSize)
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_MARGIN_TOP, playerNameMarginTop)
        bundle.putInt(PlayerFragment.KEY_PLAYER_POINTS_SIZE, pointsSize)
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(animator.fade_in, animator.fade_out)
            .add(frameLayout, fragment)
            .commit()
    }
}