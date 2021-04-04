package com.etologic.pointscorer.business.use_cases.player.points

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class GetPlayerPointsUseCase
@Inject internal constructor(
    private val playersRepository: PlayersRepository,
) {
    
    fun execute(playerId: Int): Int =
        playersRepository.getPlayerPoints(playerId)
    
}
