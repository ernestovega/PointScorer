package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.screens.BaseActivity

class GSevenPlayersActivity : BaseActivity() {
    
    companion object {
        
        private const val PLAYER_1_ID = 71
        private const val PLAYER_2_ID = 72
        private const val PLAYER_3_ID = 73
        private const val PLAYER_4_ID = 74
        private const val PLAYER_5_ID = 75
        private const val PLAYER_6_ID = 76
        private const val PLAYER_7_ID = 77
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.h_seven_players_activity)
        
        window.addFlags(FLAG_FULLSCREEN)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
    
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize7P).toInt()//14
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop7P).toInt()//8
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize7P).toInt()//48
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer71, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer72, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer73, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_4_ID, R.id.flPlayer74, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_5_ID, R.id.flPlayer75, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_6_ID, R.id.flPlayer76, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_7_ID, R.id.flPlayer77, nameSize, nameMarginTop, pointsSize)
    }
}