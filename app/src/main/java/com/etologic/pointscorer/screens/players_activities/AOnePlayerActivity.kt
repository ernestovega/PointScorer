package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import com.etologic.pointscorer.screens.BaseActivity
import com.etologic.pointscorer.R.id
import com.etologic.pointscorer.R.layout

class AOnePlayerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.b_one_player_activity)
        window.addFlags(LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON)
        initPlayer(11, id.flPlayer11, 36, 48, 100)
    }
}