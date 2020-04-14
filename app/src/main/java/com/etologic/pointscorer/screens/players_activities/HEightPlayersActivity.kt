package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import com.etologic.pointscorer.screens.BaseActivity
import com.etologic.pointscorer.R.id
import com.etologic.pointscorer.R.layout

class HEightPlayersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.i_eight_players_activity)
        window.addFlags(LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON)
        initPlayer(81, id.flPlayer81, 14, 8, 48)
        initPlayer(82, id.flPlayer82, 14, 8, 48)
        initPlayer(83, id.flPlayer83, 14, 8, 48)
        initPlayer(84, id.flPlayer84, 14, 8, 48)
        initPlayer(85, id.flPlayer85, 14, 8, 48)
        initPlayer(86, id.flPlayer86, 14, 8, 48)
        initPlayer(87, id.flPlayer87, 14, 8, 48)
        initPlayer(88, id.flPlayer88, 14, 8, 48)
    }
}