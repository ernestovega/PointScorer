package com.etologic.pointscorer.screens.players_activities

import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.screens.BaseActivity

class AOnePlayerActivity : BaseActivity() {
    
    companion object {
        
        private const val PLAYER_1_ID = 11
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.b_one_player_activity)
        
        window.addFlags(FLAG_FULLSCREEN)
        window.addFlags(FLAG_KEEP_SCREEN_ON)
    
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize1P).toInt()//36
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop1P).toInt()//48
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize1P).toInt()//100
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer11, nameSize, nameMarginTop, pointsSize)
    }
}