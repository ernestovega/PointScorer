package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import com.etologic.pointscorer.screens.BaseActivity
import com.etologic.pointscorer.R.id
import com.etologic.pointscorer.R.layout

class EFivePlayersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.f_five_players_activity)
        window.addFlags(LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON)
        initPlayer(51, id.flPlayer51, 16, 8, 48)
        initPlayer(52, id.flPlayer52, 16, 8, 48)
        initPlayer(53, id.flPlayer53, 16, 8, 48)
        initPlayer(54, id.flPlayer54, 16, 8, 48)
        initPlayer(55, id.flPlayer55, 16, 8, 48)
    }
}