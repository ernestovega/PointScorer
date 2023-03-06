package com.etologic.pointscorer.bussiness

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class GetPlayerPointsUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    suspend fun invoke(playerId: Int) =
        playersRepository.getPlayerPoints(playerId)

}