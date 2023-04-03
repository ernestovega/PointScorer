package com.etologic.pointscorer.bussiness.use_cases

import com.etologic.pointscorer.bussiness.model.entities.Player
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class GetPlayerUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    suspend fun invoke(playerId: Int): Player =
        playersRepository.getPlayer(playerId)

}