package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import android.net.Uri
import androidx.core.content.ContextCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.bussiness.model.Player
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
    private val playersDataStoreDataSource: PlayersDataStoreDataSource,
) : CoroutineScope {

    private val defaultName: String = context.getString(R.string.player_name)
    private val defaultColor: Int = ContextCompat.getColor(context, R.color.white)
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()

    private fun buildPlayerId(i: Int, x: Int) = Integer.valueOf("$i$x")

    suspend fun getPlayer(playerId: Int): Player =
        playersMemoryDataSource.get(playerId)
            ?: (playersDataStoreDataSource.get(playerId)
                ?.also { playersMemoryDataSource.save(it.id, it) })
            ?: (Player(playerId, defaultName, defaultColor, null, initialPointsRepository.getInitialPoints())
                .also { savePlayer(it) })

    private suspend fun savePlayer(player: Player) {
        playersMemoryDataSource.save(player.id, player)
        playersDataStoreDataSource.save(player.id, player)
    }

    suspend fun savePlayerName(playerId: Int, newName: String): Player {
        getPlayer(playerId)
            .let {
                val updatedPlayer = Player(it.id, newName, it.color, it.background, it.points)
                savePlayer(updatedPlayer)
                return updatedPlayer
            }
    }

    suspend fun savePlayerColor(playerId: Int, newColor: Int): Player {
        getPlayer(playerId)
            .let {
                val updatedPlayer = Player(it.id, it.name, newColor, it.background, it.points)
                savePlayer(updatedPlayer)
                return updatedPlayer
            }
    }

    suspend fun savePlayerBackground(playerId: Int, newBackground: Uri?): Player {
        getPlayer(playerId)
            .let {
                val updatedPlayer = Player(it.id, it.name, it.color, newBackground, it.points)
                savePlayer(updatedPlayer)
                return updatedPlayer
            }
    }

    suspend fun savePlayerPoints(playerId: Int, newPoints: Int): Player {
        getPlayer(playerId)
            .let {
                val updatedPlayer = Player(it.id, it.name, it.color, it.background, newPoints)
                savePlayer(updatedPlayer)
                return updatedPlayer
            }
    }

    suspend fun restoreOnePlayerPoints(playerId: Int): Player {
        initialPointsRepository.getInitialPoints().let { initialPoints ->
            savePlayerPoints(playerId, initialPoints)
            return getPlayer(playerId)
        }
    }

    suspend fun resetAllPlayersPoints() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerPoints(buildPlayerId(i, x), initialPointsRepository.getInitialPoints())
            }
        }
    }

    suspend fun resetAllPlayersNames() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerName(buildPlayerId(i, x), defaultName)
            }
        }
    }

    suspend fun resetAllPlayersColors() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerColor(buildPlayerId(i, x), defaultColor)
            }
        }
    }

    suspend fun resetAllPlayersBackgrounds() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerBackground(buildPlayerId(i, x), null)
            }
        }
    }
}