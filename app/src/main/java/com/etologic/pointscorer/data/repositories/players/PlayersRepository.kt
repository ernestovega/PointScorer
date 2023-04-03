package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import android.net.Uri
import androidx.core.content.ContextCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.bussiness.model.entities.Player
import com.etologic.pointscorer.common.Constants.MAX_PLAYERS
import com.etologic.pointscorer.data.repositories.initial_points.InitialPointsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PlayersRepository
@Inject internal constructor(
    @ApplicationContext context: Context,
    private val initialPointsRepository: InitialPointsRepository,
    private val playersMemoryDataSource: PlayersMemoryDataSource,
    private val playersDataBaseDataSource: PlayersDataBaseDataSource,
) : CoroutineScope {

    private val defaultName: String = context.getString(R.string.player_name)
    private val defaultColor: Int = ContextCompat.getColor(context, R.color.white)
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()

    suspend fun getPlayer(playerId: Int): Player {

        suspend fun createPlayer(playerId: Int): Player {
            val newPlayer = Player(
                playerId,
                defaultName,
                defaultColor,
                null,
                initialPointsRepository.getInitialPoints()
            )
            playersDataBaseDataSource.insert(newPlayer)
            return playersMemoryDataSource.save(newPlayer.id, newPlayer)
        }

        return playersMemoryDataSource.get(playerId)
            ?: playersDataBaseDataSource.get(playerId)
                ?.also { playersMemoryDataSource.save(it.id, it) }
            ?: createPlayer(playerId)
    }

    suspend fun savePlayerName(playerId: Int, newName: String): Player {
        playersDataBaseDataSource.updateName(playerId, newName)
        return getPlayer(playerId)
    }

    suspend fun savePlayerColor(playerId: Int, newColor: Int): Player {
        playersDataBaseDataSource.updateColor(playerId, newColor)
        return getPlayer(playerId)
    }

    suspend fun savePlayerBackground(playerId: Int, newBackground: Uri?): Player {
        playersDataBaseDataSource.updateBackground(playerId, newBackground.toString())
        return getPlayer(playerId)
    }

    suspend fun savePlayerPoints(playerId: Int, newPoints: Int): Player {
        playersDataBaseDataSource.updatePoints(playerId, newPoints)
        return getPlayer(playerId)
    }

    suspend fun restoreOnePlayerPoints(playerId: Int): Player {
        playersDataBaseDataSource.updatePoints(playerId, initialPointsRepository.getInitialPoints())
        return getPlayer(playerId)
    }

    suspend fun restoreAllPlayers() {
        restoreAllPlayersNames()
        restoreAllPlayersColors()
        restoreAllPlayersBackgrounds()
        restoreAllPlayersPoints()
    }

    suspend fun restoreAllPlayersNames() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerName(buildPlayerId(i, x), defaultName)
            }
        }
    }

    suspend fun restoreAllPlayersColors() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerColor(buildPlayerId(i, x), defaultColor)
            }
        }
    }

    suspend fun restoreAllPlayersBackgrounds() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerBackground(buildPlayerId(i, x), null)
            }
        }
    }

    suspend fun restoreAllPlayersPoints() {
        val initialPoints = initialPointsRepository.getInitialPoints()
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerPoints(buildPlayerId(i, x), initialPoints)
            }
        }
    }

    private fun buildPlayerId(i: Int, x: Int) =
        Integer.valueOf("$i$x")

}