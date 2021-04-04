package com.etologic.pointscorer.business.use_cases.player.color

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class GetPlayerColorUseCase
@Inject internal constructor(
    private val playersRepository: PlayersRepository,
) {
    
    fun execute(playerId: Int): Int =
        playersRepository.getPlayerColor(playerId)
    
}
