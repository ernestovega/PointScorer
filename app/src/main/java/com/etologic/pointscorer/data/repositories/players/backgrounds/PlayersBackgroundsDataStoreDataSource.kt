package com.etologic.pointscorer.data.repositories.players.backgrounds

import android.net.Uri
import com.etologic.pointscorer.data.repositories.base.DataStoreHelper
import javax.inject.Inject

class PlayersBackgroundsDataStoreDataSource
@Inject constructor(private val dataStoreHelper: DataStoreHelper) {

    private val keyPrefix = "background_player_"

    private fun buildKeyName(playerId: Int): String =
        "$keyPrefix$playerId"

    suspend fun get(playerId: Int): Uri? =
        dataStoreHelper.getString(buildKeyName(playerId))?.let { Uri.parse(it) }

    suspend fun save(playerId: Int, newValue: Uri) {
        dataStoreHelper.saveString(buildKeyName(playerId), newValue.toString())
    }

    suspend fun clear(playerId: Int) {
        dataStoreHelper.clear(buildKeyName(playerId))
    }

}