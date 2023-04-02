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
import com.etologic.pointscorer.databinding.GameBTwoPlayersFragmentBinding

class Game2PlayersFragment : GameFragment<GameBTwoPlayersFragmentBinding>() {

    companion object {
        private const val GAME_2_PLAYER_1_ID = 21
        private const val GAME_2_PLAYER_2_ID = 22
    }

    override val gamePlayersNum = 2

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        GameBTwoPlayersFragmentBinding.inflate(inflater, container, false)

    override fun initPlayers() {
        val playerFragmentInitialData = PlayerFragment.PlayerFragmentInitialData(
            nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize2P).toInt(),
            nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop2P).toInt(),
            pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize2P).toInt(),
            positiveAuxPointsMarginTop = resources.getDimension(R.dimen.positiveAuxPointsMarginTop2P).toInt(),
            auxPointsMargin = resources.getDimension(R.dimen.auxPointsMargin2P).toInt(),
        )

        initPlayerFragment(GAME_2_PLAYER_1_ID, R.id.fcvPlayer21, playerFragmentInitialData)
        initPlayerFragment(GAME_2_PLAYER_2_ID, R.id.fcvPlayer22, playerFragmentInitialData)
    }

    override fun initSpecificViews() {
        binding.fabGamePlayerSettings.setOnClickListener { askConfirmRestoreAllPlayersPoints() }
    }

    override fun getAdsContainer(): LinearLayout = binding.llAdsContainer

    override fun getPlayerSettingsMenuNavAction(
        playerSettingsMenuInitialData: GameViewModel.PlayerSettingsMenuInitialData
    ): NavDirections =
        Game2PlayersFragmentDirections.actionGame2PlayersFragmentToPlayerSettingsMenuDialogFragment(
            playerId = playerSettingsMenuInitialData.playerId,
            playerInitialName = playerSettingsMenuInitialData.playerInitialName,
            playerInitialColor = playerSettingsMenuInitialData.playerInitialColor,
            playerHasCustomBackground = playerSettingsMenuInitialData.playerHasCustomBackground
        )

}