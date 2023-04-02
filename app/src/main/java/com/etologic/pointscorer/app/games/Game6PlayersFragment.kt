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
import com.etologic.pointscorer.databinding.GameFSixPlayersFragmentBinding

class Game6PlayersFragment : GameFragment<GameFSixPlayersFragmentBinding>() {

    companion object {
        private const val GAME_6_PLAYER_1_ID = 61
        private const val GAME_6_PLAYER_2_ID = 62
        private const val GAME_6_PLAYER_3_ID = 63
        private const val GAME_6_PLAYER_4_ID = 64
        private const val GAME_6_PLAYER_5_ID = 65
        private const val GAME_6_PLAYER_6_ID = 66
    }

    override val gamePlayersNum = 6

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        GameFSixPlayersFragmentBinding.inflate(inflater, container, false)

    override fun initPlayers() {
        val playerFragmentInitialData = PlayerFragment.PlayerFragmentInitialData(
            nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize6P).toInt(),
            nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop6P).toInt(),
            pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize6P).toInt(),
            positiveAuxPointsMarginTop = resources.getDimension(R.dimen.positiveAuxPointsMarginTop6P).toInt(),
            auxPointsMargin = resources.getDimension(R.dimen.auxPointsMargin6P).toInt()
        )

        initPlayerFragment(GAME_6_PLAYER_1_ID, R.id.fcvPlayer61, playerFragmentInitialData)
        initPlayerFragment(GAME_6_PLAYER_2_ID, R.id.fcvPlayer62, playerFragmentInitialData)
        initPlayerFragment(GAME_6_PLAYER_3_ID, R.id.fcvPlayer63, playerFragmentInitialData)
        initPlayerFragment(GAME_6_PLAYER_4_ID, R.id.fcvPlayer64, playerFragmentInitialData)
        initPlayerFragment(GAME_6_PLAYER_5_ID, R.id.fcvPlayer65, playerFragmentInitialData)
        initPlayerFragment(GAME_6_PLAYER_6_ID, R.id.fcvPlayer66, playerFragmentInitialData)
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
        Game6PlayersFragmentDirections.actionGame6PlayersFragmentToPlayerSettingsMenuDialogFragment(
            playerId = playerSettingsMenuInitialData.playerId,
            playerInitialName = playerSettingsMenuInitialData.playerInitialName,
            playerInitialColor = playerSettingsMenuInitialData.playerInitialColor,
            playerHasCustomBackground = playerSettingsMenuInitialData.playerHasCustomBackground
        )

}