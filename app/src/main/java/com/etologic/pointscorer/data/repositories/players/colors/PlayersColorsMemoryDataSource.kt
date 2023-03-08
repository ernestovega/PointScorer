package com.etologic.pointscorer.data.repositories.players.colors

import com.etologic.pointscorer.data.repositories.base.BaseMapMemoryDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersColorsMemoryDataSource @Inject constructor() : BaseMapMemoryDataSource<Int, Int>()
