package com.etologic.pointscorer.app.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavDirections
import com.etologic.pointscorer.R
import com.etologic.pointscorer.app.games.base.game.GameFragment
import com.etologic.pointscorer.app.games.base.game.GameViewModel
import com.etologic.pointscorer.app.games.base.player.PlayerFragment
import com.etologic.pointscorer.databinding.GameGSevenPlayersFragmentBinding

class Game7PlayersFragment : GameFragment<GameGSevenPlayersFragmentBinding>() {

    companion object {
        private const val GAME_7_PLAYER_1_ID = 71
        private const val GAME_7_PLAYER_2_ID = 72
        private const val GAME_7_PLAYER_3_ID = 73
        private const val GAME_7_PLAYER_4_ID = 74
        private const val GAME_7_PLAYER_5_ID = 75
        private const val GAME_7_PLAYER_6_ID = 76
        private const val GAME_7_PLAYER_7_ID = 77
    }

    override val gamePlayersNum = 7

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        GameGSevenPlayersFragmentBinding.inflate(inflater, container, false)

    override fun initPlayers() {
        val playerFragmentInitialData = PlayerFragment.PlayerFragmentInitialData(
            nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize7P).toInt(),
            nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop7P).toInt(),
            pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize7P).toInt(),
            positiveAuxPointsMarginTop = resources.getDimension(R.dimen.positiveAuxPointsMarginTop7P).toInt(),
            auxPointsMargin = resources.getDimension(R.dimen.auxPointsMargin7P).toInt()
        )

        initPlayerFragment(GAME_7_PLAYER_1_ID, R.id.fcvPlayer71, playerFragmentInitialData)
        initPlayerFragment(GAME_7_PLAYER_2_ID, R.id.fcvPlayer72, playerFragmentInitialData)
        initPlayerFragment(GAME_7_PLAYER_3_ID, R.id.fcvPlayer73, playerFragmentInitialData)
        initPlayerFragment(GAME_7_PLAYER_4_ID, R.id.fcvPlayer74, playerFragmentInitialData)
        initPlayerFragment(GAME_7_PLAYER_5_ID, R.id.fcvPlayer75, playerFragmentInitialData)
        initPlayerFragment(GAME_7_PLAYER_6_ID, R.id.fcvPlayer76, playerFragmentInitialData)
        initPlayerFragment(GAME_7_PLAYER_7_ID, R.id.fcvPlayer77, playerFragmentInitialData)
    }

    override fun initSpecificViews() {
        binding.fabGamePlayerSettings.setOnClickListener {
            askConfirmRestoreAllPlayersPoints()
        }
    }

    override fun getAdsContainer(): LinearLayout = binding.llAdsContainer

    override fun getPlayerSettingsMenuNavAction(
        playerSettingsMenuInitialData: GameViewModel.PlayerSettingsMenuInitialData
    ): NavDirections =
        Game7PlayersFragmentDirections.actionGame7PlayersFragmentToPlayerSettingsMenuDialogFragment(
            playerId = playerSettingsMenuInitialData.playerId,
            playerInitialName = playerSettingsMenuInitialData.playerInitialName,
            playerInitialColor = playerSettingsMenuInitialData.playerInitialColor,
            playerHasCustomBackground = playerSettingsMenuInitialData.playerHasCustomBackground
        )

}