package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersSharedPrefsDataSource
@Inject constructor(context: Context) {
    
    companion object {
        
        private const val FILE_NAME = "points_scorer_shared_prefs"
        private const val KEY_INITIAL_POINTS = "initial_points"
        private const val KEY_INITIAL_CHECK_DONE = "initial_check_done"
        private const val KEY_NAME = "name_player_"
        private const val KEY_POINTS = "points_player_"
        private const val KEY_COLOR = "color_player_"
    }
    
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    
    fun isInitialCheckNotDone(): Boolean =
        sharedPrefs.getBoolean(KEY_INITIAL_CHECK_DONE, false)
    
    fun saveInitialCheckDone() {
        sharedPrefs.edit().putBoolean(KEY_INITIAL_CHECK_DONE, true).apply()
    }
    
    fun getInitialPoints(defaultInitialPoints: Int) =
        sharedPrefs.getInt(KEY_INITIAL_POINTS, defaultInitialPoints)
    
    fun saveInitialPoints(newInitialPoints: Int) {
        sharedPrefs.edit().putInt(KEY_INITIAL_POINTS, newInitialPoints).apply()
    }
    
    fun getPlayerPoints(playerId: Int, initialPoints: Int): Int =
        sharedPrefs.getInt(getPlayerPointsKey(playerId), initialPoints)
    
    fun savePlayerPoints(points: Int, playerId: Int) {
        sharedPrefs.edit().putInt(getPlayerPointsKey(playerId), points).apply()
    }
    
    fun getPlayerName(playerId: Int, defaultPlayerName: String): String =
        sharedPrefs.getString(getPlayerNameKey(playerId), defaultPlayerName) ?: defaultPlayerName
    
    fun savePlayerName(playerId: Int, name: String) {
        sharedPrefs.edit().putString(getPlayerNameKey(playerId), name).apply()
    }
    
    fun getPlayerColor(playerId: Int, defaultColor: Int): Int =
        sharedPrefs.getInt(getPlayerColorKey(playerId), defaultColor)
    
    fun savePlayerColor(color: Int, playerId: Int) =
        sharedPrefs.edit().putInt(getPlayerColorKey(playerId), color).apply()
    
    private fun getPlayerPointsKey(playerId: Int) =
        "$KEY_POINTS$playerId"
    
    private fun getPlayerNameKey(playerId: Int) =
        "$KEY_NAME$playerId"
    
    private fun getPlayerColorKey(playerId: Int) =
        "$KEY_COLOR$playerId"
}