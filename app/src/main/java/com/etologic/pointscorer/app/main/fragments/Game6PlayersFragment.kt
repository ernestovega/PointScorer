package com.etologic.pointscorer.app.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseXPlayersFragment
import com.etologic.pointscorer.databinding.GameGSixPlayersFragmentBinding

class Game6PlayersFragment : BaseXPlayersFragment() {
    
    private var fragmentBinding: GameGSixPlayersFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = GameGSixPlayersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    
    companion object {
        
        private const val PLAYER_1_ID = 61
        private const val PLAYER_2_ID = 62
        private const val PLAYER_3_ID = 63
        private const val PLAYER_4_ID = 64
        private const val PLAYER_5_ID = 65
        private const val PLAYER_6_ID = 66
    }
}