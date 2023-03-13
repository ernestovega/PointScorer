package com.etologic.pointscorer.bussiness

import android.net.Uri
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class SaveNewBackgroundUseCase @Inject constructor(
    private val playersRepository: PlayersRepository
) {

    suspend fun invoke(playerId: Int, newBackgroundUri: Uri?): Uri? =
        playersRepository.savePlayerBackground(playerId, newBackgroundUri)

}