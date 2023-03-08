package com.etologic.pointscorer.data.repositories.players.points

import com.etologic.pointscorer.data.repositories.base.BaseMapMemoryDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersPointsMemoryDataSource @Inject constructor() : BaseMapMemoryDataSource<Int, Int>()
