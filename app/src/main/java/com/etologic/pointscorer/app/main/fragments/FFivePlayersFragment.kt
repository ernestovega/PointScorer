package com.etologic.pointscorer.app.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseXPlayersFragment
import com.etologic.pointscorer.databinding.GameFFivePlayersFragmentBinding

class FFivePlayersFragment : BaseXPlayersFragment() {
    
    private var fragmentBinding: GameFFivePlayersFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = GameFFivePlayersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize5P).toInt()//16
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop5P).toInt()//8
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize5P).toInt()//48
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer51, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer52, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer53, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_4_ID, R.id.flPlayer54, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_5_ID, R.id.flPlayer55, nameSize, nameMarginTop, pointsSize)
    }
    
    companion object {
        
        private const val PLAYER_1_ID = 51
        private const val PLAYER_2_ID = 52
        private const val PLAYER_3_ID = 53
        private const val PLAYER_4_ID = 54
        private const val PLAYER_5_ID = 55
    }
}