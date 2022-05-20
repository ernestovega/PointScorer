package com.etologic.pointscorer.app.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseXPlayersFragment
import com.etologic.pointscorer.databinding.GameCTwoPlayersFragmentBinding

class Game2PlayersFragment : BaseXPlayersFragment() {
    
    private var _binding: GameCTwoPlayersFragmentBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GameCTwoPlayersFragmentBinding.inflate(inflater, container, false)
        baseBinding = binding
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize2P).toInt()
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop2P).toInt()
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize2P).toInt()
        
        initPlayerFragment(GAME_2_PLAYER_1_ID, R.id.flPlayer21, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(GAME_2_PLAYER_2_ID, R.id.flPlayer22, nameSize, nameMarginTop, pointsSize)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
    
    companion object {
        
        const val GAME_2_PLAYER_1_ID = 21
        const val GAME_2_PLAYER_2_ID = 22
    }
}