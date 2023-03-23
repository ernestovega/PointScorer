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
import com.etologic.pointscorer.app.games.game_c_three_players.Game3PlayersFragmentDirections
import com.etologic.pointscorer.databinding.GameCThreePlayersFragmentBinding

class Game3PlayersFragment : GameFragment<GameCThreePlayersFragmentBinding>() {

    companion object {
        private const val GAME_3_PLAYER_1_ID = 31
        private const val GAME_3_PLAYER_2_ID = 32
        private const val GAME_3_PLAYER_3_ID = 33
    }

    override val gamePlayersNum = 3

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        GameCThreePlayersFragmentBinding.inflate(inflater, container, false)

    override fun initPlayers() {
        val playerFragmentInitialData = PlayerFragment.PlayerFragmentInitialData(
            nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize3P).toInt(),
            nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop3P).toInt(),
            pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize3P).toInt(),
            positiveAuxPointsMarginTop = resources.getDimension(R.dimen.positiveAuxPointsMarginTop3P).toInt(),
            auxPointsMargin = resources.getDimension(R.dimen.auxPointsMargin3P).toInt(),
        )

        initPlayerFragment(GAME_3_PLAYER_1_ID, R.id.fcvPlayer31, playerFragmentInitialData)
        initPlayerFragment(GAME_3_PLAYER_2_ID, R.id.fcvPlayer32, playerFragmentInitialData)
        initPlayerFragment(GAME_3_PLAYER_3_ID, R.id.fcvPlayer33, playerFragmentInitialData)
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
        Game3PlayersFragmentDirections.actionGame3PlayersFragmentToPlayerSettingsMenuDialogFragment(
            playerId = playerSettingsMenuInitialData.playerId,
            playerInitialName = playerSettingsMenuInitialData.playerInitialName,
            playerInitialColor = playerSettingsMenuInitialData.playerInitialColor,
            playerHasCustomBackground = playerSettingsMenuInitialData.playerHasCustomBackground
        )

}