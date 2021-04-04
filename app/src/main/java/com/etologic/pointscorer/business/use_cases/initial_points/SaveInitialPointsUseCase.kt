package com.etologic.pointscorer.business.use_cases.initial_points

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class SaveInitialPointsUseCase
@Inject constructor(
    private val playersRepository: PlayersRepository
) {
    
    fun execute(newInitialPoints: Int) {
        playersRepository.saveInitialPoints(newInitialPoints)
    }
    
}