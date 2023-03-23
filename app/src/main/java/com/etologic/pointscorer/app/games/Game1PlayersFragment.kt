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
import com.etologic.pointscorer.app.games.game_a_one_players.Game1PlayersFragmentDirections
import com.etologic.pointscorer.databinding.GameAOnePlayerFragmentBinding

class Game1PlayersFragment : GameFragment<GameAOnePlayerFragmentBinding>() {

    companion object {
        private const val GAME_1_PLAYER_1_ID = 11
    }

    override val gamePlayersNum = 1

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        GameAOnePlayerFragmentBinding.inflate(inflater, container, false)

    override fun initPlayers() {
        val playerFragmentInitialData = PlayerFragment.PlayerFragmentInitialData(
            nameSize = ResourcesCompat.getFloat(resources, R.dimen.nameSize1P).toInt(),
            nameMarginTop = ResourcesCompat.getFloat(resources, R.dimen.nameMarginTop1P).toInt(),
            pointsSize = ResourcesCompat.getFloat(resources, R.dimen.pointsSize1P).toInt(),
            positiveAuxPointsMarginTop = resources.getDimension(R.dimen.positiveAuxPointsMarginTop1P).toInt(),
            auxPointsMargin = resources.getDimension(R.dimen.auxPointsMargin1P).toInt(),
        )

        initPlayerFragment(GAME_1_PLAYER_1_ID, R.id.fcvPlayer11, playerFragmentInitialData)
    }

    override fun initSpecificViews() {}

    override fun getAdsContainer(): LinearLayout = binding.llAdsContainer

    override fun getPlayerSettingsMenuNavAction(
        playerSettingsMenuInitialData: GameViewModel.PlayerSettingsMenuInitialData
    ): NavDirections =
        Game1PlayersFragmentDirections.actionGame1PlayersFragmentToPlayerSettingsMenuDialogFragment(
            playerId = playerSettingsMenuInitialData.playerId,
            playerInitialName = playerSettingsMenuInitialData.playerInitialName,
            playerInitialColor = playerSettingsMenuInitialData.playerInitialColor,
            playerHasCustomBackground = playerSettingsMenuInitialData.playerHasCustomBackground
        )

}