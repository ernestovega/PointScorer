package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.screens.BaseActivity

class HEightPlayersActivity : BaseActivity() {
    
    companion object {
        
        private const val PLAYER_1_ID = 81
        private const val PLAYER_2_ID = 82
        private const val PLAYER_3_ID = 83
        private const val PLAYER_4_ID = 84
        private const val PLAYER_5_ID = 85
        private const val PLAYER_6_ID = 86
        private const val PLAYER_7_ID = 87
        private const val PLAYER_8_ID = 88
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.i_eight_players_activity)
        
        window.addFlags(FLAG_FULLSCREEN)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
    
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize8P).toInt()//14
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop8P).toInt()//8
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize8P).toInt()//48
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer81, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer82, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer83, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_4_ID, R.id.flPlayer84, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_5_ID, R.id.flPlayer85, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_6_ID, R.id.flPlayer86, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_7_ID, R.id.flPlayer87, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_8_ID, R.id.flPlayer88, nameSize, nameMarginTop, pointsSize)
    }
}