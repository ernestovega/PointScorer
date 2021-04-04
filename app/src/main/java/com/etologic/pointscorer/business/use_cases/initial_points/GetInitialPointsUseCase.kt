package com.etologic.pointscorer.business.use_cases.initial_points

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class GetInitialPointsUseCase
@Inject constructor(
    private val playersRepository: PlayersRepository
) {
    
    fun execute(): Int =
        playersRepository.initialPoints!!
    
}