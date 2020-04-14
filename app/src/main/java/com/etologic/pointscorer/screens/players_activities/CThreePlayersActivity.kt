package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import com.etologic.pointscorer.screens.BaseActivity
import com.etologic.pointscorer.R.id
import com.etologic.pointscorer.R.layout

class CThreePlayersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.d_three_players_activity)
        window.addFlags(LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON)
        initPlayer(31, id.flPlayer31, 20, 20, 64)
        initPlayer(32, id.flPlayer32, 20, 20, 64)
        initPlayer(33, id.flPlayer33, 20, 20, 64)
    }
}