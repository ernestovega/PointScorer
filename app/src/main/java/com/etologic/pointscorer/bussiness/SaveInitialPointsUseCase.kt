package com.etologic.pointscorer.bussiness

import com.etologic.pointscorer.data.repositories.initial_points.InitialPointsRepository
import javax.inject.Inject

class SaveInitialPointsUseCase @Inject constructor(
    private val initialPointsRepository: InitialPointsRepository
) {

    suspend fun invoke(newInitialPoints: Int) {
        initialPointsRepository.saveInitialPoints(newInitialPoints)
    }

}