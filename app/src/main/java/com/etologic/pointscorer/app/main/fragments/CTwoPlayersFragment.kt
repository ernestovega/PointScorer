package com.etologic.pointscorer.app.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseXPlayersFragment
import com.etologic.pointscorer.databinding.GameCTwoPlayersFragmentBinding

class CTwoPlayersFragment : BaseXPlayersFragment() {
    
    private var fragmentBinding: GameCTwoPlayersFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = GameCTwoPlayersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize2P).toInt()
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop2P).toInt()
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize2P).toInt()
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer21, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer22, nameSize, nameMarginTop, pointsSize)
    }
    
    companion object {
        
        private const val PLAYER_1_ID = 21
        private const val PLAYER_2_ID = 22
    }
}