package com.etologic.pointscorer.data.repositories.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreHelper @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        const val FILE_NAME = "points_scorer_shared_prefs"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(FILE_NAME)

    suspend fun getBoolean(keyName: String): Boolean? =
        context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(keyName)]
        }.firstOrNull()

    suspend fun saveBoolean(keyName: String, newValue: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(keyName)] = newValue
        }
    }

    suspend fun getInt(keyName: String): Int? =
        context.dataStore.data.map { preferences ->
            preferences[intPreferencesKey(keyName)]
        }.firstOrNull()

    suspend fun saveInt(keyName: String, newValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(keyName)] = newValue
        }
    }

    suspend fun getString(keyName: String): String? =
        context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(keyName)]
        }.firstOrNull()

    suspend fun saveString(keyName: String, newValue: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(keyName)] = newValue
        }
    }

}