package com.etologic.pointscorer.app.main.fragments.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class PlayerFragmentViewModelFactory
@Inject internal constructor(private val playersRepository: PlayersRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PlayerFragmentViewModel(playersRepository) as T
}
