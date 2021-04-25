package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import androidx.core.content.ContextCompat
import com.etologic.pointscorer.R
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PlayersRepository
@Inject internal constructor(
    context: Context,
    private val memoryDataSource: PlayersMemoryDataSource,
    private val sharedPrefsDataSource: PlayersSharedPrefsDataSource
) {
    
    private val defaultPlayerName: String = context.getString(R.string.player_name)
    private val defaultPlayerColor: Int = ContextCompat.getColor(context, R.color.white)
    
    init {
        runBlocking {
            if (sharedPrefsDataSource.isInitialCheckNotDone()) {
                restoreAllPlayers()
                sharedPrefsDataSource.saveInitialCheckDone()
            }
        }
    }
    
    private suspend fun restoreAllPlayers() {
        for (i in 1..MAX_PLAYERS)
            for (x in 1..i)
                resetPlayer(Integer.valueOf("$i$x"))
    }
    
    private suspend fun resetPlayer(playerId: Int) {
        savePlayerPoints(getInitialPoints(), playerId)
        savePlayerName(playerId, "")
        savePlayerColor(playerId, defaultPlayerColor)
    }
    
    //INITIAL POINTS
    suspend fun getInitialPoints(): Int =
        memoryDataSource.getInitialPoints() ?:
            sharedPrefsDataSource.getInitialPoints(DEFAULT_INITIAL_POINTS)
                .also { memoryDataSource.saveInitialPoints(it) }
    
    suspend fun saveInitialPoints(newInitialPoints: Int) {
        memoryDataSource.saveInitialPoints(newInitialPoints)
        sharedPrefsDataSource.saveInitialPoints(newInitialPoints)
    }
    
    //POINTS
    suspend fun getPlayerPoints(playerId: Int): Int =
        memoryDataSource.getPlayerPoints(playerId) ?:
        sharedPrefsDataSource.getPlayerPoints(playerId, getInitialPoints())
            .also { memoryDataSource.savePlayerPoints(playerId, it) }
    
    suspend fun savePlayerPoints(playerId: Int, newInitialPoints: Int) {
        memoryDataSource.savePlayerPoints(playerId, newInitialPoints)
        sharedPrefsDataSource.savePlayerPoints(playerId, newInitialPoints)
    }
    
    suspend fun restoreAllPlayersPoints() {
        for (i in 1..MAX_PLAYERS)
            for (x in 1..i)
                savePlayerPoints(getInitialPoints(), Integer.valueOf("$i$x"))
    }
    
    //NAME
    suspend fun getPlayerName(playerId: Int): String =
        memoryDataSource.getPlayerName(playerId) ?:
        sharedPrefsDataSource.getPlayerName(playerId, defaultPlayerName)
            .also { memoryDataSource.savePlayerName(playerId, it) }
    
    suspend fun savePlayerName(playerId: Int, newName: String) {
        memoryDataSource.savePlayerName(playerId, newName)
        sharedPrefsDataSource.savePlayerName(playerId, newName)
    }
    
    //COLOR
    suspend fun getPlayerColor(playerId: Int): Int =
        memoryDataSource.getPlayerColor(playerId) ?:
        sharedPrefsDataSource.getPlayerColor(playerId, defaultPlayerColor)
            .also { memoryDataSource.savePlayerColor(playerId, it) }
    
    suspend fun savePlayerColor(playerId: Int, newInitialColor: Int) {
        memoryDataSource.savePlayerColor(playerId, newInitialColor)
        sharedPrefsDataSource.savePlayerColor(playerId, newInitialColor)
    }
    
    companion object {
        
        private const val MAX_PLAYERS = 8
        const val DEFAULT_INITIAL_POINTS = 100
    }
    
}