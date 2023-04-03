package com.etologic.pointscorer.data.repositories.players

import com.etologic.pointscorer.bussiness.model.entities.Player
import com.etologic.pointscorer.data.repositories.base.BaseMapMemoryDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersMemoryDataSource @Inject constructor() : BaseMapMemoryDataSource<Int, Player>()
