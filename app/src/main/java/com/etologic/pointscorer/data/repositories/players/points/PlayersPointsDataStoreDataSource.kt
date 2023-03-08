package com.etologic.pointscorer.data.repositories.players.points

import com.etologic.pointscorer.data.repositories.base.DataStoreHelper
import javax.inject.Inject

class PlayersPointsDataStoreDataSource
@Inject constructor(private val dataStoreHelper: DataStoreHelper) {

    private val keyPrefix = "points_player_"

    private fun buildKeyName(playerId: Int) = "$keyPrefix$playerId"

    suspend fun get(playerId: Int): Int? = dataStoreHelper.getInt(buildKeyName(playerId))

    suspend fun save(playerId: Int, newValue: Int) = dataStoreHelper.saveInt(buildKeyName(playerId), newValue)

}