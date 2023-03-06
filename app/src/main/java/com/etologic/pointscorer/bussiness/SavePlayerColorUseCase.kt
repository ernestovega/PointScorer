package com.etologic.pointscorer.bussiness

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class SavePlayerColorUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    suspend fun invoke(playerId: Int, newColor: Int) =
        playersRepository.savePlayerColor(playerId, newColor)

}