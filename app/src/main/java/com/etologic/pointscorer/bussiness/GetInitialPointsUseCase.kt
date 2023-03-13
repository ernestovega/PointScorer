package com.etologic.pointscorer.bussiness

import com.etologic.pointscorer.data.repositories.initial_points.InitialPointsRepository
import javax.inject.Inject

class GetInitialPointsUseCase @Inject constructor(
    private val initialPointsRepository: InitialPointsRepository
) {

    suspend fun invoke(): Int =
        initialPointsRepository.getInitialPoints()

}