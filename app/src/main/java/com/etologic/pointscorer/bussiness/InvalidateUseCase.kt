package com.etologic.pointscorer.bussiness

import com.etologic.pointscorer.data.repositories.initial_points.InitialPointsRepository
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class InvalidateUseCase @Inject constructor(
    private val playersRepository: PlayersRepository,
    private val initialPointsRepository: InitialPointsRepository,
) {

    fun invoke() {
        playersRepository.invalidate()
        initialPointsRepository.invalidate()
    }

}