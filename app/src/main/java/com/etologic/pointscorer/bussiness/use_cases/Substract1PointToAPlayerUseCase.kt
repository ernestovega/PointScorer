package com.etologic.pointscorer.bussiness.use_cases

import com.etologic.pointscorer.common.Constants
import com.etologic.pointscorer.data.exceptions.MinPointsReachedException
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class Substract1PointToAPlayerUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    @Throws(MinPointsReachedException::class)
    suspend fun invoke(playerId: Int): Int {
        val oldPoints = playersRepository.getPlayer(playerId).points
        val newPoints = oldPoints - 1
        if (oldPoints > Constants.MIN_POINTS) {
            playersRepository.savePlayerPoints(playerId, newPoints)
            return playersRepository.getPlayer(playerId).points
        } else {
            throw MinPointsReachedException()
        }
    }

}