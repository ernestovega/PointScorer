package com.etologic.pointscorer.bussiness

import com.etologic.pointscorer.common.Constants
import com.etologic.pointscorer.data.exceptions.MinPointsReachedException
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class Substract1PointToAPlayerUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    @Throws(MinPointsReachedException::class)
    suspend fun invoke(playerId: Int): Int {
        val oldPoints = playersRepository.getPlayerPoints(playerId)
        val newPoints = oldPoints - 1
        if (oldPoints > Constants.MIN_POINTS) {
            playersRepository.savePlayerPoints(playerId, newPoints)
            return newPoints
        } else {
            throw MinPointsReachedException()
        }
    }

}