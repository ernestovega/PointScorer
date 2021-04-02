package com.etologic.pointscorer.app.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseXPlayersFragment
import com.etologic.pointscorer.databinding.GameDThreePlayersFragmentBinding

class DThreePlayersFragment : BaseXPlayersFragment() {
    
    private var fragmentBinding: GameDThreePlayersFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = GameDThreePlayersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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