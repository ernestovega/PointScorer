package com.etologic.pointscorer.business.use_cases.player.name

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class SavePlayerNameUseCase
@Inject internal constructor(
    private val playersRepository: PlayersRepository,
) {
    
    fun execute(playerId: Int, newName: String) {
        playersRepository.savePlayerName(playerId, newName)
    }
    
}
