package com.etologic.pointscorer.business.use_cases.player.points

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class RestoreAllPlayersPointsUseCase
@Inject internal constructor(
    private val playersRepository: PlayersRepository,
) {
    
    fun execute() {
        playersRepository.restoreAllPlayersPoints()
    }
    
}
