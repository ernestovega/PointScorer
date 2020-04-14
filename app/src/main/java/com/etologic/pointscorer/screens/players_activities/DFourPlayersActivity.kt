package com.etologic.pointscorer.screens.players_activities

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager.LayoutParams
import com.etologic.pointscorer.screens.BaseActivity
import com.etologic.pointscorer.R.id
import com.etologic.pointscorer.R.layout

class DFourPlayersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.e_four_players_activity)
        window.addFlags(LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON)
        val nameMarginTop =
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 40
        initPlayer(41, id.flPlayer41, 16, nameMarginTop, 48)
        initPlayer(42, id.flPlayer42, 16, nameMarginTop, 48)
        initPlayer(43, id.flPlayer43, 16, nameMarginTop, 48)
        initPlayer(44, id.flPlayer44, 16, nameMarginTop, 48)
    }
}