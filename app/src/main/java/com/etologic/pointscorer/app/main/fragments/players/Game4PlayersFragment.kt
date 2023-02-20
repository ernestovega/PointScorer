package com.etologic.pointscorer.app.main.fragments.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseXPlayersFragment
import com.etologic.pointscorer.databinding.GameEFourPlayersFragmentBinding

class Game4PlayersFragment : BaseXPlayersFragment() {

    private var _binding: GameEFourPlayersFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameEFourPlayersFragmentBinding.inflate(inflater, container, false)
        baseBinding = binding
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize4P).toInt()//16
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop4P)
            .toInt()//Portrait=40 Landscape=4
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize4P).toInt()//48

        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer41, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer42, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer43, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_4_ID, R.id.flPlayer44, nameSize, nameMarginTop, pointsSize)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        private const val PLAYER_1_ID = 41
        private const val PLAYER_2_ID = 42
        private const val PLAYER_3_ID = 43
        private const val PLAYER_4_ID = 44
    }
}