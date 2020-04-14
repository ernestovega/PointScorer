package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import com.etologic.pointscorer.screens.BaseActivity
import com.etologic.pointscorer.R.id
import com.etologic.pointscorer.R.layout

class GSevenPlayersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.h_seven_players_activity)
        window.addFlags(LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON)
        initPlayer(71, id.flPlayer71, 14, 8, 48)
        initPlayer(72, id.flPlayer72, 14, 8, 48)
        initPlayer(73, id.flPlayer73, 14, 8, 48)
        initPlayer(74, id.flPlayer74, 14, 8, 48)
        initPlayer(75, id.flPlayer75, 14, 8, 48)
        initPlayer(76, id.flPlayer76, 14, 8, 48)
        initPlayer(77, id.flPlayer77, 14, 8, 48)
    }
}