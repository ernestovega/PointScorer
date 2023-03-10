package com.etologic.pointscorer.app.main.fragments.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.base.BaseGameXPlayersFragment
import com.etologic.pointscorer.databinding.GameHSevenPlayersFragmentBinding

class Game7PlayersXPlayersFragment : BaseGameXPlayersFragment() {

    companion object {
        private const val PLAYER_1_ID = 71
        private const val PLAYER_2_ID = 72
        private const val PLAYER_3_ID = 73
        private const val PLAYER_4_ID = 74
        private const val PLAYER_5_ID = 75
        private const val PLAYER_6_ID = 76
        private const val PLAYER_7_ID = 77

        fun getNewInstance(): Game7PlayersXPlayersFragment = Game7PlayersXPlayersFragment()
    }

    private var _binding: GameHSevenPlayersFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize7P).toInt()//14
        val nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop7P).toInt()//8
        val pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize7P).toInt()//48

        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer71, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer72, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer73, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_4_ID, R.id.flPlayer74, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_5_ID, R.id.flPlayer75, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_6_ID, R.id.flPlayer76, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_7_ID, R.id.flPlayer77, nameSize, nameMarginTop, pointsSize)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GameHSevenPlayersFragmentBinding.inflate(inflater, container, false)
        baseBinding = binding
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}