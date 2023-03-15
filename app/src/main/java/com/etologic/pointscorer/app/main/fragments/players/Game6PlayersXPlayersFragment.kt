package com.etologic.pointscorer.app.main.fragments.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.main.fragments.players.base.BaseGameXPlayersFragment
import com.etologic.pointscorer.databinding.GameGSixPlayersFragmentBinding

class Game6PlayersXPlayersFragment : BaseGameXPlayersFragment() {

    companion object {
        private const val PLAYER_1_ID = 61
        private const val PLAYER_2_ID = 62
        private const val PLAYER_3_ID = 63
        private const val PLAYER_4_ID = 64
        private const val PLAYER_5_ID = 65
        private const val PLAYER_6_ID = 66

        fun getNewInstance(): Game6PlayersXPlayersFragment = Game6PlayersXPlayersFragment()
    }

    private var _binding: GameGSixPlayersFragmentBinding? = null
    private val binding get() = _binding!!

    override val gamePlayersNum = 6
    override val fragmentId = R.id.game6PlayersXPlayersFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize6P).toInt()//16
        nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop6P).toInt()//8
        pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize6P).toInt()//48

        initPlayerFragment(PLAYER_1_ID, R.id.flPlayer61, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_2_ID, R.id.flPlayer62, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_3_ID, R.id.flPlayer63, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_4_ID, R.id.flPlayer64, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_5_ID, R.id.flPlayer65, nameSize, nameMarginTop, pointsSize)
        initPlayerFragment(PLAYER_6_ID, R.id.flPlayer66, nameSize, nameMarginTop, pointsSize)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GameGSixPlayersFragmentBinding.inflate(inflater, container, false)
        baseBinding = binding
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}