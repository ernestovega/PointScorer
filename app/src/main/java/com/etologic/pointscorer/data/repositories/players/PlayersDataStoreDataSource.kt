package com.etologic.pointscorer.data.repositories.players

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersDataStoreDataSource
@Inject constructor(private val context: Context) {

    companion object {
        private const val FILE_NAME = "points_scorer_shared_prefs"
        private val PREFS_KEY_INITIAL_CHECK_DONE = booleanPreferencesKey("initial_check_done")
        private val PREFS_KEY_INITIAL_POINTS = intPreferencesKey("initial_points")
        private fun getPlayerNamePrefsKey(playerId: Int) =
            stringPreferencesKey("name_player_$playerId")

        private fun getPlayerPointsPrefsKey(playerId: Int) =
            intPreferencesKey("points_player_$playerId")

        private fun getPlayerColorPrefsKey(playerId: Int) =
            intPreferencesKey("color_player_$playerId")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(FILE_NAME)

    suspend fun isInitialCheckDone(): Boolean =
        context.dataStore.data.map { preferences ->
            preferences[PREFS_KEY_INITIAL_CHECK_DONE]
        }.firstOrNull() ?: false

    suspend fun getInitialPoints(): Int? =
        context.dataStore.data.map { preferences ->
            preferences[PREFS_KEY_INITIAL_POINTS]
        }.firstOrNull()

    suspend fun getPlayerName(playerId: Int): String? =
        context.dataStore.data.map { preferences ->
            preferences[getPlayerNamePrefsKey(playerId)]
        }.firstOrNull()

    suspend fun getPlayerPoints(playerId: Int): Int? =
        context.dataStore.data.map { preferences ->
            preferences[getPlayerPointsPrefsKey(playerId)]
        }.firstOrNull()

    suspend fun getPlayerColor(playerId: Int): Int? =
        context.dataStore.data.map { preferences ->
            preferences[getPlayerColorPrefsKey(playerId)]
        }.firstOrNull()

    suspend fun saveInitialCheckDone() {
        context.dataStore.edit { preferences ->
            preferences[PREFS_KEY_INITIAL_CHECK_DONE] = true
        }
    }

    suspend fun saveInitialPoints(newInitialPoints: Int) {
        context.dataStore.edit { preferences ->
            preferences[PREFS_KEY_INITIAL_POINTS] = newInitialPoints
        }
    }

    suspend fun savePlayerName(playerId: Int, newName: String) {
        context.dataStore.edit { preferences ->
            preferences[getPlayerNamePrefsKey(playerId)] = newName
        }
    }

    suspend fun savePlayerPoints(playerId: Int, newPoints: Int) {
        context.dataStore.edit { preferences ->
            preferences[getPlayerPointsPrefsKey(playerId)] = newPoints
        }
    }

    suspend fun savePlayerColor(playerId: Int, newColor: Int) {
        context.dataStore.edit { preferences ->
            preferences[getPlayerColorPrefsKey(playerId)] = newColor
        }
    }
}