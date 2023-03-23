package com.etologic.pointscorer.bussiness.use_cases

import com.etologic.pointscorer.data.repositories.initial_points.InitialPointsRepository
import javax.inject.Inject

class GetInitialPointsUseCase @Inject constructor(
    private val initialPointsRepository: InitialPointsRepository
) {

    suspend fun invoke(): Int =
        initialPointsRepository.getInitialPoints()

}