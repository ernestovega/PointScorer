package com.etologic.pointscorer.bussiness

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class RestoreAllPlayersUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    suspend fun invoke() {
        playersRepository.resetAllPlayersPoints()
        playersRepository.resetAllPlayersNames()
        playersRepository.resetAllPlayersColors()
        playersRepository.resetAllPlayersBackgrounds()
    }
}