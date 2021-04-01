package com.etologic.pointscorer.app.main.game_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.main.game_fragments.base.BaseGameFragment
import com.etologic.pointscorer.databinding.DThreePlayersActivityBinding

class DThreePlayersFragment : BaseGameFragment() {
    
    private var fragmentBinding: DThreePlayersActivityBinding? = null
    private val binding: DThreePlayersActivityBinding get() = fragmentBinding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = DThreePlayersActivityBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.addFlags(FLAG_FULLSCREEN)
//        window.addFlags(FLAG_KEEP_SCREEN_ON)
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize3P).toInt()//20
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop3P).toInt()//20
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize3P).toInt()//64
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer31, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer32, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer33, nameSize, nameMarginTop, pointsSize)
    }
    
    companion object {
        
        private const val PLAYER_1_ID = 31
        private const val PLAYER_2_ID = 32
        private const val PLAYER_3_ID = 33
    }
}