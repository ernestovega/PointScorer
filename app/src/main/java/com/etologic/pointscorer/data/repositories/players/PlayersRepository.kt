package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import androidx.core.content.ContextCompat
import com.etologic.pointscorer.R
import com.etologic.pointscorer.data.exceptions.MaxPointsReachedException
import com.etologic.pointscorer.data.exceptions.MinPointsReachedException
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.Throws

@Singleton
class PlayersRepository
@Inject internal constructor(
    context: Context,
    private val memoryDataSource: PlayersMemoryDataSource,
    private val sharedPrefsDataSource: PlayersSharedPrefsDataSource
) : CoroutineScope {
    
    private val defaultPlayerName: String = context.getString(R.string.player_name)
    private val defaultPlayerColor: Int = ContextCompat.getColor(context, R.color.white)
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()
    
    init {
        launch { doInitialCheck() }
    }
    
    private suspend fun doInitialCheck() {
        if (sharedPrefsDataSource.isInitialCheckNotDone()) {
            restoreAllPlayers()
            sharedPrefsDataSource.saveInitialCheckDone()
        }
    }
    
    private suspend fun restoreAllPlayers() {
        for (i in 1..MAX_PLAYERS)
            for (x in 1..i)
                resetPlayer(buildPlayerId(i, x))
    }
    
    private suspend fun resetPlayer(playerId: Int) {
        savePlayerPoints(playerId, getInitialPoints())
        savePlayerName(playerId, "")
        savePlayerColor(playerId, defaultPlayerColor)
    }
    
    suspend fun resetPlayerPoints(playerId: Int) {
        savePlayerPoints(playerId, getInitialPoints())
    }
    
    suspend fun getInitialPoints(): Int =
        memoryDataSource.getInitialPoints()
            ?: sharedPrefsDataSource.getInitialPoints(DEFAULT_INITIAL_POINTS)
                .also { memoryDataSource.saveInitialPoints(it) }
    
    suspend fun saveInitialPoints(newInitialPoints: Int) {
        memoryDataSource.saveInitialPoints(newInitialPoints)
        sharedPrefsDataSource.saveInitialPoints(newInitialPoints)
    }
    
    private suspend fun savePlayerPoints(playerId: Int, newPoints: Int) {
        memoryDataSource.savePlayerPoints(playerId, newPoints)
        sharedPrefsDataSource.savePlayerPoints(playerId, newPoints)
    }
    
    suspend fun getPlayerPoints(playerId: Int): Int =
        memoryDataSource.getPlayerPoints(playerId)
            ?: sharedPrefsDataSource.getPlayerPoints(playerId, getInitialPoints())
                .also { memoryDataSource.savePlayerPoints(playerId, it) }

    @Throws(MaxPointsReachedException::class)
    suspend fun plus1PlayerPoint(playerId: Int): Int {
        val oldPoints = getPlayerPoints(playerId)
        val newPoints = oldPoints + 1
        if (oldPoints < MAX_POINTS) {
            savePlayerPoints(playerId, newPoints)
            return newPoints
        } else {
            throw MaxPointsReachedException()
        }
    }

    @Throws(MinPointsReachedException::class)
    suspend fun minus1PlayerPoint(playerId: Int): Int {
        val oldPoints = getPlayerPoints(playerId)
        val newPoints = oldPoints - 1
        if (oldPoints > MIN_POINTS) {
            savePlayerPoints(playerId, newPoints)
            return newPoints
        } else {
            throw MinPointsReachedException()
        }
    }
    
    suspend fun restoreAllPlayersPoints() {
        for (i in 1..MAX_PLAYERS)
            for (x in 1..i)
                savePlayerPoints(buildPlayerId(i, x), getInitialPoints())
    }
    
    private fun buildPlayerId(i: Int, x: Int) = Integer.valueOf("$i$x")
    
    suspend fun getPlayerName(playerId: Int): String =
        memoryDataSource.getPlayerName(playerId)
            ?: sharedPrefsDataSource.getPlayerName(playerId, defaultPlayerName)
                .also { memoryDataSource.savePlayerName(playerId, it) }
    
    suspend fun savePlayerName(playerId: Int, newName: String) {
        memoryDataSource.savePlayerName(playerId, newName)
        sharedPrefsDataSource.savePlayerName(playerId, newName)
    }
    
    suspend fun getPlayerColor(playerId: Int): Int =
        memoryDataSource.getPlayerColor(playerId)
            ?: sharedPrefsDataSource.getPlayerColor(playerId, defaultPlayerColor)
                .also { memoryDataSource.savePlayerColor(playerId, it) }
    
    suspend fun savePlayerColor(playerId: Int, newInitialColor: Int) {
        memoryDataSource.savePlayerColor(playerId, newInitialColor)
        sharedPrefsDataSource.savePlayerColor(playerId, newInitialColor)
    }

    fun invalidate() {
        coroutineContext.job.cancel()
    }
    
    companion object {
        
        private const val MAX_PLAYERS = 8
        const val DEFAULT_INITIAL_POINTS = 100
        const val MAX_POINTS = 9999
        const val MIN_POINTS = -999
    }
    
}