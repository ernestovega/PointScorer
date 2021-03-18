package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.screens.BaseActivity

class DFourPlayersActivity : BaseActivity() {
    
    companion object {
        
        private const val PLAYER_1_ID = 41
        private const val PLAYER_2_ID = 42
        private const val PLAYER_3_ID = 43
        private const val PLAYER_4_ID = 44
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.e_four_players_activity)
        
        window.addFlags(FLAG_FULLSCREEN)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
    
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize4P).toInt()//16
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop4P).toInt()//Portrait=40 Landscape=4
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize4P).toInt()//48
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer41, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer42, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer43, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_4_ID, R.id.flPlayer44, nameSize, nameMarginTop, pointsSize)
    }
}