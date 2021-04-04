package com.etologic.pointscorer.business.use_cases.player.points

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class SavePlayerPointsUseCase
@Inject internal constructor(
    private val playersRepository: PlayersRepository,
) {
    
    fun execute(playerId: Int, points: Int) {
        playersRepository.savePlayerPoints(playerId, points)
    }
    
}
