package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.screens.BaseActivity

class CThreePlayersActivity : BaseActivity() {
    
    companion object {
        
        private const val PLAYER_1_ID = 31
        private const val PLAYER_2_ID = 32
        private const val PLAYER_3_ID = 33
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.d_three_players_activity)
        
        window.addFlags(FLAG_FULLSCREEN)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
    
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize3P).toInt()//20
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop3P).toInt()//20
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize3P).toInt()//64
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer31, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer32, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer33, nameSize, nameMarginTop, pointsSize)
    }
}