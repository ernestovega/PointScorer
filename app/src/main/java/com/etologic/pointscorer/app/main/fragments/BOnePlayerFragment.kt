package com.etologic.pointscorer.app.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseXPlayersFragment
import com.etologic.pointscorer.databinding.GameBOnePlayerFragmentBinding

class BOnePlayerFragment : BaseXPlayersFragment() {
    
    private var fragmentBinding: GameBOnePlayerFragmentBinding? = null
    private val binding get() = fragmentBinding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = GameBOnePlayerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize1P).toInt()//36
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop1P).toInt()//48
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize1P).toInt()//100
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer11, nameSize, nameMarginTop, pointsSize)
        //ToDo: requireActivity().window.addFlags(FLAG_KEEP_SCREEN_ON)
    }
    
    companion object {
        
        private const val PLAYER_1_ID = 11
    }
}