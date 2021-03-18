package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.screens.BaseActivity

class FSixPlayersActivity : BaseActivity() {
    
    companion object {
        
        private const val PLAYER_1_ID = 61
        private const val PLAYER_2_ID = 62
        private const val PLAYER_3_ID = 63
        private const val PLAYER_4_ID = 64
        private const val PLAYER_5_ID = 65
        private const val PLAYER_6_ID = 66
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.g_six_players_activity)
        
        window.addFlags(FLAG_FULLSCREEN)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
    
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize6P).toInt()//16
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop6P).toInt()//8
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize6P).toInt()//48
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer61, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer62, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer63, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_4_ID, R.id.flPlayer64, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_5_ID, R.id.flPlayer65, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_6_ID, R.id.flPlayer66, nameSize, nameMarginTop, pointsSize)
    }
}