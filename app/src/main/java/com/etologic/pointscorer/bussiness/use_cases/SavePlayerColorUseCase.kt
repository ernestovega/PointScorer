package com.etologic.pointscorer.bussiness.use_cases

import com.etologic.pointscorer.bussiness.model.Player
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class SavePlayerColorUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    suspend fun invoke(playerId: Int, newColor: Int): Player =
        playersRepository.savePlayerColor(playerId, newColor)

}