package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import androidx.core.content.ContextCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.common.Constants.MAX_PLAYERS
import com.etologic.pointscorer.data.repositories.initial_points.InitialPointsRepository
import com.etologic.pointscorer.data.repositories.players.colors.PlayersColorsDataStoreDataSource
import com.etologic.pointscorer.data.repositories.players.colors.PlayersColorsMemoryDataSource
import com.etologic.pointscorer.data.repositories.players.initial_check.InitialCheckDataStoreDataSource
import com.etologic.pointscorer.data.repositories.players.names.PlayersNamesDataStoreDataSource
import com.etologic.pointscorer.data.repositories.players.names.PlayersNamesMemoryDataSource
import com.etologic.pointscorer.data.repositories.players.points.PlayersPointsDataStoreDataSource
import com.etologic.pointscorer.data.repositories.players.points.PlayersPointsMemoryDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PlayersRepository
@Inject internal constructor(
    @ApplicationContext context: Context,
    private val playersPointsMemoryDataSource: PlayersPointsMemoryDataSource,
    private val playersNamesMemoryDataSource: PlayersNamesMemoryDataSource,
    private val playersColorsMemoryDataSource: PlayersColorsMemoryDataSource,
    private val playersPointsDataStoreDataSource: PlayersPointsDataStoreDataSource,
    private val playersNamesDataStoreDataSource: PlayersNamesDataStoreDataSource,
    private val playersColorsDataStoreDataSource: PlayersColorsDataStoreDataSource,
    private val initialCheckDataStoreDataSource: InitialCheckDataStoreDataSource,
    private val initialPointsRepository: InitialPointsRepository
) : CoroutineScope {

    private val defaultPlayerName: String = context.getString(R.string.player_name)
    private val defaultPlayerColor: Int = ContextCompat.getColor(context, R.color.white)
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()

    init {

        suspend fun resetPlayer(playerId: Int) {
            savePlayerPoints(playerId, initialPointsRepository.getInitialPoints())
            savePlayerName(playerId, "")
            savePlayerColor(playerId, defaultPlayerColor)
        }

        suspend fun restoreAllPlayers() {
            for (i in 1..MAX_PLAYERS) {
                for (x in 1..i) {
                    resetPlayer(buildPlayerId(i, x))
                }
            }
        }

        launch {
            if (!initialCheckDataStoreDataSource.get()) {
                restoreAllPlayers()
                initialCheckDataStoreDataSource.save(true)
            }
        }
    }

    suspend fun resetPlayerPoints(playerId: Int) {
        savePlayerPoints(playerId, initialPointsRepository.getInitialPoints())
    }

    suspend fun savePlayerPoints(playerId: Int, newPoints: Int) {
        playersPointsMemoryDataSource.save(playerId, newPoints)
        playersPointsDataStoreDataSource.save(playerId, newPoints)
    }

    suspend fun getPlayerPoints(playerId: Int): Int =
        playersPointsMemoryDataSource.get(playerId)
            ?: (playersPointsDataStoreDataSource.get(playerId) ?: initialPointsRepository.getInitialPoints())
                .also { playersPointsMemoryDataSource.save(playerId, it) }

    suspend fun resetAllPlayersPoints() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerPoints(buildPlayerId(i, x), initialPointsRepository.getInitialPoints())
            }
        }
    }

    suspend fun resetAllPlayersNamesAndColors() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                savePlayerColor(buildPlayerId(i, x), defaultPlayerColor)
                savePlayerName(buildPlayerId(i, x), defaultPlayerName)
            }
        }
    }

    private fun buildPlayerId(i: Int, x: Int) = Integer.valueOf("$i$x")

    suspend fun getPlayerName(playerId: Int): String =
        playersNamesMemoryDataSource.get(playerId)
            ?: (playersNamesDataStoreDataSource.get(playerId) ?: defaultPlayerName)
                .also { playersNamesMemoryDataSource.save(playerId, it) }

    suspend fun savePlayerName(playerId: Int, newName: String) {
        playersNamesMemoryDataSource.save(playerId, newName)
        playersNamesDataStoreDataSource.save(playerId, newName)
    }

    suspend fun getPlayerColor(playerId: Int): Int =
        playersColorsMemoryDataSource.get(playerId)
            ?: (playersColorsDataStoreDataSource.get(playerId) ?: defaultPlayerColor)
                .also { playersColorsMemoryDataSource.save(playerId, it) }

    suspend fun savePlayerColor(playerId: Int, newColor: Int) {
        playersColorsMemoryDataSource.save(playerId, newColor)
        playersColorsDataStoreDataSource.save(playerId, newColor)
    }

    fun invalidate() {
        coroutineContext.job.cancel()
    }

}