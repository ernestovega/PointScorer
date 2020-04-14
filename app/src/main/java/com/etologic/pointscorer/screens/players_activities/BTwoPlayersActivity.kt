package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import com.etologic.pointscorer.screens.BaseActivity
import com.etologic.pointscorer.R.id
import com.etologic.pointscorer.R.layout

class BTwoPlayersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.c_two_players_activity)
        window.addFlags(LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON)
        initPlayer(21, id.flPlayer21, 24, 2, 80)
        initPlayer(22, id.flPlayer22, 24, 2, 80)
    }
}