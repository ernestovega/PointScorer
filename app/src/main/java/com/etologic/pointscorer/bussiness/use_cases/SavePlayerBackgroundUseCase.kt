package com.etologic.pointscorer.bussiness.use_cases

import android.net.Uri
import com.etologic.pointscorer.bussiness.model.Player
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class SavePlayerBackgroundUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    suspend fun invoke(playerId: Int, newBackgroundUri: Uri?): Player =
        playersRepository.savePlayerBackground(playerId, newBackgroundUri)

}