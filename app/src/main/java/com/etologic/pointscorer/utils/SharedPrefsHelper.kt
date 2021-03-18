package com.etologic.pointscorer.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import com.etologic.pointscorer.R.color
import java.lang.ref.WeakReference
import java.util.*

@WorkerThread//ToDo: como va esto?
class SharedPrefsHelper(context: Context) {
    
    companion object {
        private const val FILE_NAME = "points_scorer_shared_prefs"
        private const val MAX_PLAYERS = 8
        private const val DEFAULT_INITIAL_POINTS = 100
        private const val KEY_INITIAL_POINTS = "initial_points"
        private const val KEY_INITIAL_CHECK_DONE = "initial_check_done"
        private const val KEY_NAME = "name_player_"
        private const val KEY_POINTS = "points_player_"
        private const val KEY_COLOR = "color_player_"
        private const val DEFAULT_PLAYER_NAME = "Player name"
    }
    
    private val weakContext: WeakReference<Context> = WeakReference(context)
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    private val defaultTextColor: Int = ContextCompat.getColor(context, color.gray_text)
    internal var initialPoints: Int
    
    init {
        initialPoints = getInitialPoints()
    }
    
    fun getInitialPoints(): Int {
        return sharedPrefs.getInt(KEY_INITIAL_POINTS, DEFAULT_INITIAL_POINTS)
    }

    fun initRecordsIfProceed() {
        Thread {
            if (!sharedPrefs.getBoolean(KEY_INITIAL_CHECK_DONE, false)) {
                resetAll()
                sharedPrefs.edit().putBoolean(KEY_INITIAL_CHECK_DONE, true).apply()
            }
        }.run()
    }

    fun resetAll() {
        for (i in 1..MAX_PLAYERS) {
            for (x in 1..i) {
                val sId = String.format(Locale.ENGLISH, "%d%d", i, x)
                resetPlayer(Integer.valueOf(sId))
            }
        }
    }

    private fun resetPlayer(playerId: Int) {
        savePlayerPoints(initialPoints, playerId)
        savePlayerColor(ContextCompat.getColor(weakContext.get()!!, color.gray_text), playerId)
        savePlayerName("", playerId)
    }

    private fun resetPlayers(playerIds: IntArray) {
        for (id in playerIds) resetPlayer(id)
    }

    fun saveInitialPoints(points: Int) {
        sharedPrefs.edit().putInt(KEY_INITIAL_POINTS, points).apply()
        initialPoints = points
    }

    fun getPlayerPoints(playerId: Int): Int {
        val key = String.format(Locale.ENGLISH, "%s%d", KEY_POINTS, playerId)
        return sharedPrefs.getInt(key, initialPoints)
    }

    fun getPlayerColor(playerId: Int): Int {
        return sharedPrefs.getInt(KEY_COLOR + playerId, defaultTextColor)
    }

    fun getPlayerName(playerId: Int): String? {
        return sharedPrefs.getString(KEY_NAME + playerId, DEFAULT_PLAYER_NAME)
    }

    fun savePlayerName(name: String?, playerId: Int) {
        sharedPrefs.edit().putString(KEY_NAME + playerId, name).apply()
    }

    fun savePlayerPoints(points: Int, playerId: Int) {
        val key = String.format(Locale.ENGLISH, "%s%d", KEY_POINTS, playerId)
        sharedPrefs.edit().putInt(key, points).apply()
    }

    fun savePlayerColor(color: Int, playerId: Int) {
        sharedPrefs.edit().putInt(String.format(Locale.ENGLISH, "%s%d", KEY_COLOR, playerId), color)
            .apply()
    }
    
}