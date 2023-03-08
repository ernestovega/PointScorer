package com.etologic.pointscorer.data.repositories.players.names

import com.etologic.pointscorer.data.repositories.base.DataStoreHelper
import javax.inject.Inject

class PlayersNamesDataStoreDataSource
@Inject constructor(private val dataStoreHelper: DataStoreHelper) {

    private val keyPrefix = "name_player_"

    private fun buildKeyName(playerId: Int) = "$keyPrefix$playerId"

    suspend fun get(playerId: Int): String? = dataStoreHelper.getString(buildKeyName(playerId))

    suspend fun save(playerId: Int, newValue: String) = dataStoreHelper.saveString(buildKeyName(playerId), newValue)

}