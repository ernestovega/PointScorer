package com.etologic.pointscorer.business.use_cases.player.name

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class GetPlayerNameUseCase
@Inject internal constructor(
    private val playersRepository: PlayersRepository,
) {
    
    fun execute(playerId: Int): String =
        playersRepository.getPlayerName(playerId)
    
}
