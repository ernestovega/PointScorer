package com.etologic.pointscorer.data.repositories.players.names

import com.etologic.pointscorer.data.repositories.base.BaseMapMemoryDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersNamesMemoryDataSource @Inject constructor(): BaseMapMemoryDataSource<Int, String>()