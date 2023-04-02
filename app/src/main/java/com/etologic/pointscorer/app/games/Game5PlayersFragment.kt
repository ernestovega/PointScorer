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
import com.etologic.pointscorer.databinding.GameEFivePlayersFragmentBinding

class Game5PlayersFragment : GameFragment<GameEFivePlayersFragmentBinding>() {

    companion object {
        private const val GAME_5_PLAYER_1_ID = 51
        private const val GAME_5_PLAYER_2_ID = 52
        private const val GAME_5_PLAYER_3_ID = 53
        private const val GAME_5_PLAYER_4_ID = 54
        private const val GAME_5_PLAYER_5_ID = 55
    }

    override val gamePlayersNum = 5

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        GameEFivePlayersFragmentBinding.inflate(inflater, container, false)

    override fun initPlayers() {
        val playerFragmentInitialData = PlayerFragment.PlayerFragmentInitialData(
            nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize5P).toInt(),
            nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop5P).toInt(),
            pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize5P).toInt(),
            positiveAuxPointsMarginTop = resources.getDimension(R.dimen.positiveAuxPointsMarginTop5P).toInt(),
            auxPointsMargin = resources.getDimension(R.dimen.auxPointsMargin5P).toInt()
        )

        initPlayerFragment(GAME_5_PLAYER_1_ID, R.id.fcvPlayer51, playerFragmentInitialData)
        initPlayerFragment(GAME_5_PLAYER_2_ID, R.id.fcvPlayer52, playerFragmentInitialData)
        initPlayerFragment(GAME_5_PLAYER_3_ID, R.id.fcvPlayer53, playerFragmentInitialData)
        initPlayerFragment(GAME_5_PLAYER_4_ID, R.id.fcvPlayer54, playerFragmentInitialData)
        initPlayerFragment(GAME_5_PLAYER_5_ID, R.id.fcvPlayer55, playerFragmentInitialData)
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
        Game5PlayersFragmentDirections.actionGame5PlayersFragmentToPlayerSettingsMenuDialogFragment(
            playerId = playerSettingsMenuInitialData.playerId,
            playerInitialName = playerSettingsMenuInitialData.playerInitialName,
            playerInitialColor = playerSettingsMenuInitialData.playerInitialColor,
            playerHasCustomBackground = playerSettingsMenuInitialData.playerHasCustomBackground
        )

}