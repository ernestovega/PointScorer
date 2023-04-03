package com.etologic.pointscorer.bussiness.use_cases

import com.etologic.pointscorer.common.Constants
import com.etologic.pointscorer.bussiness.model.exceptions.MaxPointsReachedException
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class Add1PointToAPlayerUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    @Throws(MaxPointsReachedException::class)
    suspend fun invoke(playerId: Int): Int {
        val oldPoints = playersRepository.getPlayer(playerId).points
        val newPoints = oldPoints + 1
        if (oldPoints < Constants.MAX_POINTS) {
            return playersRepository.savePlayerPoints(playerId, newPoints).points
        } else {
            throw MaxPointsReachedException()
        }
    }

}