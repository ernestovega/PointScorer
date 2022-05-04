package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayersSharedPrefsDataSource
@Inject constructor(context: Context) {
    
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    
    suspend fun isInitialCheckNotDone(): Boolean =
        withContext(Dispatchers.IO) {
            sharedPrefs.getBoolean(KEY_INITIAL_CHECK_DONE, false)
        }
    
    suspend fun saveInitialCheckDone() {
        withContext(Dispatchers.IO) {
            sharedPrefs.edit().putBoolean(KEY_INITIAL_CHECK_DONE, true).apply()
        }
    }
    
    suspend fun getInitialPoints(defaultInitialPoints: Int) =
        withContext(Dispatchers.IO) {
            sharedPrefs.getInt(KEY_INITIAL_POINTS, defaultInitialPoints)
        }
    
    suspend fun saveInitialPoints(newInitialPoints: Int) {
        withContext(Dispatchers.IO) {
            sharedPrefs.edit().putInt(KEY_INITIAL_POINTS, newInitialPoints).apply()
        }
    }
    
    suspend fun getPlayerPoints(playerId: Int, initialPoints: Int): Int =
        withContext(Dispatchers.IO) {
            sharedPrefs.getInt(getPlayerPointsKey(playerId), initialPoints)
        }
    
    suspend fun savePlayerPoints(playerId: Int, points: Int) {
        withContext(Dispatchers.IO) {
            sharedPrefs.edit().putInt(getPlayerPointsKey(playerId), points).apply()
        }
    }
    
    suspend fun getPlayerName(playerId: Int, defaultPlayerName: String): String =
        withContext(Dispatchers.IO) {
            sharedPrefs.getString(getPlayerNameKey(playerId), defaultPlayerName) ?: defaultPlayerName
        }
    
    suspend fun savePlayerName(playerId: Int, name: String) {
        withContext(Dispatchers.IO) {
            sharedPrefs.edit().putString(getPlayerNameKey(playerId), name).apply()
        }
    }
    
    suspend fun getPlayerColor(playerId: Int, defaultColor: Int): Int =
        withContext(Dispatchers.IO) {
            sharedPrefs.getInt(getPlayerColorKey(playerId), defaultColor)
        }
    
    suspend fun savePlayerColor(playerId: Int, color: Int) =
        withContext(Dispatchers.IO) {
            sharedPrefs.edit().putInt(getPlayerColorKey(playerId), color).apply()
        }

    private fun getPlayerPointsKey(playerId: Int) =
        "$KEY_POINTS$playerId"
    
    private fun getPlayerNameKey(playerId: Int) =
        "$KEY_NAME$playerId"
    
    private fun getPlayerColorKey(playerId: Int) =
        "$KEY_COLOR$playerId"

    companion object {
        
        private const val FILE_NAME = "points_scorer_shared_prefs"
        private const val KEY_INITIAL_POINTS = "initial_points"
        private const val KEY_INITIAL_CHECK_DONE = "initial_check_done"
        private const val KEY_NAME = "name_player_"
        private const val KEY_POINTS = "points_player_"
        private const val KEY_COLOR = "color_player_"
    }
}