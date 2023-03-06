package com.etologic.pointscorer.data.repositories.players.initial_check

import com.etologic.pointscorer.data.repositories.base.DataStoreHelper
import javax.inject.Inject

class InitialCheckDataStoreDataSource
@Inject constructor(private val dataStoreHelper: DataStoreHelper) {

    companion object {
        private const val keyName = "is_initial_check_done"
    }

    suspend fun get(): Boolean = dataStoreHelper.getBoolean(keyName) ?: false

    suspend fun save(newValue: Boolean) = dataStoreHelper.saveBoolean(keyName, newValue)

}