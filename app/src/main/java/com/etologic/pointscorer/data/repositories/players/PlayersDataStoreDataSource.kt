package com.etologic.pointscorer.data.repositories.players

import com.etologic.pointscorer.bussiness.model.Player
import com.etologic.pointscorer.data.repositories.base.DataStoreHelper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@Suppress("unused")
@Deprecated("Alternative to Room PlayersDataBaseDataSource")
class PlayersDataStoreDataSource
@Inject constructor(private val dataStoreHelper: DataStoreHelper) {

    private val keyPrefix = "player"

    private fun buildKeyName(playerId: Int) = "$keyPrefix$playerId"

    suspend fun get(playerId: Int): Player? =
        dataStoreHelper.getString(buildKeyName(playerId))?.let { Json.decodeFromString(it) }

    suspend fun save(playerId: Int, newValue: Player) =
        dataStoreHelper.saveString(buildKeyName(playerId),
            Json.encodeToString(newValue)
        )

}