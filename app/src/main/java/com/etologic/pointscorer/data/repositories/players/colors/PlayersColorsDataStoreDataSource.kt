package com.etologic.pointscorer.data.repositories.players.colors

import com.etologic.pointscorer.data.repositories.base.DataStoreHelper
import javax.inject.Inject

class PlayersColorsDataStoreDataSource
@Inject constructor(private val dataStoreHelper: DataStoreHelper) {

    private val keyPrefix = "color_player_"

    private fun buildKeyName(playerId: Int) = "$keyPrefix$playerId"

    suspend fun get(playerId: Int): Int? = dataStoreHelper.getInt(buildKeyName(playerId))

    suspend fun save(playerId: Int, newValue: Int) = dataStoreHelper.saveInt(buildKeyName(playerId), newValue)

}