package com.etologic.pointscorer.app.main.game_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseMainFragment
import com.etologic.pointscorer.app.main.game_fragments.base.BaseGameFragment
import com.etologic.pointscorer.databinding.CTwoPlayersActivityBinding

class CTwoPlayersFragment : BaseGameFragment() {
    
    private var fragmentBinding: CTwoPlayersActivityBinding? = null
    private val binding: CTwoPlayersActivityBinding get() = fragmentBinding!!
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = CTwoPlayersActivityBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.addFlags(FLAG_FULLSCREEN)
//        window.addFlags(FLAG_KEEP_SCREEN_ON)
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize2P).toInt()//24
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop2P).toInt()//2
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize2P).toInt()//80
        
        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer21, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer22, nameSize, nameMarginTop, pointsSize)
    }
    
    companion object {
        
        private const val PLAYER_1_ID = 21
        private const val PLAYER_2_ID = 22
    }
}