package com.etologic.pointscorer.bussiness.use_cases

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class RestoreAllPlayersColorsUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    suspend fun invoke() {
        playersRepository.restoreAllPlayersColors()
    }

}