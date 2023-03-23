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
import com.etologic.pointscorer.app.games.game_d_four_players.Game4PlayersFragmentDirections
import com.etologic.pointscorer.databinding.GameDFourPlayersFragmentBinding

class Game4PlayersFragment : GameFragment<GameDFourPlayersFragmentBinding>() {

    companion object {
        private const val GAME_4_PLAYER_1_ID = 41
        private const val GAME_4_PLAYER_2_ID = 42
        private const val GAME_4_PLAYER_3_ID = 43
        private const val GAME_4_PLAYER_4_ID = 44
    }

    override val gamePlayersNum = 4

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        GameDFourPlayersFragmentBinding.inflate(inflater, container, false)

    override fun initPlayers() {
        val playerFragmentInitialData = PlayerFragment.PlayerFragmentInitialData(
            nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize4P).toInt(),
            nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop4P).toInt(),
            pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize4P).toInt(),
            positiveAuxPointsMarginTop = resources.getDimension(R.dimen.positiveAuxPointsMarginTop4P).toInt(),
            auxPointsMargin = resources.getDimension(R.dimen.auxPointsMargin4P).toInt()
        )

        initPlayerFragment(GAME_4_PLAYER_1_ID, R.id.fcvPlayer41, playerFragmentInitialData)
        initPlayerFragment(GAME_4_PLAYER_2_ID, R.id.fcvPlayer42, playerFragmentInitialData)
        initPlayerFragment(GAME_4_PLAYER_3_ID, R.id.fcvPlayer43, playerFragmentInitialData)
        initPlayerFragment(GAME_4_PLAYER_4_ID, R.id.fcvPlayer44, playerFragmentInitialData)
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
        Game4PlayersFragmentDirections.actionGame4PlayersFragmentToPlayerSettingsMenuDialogFragment(
            playerId = playerSettingsMenuInitialData.playerId,
            playerInitialName = playerSettingsMenuInitialData.playerInitialName,
            playerInitialColor = playerSettingsMenuInitialData.playerInitialColor,
            playerHasCustomBackground = playerSettingsMenuInitialData.playerHasCustomBackground
        )

}