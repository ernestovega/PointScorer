package com.etologic.pointscorer.app.main.fragments.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseGameFragment
import com.etologic.pointscorer.databinding.GameBOnePlayerFragmentBinding

class Game1PlayerFragment : BaseGameFragment() {

    private var _binding: GameBOnePlayerFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameBOnePlayerFragmentBinding.inflate(inflater, container, false)
        baseBinding = binding
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize1P).toInt()//36
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop1P).toInt()//48
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize1P).toInt()//100

        initPlayerFragment(GAME_1_PLAYER_1_ID, R.id.flPlayer11, nameSize, nameMarginTop, pointsSize)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        const val GAME_1_PLAYER_1_ID = 11

        fun getNewInstance(): Game1PlayerFragment = Game1PlayerFragment()
    }
}