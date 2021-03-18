package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.screens.BaseActivity

class EFivePlayersActivity : BaseActivity() {
    
    companion object {
        
        private const val PLAYER_1_ID = 51
        private const val PLAYER_2_ID = 52
        private const val PLAYER_3_ID = 53
        private const val PLAYER_4_ID = 54
        private const val PLAYER_5_ID = 55
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.f_five_players_activity)
        
        window.addFlags(FLAG_FULLSCREEN)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
    
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize5P).toInt()//16
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop5P).toInt()//8
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize5P).toInt()//48
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer51, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer52, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer53, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_4_ID, R.id.flPlayer54, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_5_ID, R.id.flPlayer55, nameSize, nameMarginTop, pointsSize)
    }
}