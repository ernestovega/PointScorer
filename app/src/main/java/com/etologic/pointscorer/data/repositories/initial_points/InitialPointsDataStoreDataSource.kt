package com.etologic.pointscorer.data.repositories.initial_points

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.etologic.pointscorer.data.repositories.base.DataStoreHelper
import javax.inject.Inject
import javax.inject.Singleton

class InitialPointsDataStoreDataSource @Inject constructor(private val dataStoreHelper: DataStoreHelper) {

    companion object {
        private const val keyName = "initial_points"
    }

    suspend fun get(): Int? = dataStoreHelper.getInt(keyName)

    suspend fun save(newValue: Int) = dataStoreHelper.saveInt(keyName, newValue)

}