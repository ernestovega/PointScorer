package com.etologic.pointscorer.app.main.game_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.main.game_fragments.base.BaseGameFragment
import com.etologic.pointscorer.databinding.IEightPlayersActivityBinding

class IEightPlayersFragment : BaseGameFragment() {
    
    private var fragmentBinding: IEightPlayersActivityBinding? = null
    private val binding: IEightPlayersActivityBinding get() = fragmentBinding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = IEightPlayersActivityBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.addFlags(FLAG_FULLSCREEN)
//        window.addFlags(FLAG_KEEP_SCREEN_ON)
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
}