package com.etologic.pointscorer.data.repositories.initial_points

import com.etologic.pointscorer.data.repositories.base.BaseMemoryDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitialPointsMemoryDataSource @Inject constructor(): BaseMemoryDataSource<Int>()