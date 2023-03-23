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
import com.etologic.pointscorer.app.games.game_h_eight_players.Game8PlayersFragmentDirections
import com.etologic.pointscorer.databinding.GameHEightPlayersFragmentBinding

class Game8PlayersFragment : GameFragment<GameHEightPlayersFragmentBinding>() {

    companion object {
        private const val GAME_8_PLAYER_1_ID = 81
        private const val GAME_8_PLAYER_2_ID = 82
        private const val GAME_8_PLAYER_3_ID = 83
        private const val GAME_8_PLAYER_4_ID = 84
        private const val GAME_8_PLAYER_5_ID = 85
        private const val GAME_8_PLAYER_6_ID = 86
        private const val GAME_8_PLAYER_7_ID = 87
        private const val GAME_8_PLAYER_8_ID = 88
    }

    override val gamePlayersNum = 8

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        GameHEightPlayersFragmentBinding.inflate(inflater, container, false)

    override fun initPlayers() {
        val playerFragmentInitialData = PlayerFragment.PlayerFragmentInitialData(
            nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize8P).toInt(),
            nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop8P).toInt(),
            pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize8P).toInt(),
            positiveAuxPointsMarginTop = resources.getDimension(R.dimen.positiveAuxPointsMarginTop8P).toInt(),
            auxPointsMargin = resources.getDimension(R.dimen.auxPointsMargin8P).toInt()
        )

        initPlayerFragment(GAME_8_PLAYER_1_ID, R.id.fcvPlayer81, playerFragmentInitialData)
        initPlayerFragment(GAME_8_PLAYER_2_ID, R.id.fcvPlayer82, playerFragmentInitialData)
        initPlayerFragment(GAME_8_PLAYER_3_ID, R.id.fcvPlayer83, playerFragmentInitialData)
        initPlayerFragment(GAME_8_PLAYER_4_ID, R.id.fcvPlayer84, playerFragmentInitialData)
        initPlayerFragment(GAME_8_PLAYER_5_ID, R.id.fcvPlayer85, playerFragmentInitialData)
        initPlayerFragment(GAME_8_PLAYER_6_ID, R.id.fcvPlayer86, playerFragmentInitialData)
        initPlayerFragment(GAME_8_PLAYER_7_ID, R.id.fcvPlayer87, playerFragmentInitialData)
        initPlayerFragment(GAME_8_PLAYER_8_ID, R.id.fcvPlayer88, playerFragmentInitialData)
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
        Game8PlayersFragmentDirections.actionGame8PlayersFragmentToPlayerSettingsMenuDialogFragment(
            playerId = playerSettingsMenuInitialData.playerId,
            playerInitialName = playerSettingsMenuInitialData.playerInitialName,
            playerInitialColor = playerSettingsMenuInitialData.playerInitialColor,
            playerHasCustomBackground = playerSettingsMenuInitialData.playerHasCustomBackground
        )

}