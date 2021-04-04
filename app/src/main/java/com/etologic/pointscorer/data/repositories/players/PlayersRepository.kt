package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import androidx.core.content.ContextCompat
import com.etologic.pointscorer.R
import javax.inject.Inject

class PlayersRepository
@Inject internal constructor(
    context: Context,
    private val sharedPrefsDataSource: PlayersSharedPrefsDataSource
) {
    
    companion object {
        private const val MAX_PLAYERS = 8
        private const val DEFAULT_INITIAL_POINTS = 100
    }
    
    private val defaultPlayerName: String = context.getString(R.string.player_name)
    private val defaultTextColor: Int = ContextCompat.getColor(context, R.color.white)
    var initialPoints: Int? = null
        get() {
            return field ?: sharedPrefsDataSource.getInitialPoints(DEFAULT_INITIAL_POINTS).also { field = it }
        }
    
    init {
        Thread {
            if (sharedPrefsDataSource.isInitialCheckNotDone()) {
                restoreAllPlayersPoints()
                sharedPrefsDataSource.saveInitialCheckDone()
            }
        }.run()
    }
    
    //INITIAL POINTS
    private fun resetPlayer(playerId: Int) {
        savePlayerPoints(initialPoints!!, playerId)
        savePlayerName(playerId, "")
    }
    
    fun saveInitialPoints(newInitialPoints: Int) {
        sharedPrefsDataSource.saveInitialPoints(newInitialPoints)
    }
    
    //POINTS
    fun getPlayerPoints(playerId: Int): Int =
        sharedPrefsDataSource.getPlayerPoints(playerId, initialPoints!!)
    
    fun savePlayerPoints(playerId: Int, newInitialPoints: Int) {
        sharedPrefsDataSource.savePlayerPoints(playerId, newInitialPoints)
    }
    
    fun restoreAllPlayersPoints() {
        for (i in 1..MAX_PLAYERS)
            for (x in 1..i)
                resetPlayer(Integer.valueOf("$i$x"))
    }
    
    //NAME
    fun getPlayerName(playerId: Int): String =
        sharedPrefsDataSource.getPlayerName(playerId, defaultPlayerName)
    
    fun savePlayerName(playerId: Int, newName: String) {
        sharedPrefsDataSource.savePlayerName(playerId, newName)
    }
    
    //COLOR
    fun getPlayerColor(playerId: Int): Int =
        sharedPrefsDataSource.getPlayerColor(playerId, defaultTextColor)
    
    fun savePlayerColor(playerId: Int, newInitialColor: Int) {
        sharedPrefsDataSource.savePlayerColor(playerId, newInitialColor)
    }
    
}