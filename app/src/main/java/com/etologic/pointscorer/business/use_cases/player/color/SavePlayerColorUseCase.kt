package com.etologic.pointscorer.business.use_cases.player.color

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class SavePlayerColorUseCase
@Inject internal constructor(
    private val playersRepository: PlayersRepository,
) {
    
    fun execute(playerId: Int, points: Int) {
        playersRepository.savePlayerColor(playerId, points)
    }
    
}
