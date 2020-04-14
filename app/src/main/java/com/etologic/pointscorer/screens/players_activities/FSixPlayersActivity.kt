package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import com.etologic.pointscorer.screens.BaseActivity
import com.etologic.pointscorer.R.id
import com.etologic.pointscorer.R.layout

class FSixPlayersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.g_six_players_activity)
        window.addFlags(LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON)
        initPlayer(61, id.flPlayer61, 16, 8, 48)
        initPlayer(62, id.flPlayer62, 16, 8, 48)
        initPlayer(63, id.flPlayer63, 16, 8, 48)
        initPlayer(64, id.flPlayer64, 16, 8, 48)
        initPlayer(65, id.flPlayer65, 16, 8, 48)
        initPlayer(66, id.flPlayer66, 16, 8, 48)
    }
}