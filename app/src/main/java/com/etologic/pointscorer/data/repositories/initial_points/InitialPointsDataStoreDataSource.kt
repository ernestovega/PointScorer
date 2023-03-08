package com.etologic.pointscorer.data.repositories.initial_points

import com.etologic.pointscorer.data.repositories.base.DataStoreHelper
import javax.inject.Inject

class InitialPointsDataStoreDataSource @Inject constructor(private val dataStoreHelper: DataStoreHelper) {

    companion object {
        private const val keyName = "initial_points"
    }

    suspend fun get(): Int? = dataStoreHelper.getInt(keyName)

    suspend fun save(newValue: Int) = dataStoreHelper.saveInt(keyName, newValue)

}