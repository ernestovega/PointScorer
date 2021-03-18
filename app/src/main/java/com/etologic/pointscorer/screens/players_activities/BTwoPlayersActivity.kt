package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.screens.BaseActivity

class BTwoPlayersActivity : BaseActivity() {
    
    companion object {
        
        private const val PLAYER_1_ID = 21
        private const val PLAYER_2_ID = 22
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.c_two_players_activity)
        
        window.addFlags(FLAG_FULLSCREEN)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
    
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize2P).toInt()//24
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop2P).toInt()//2
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize2P).toInt()//80
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer21, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer22, nameSize, nameMarginTop, pointsSize)
    }
}