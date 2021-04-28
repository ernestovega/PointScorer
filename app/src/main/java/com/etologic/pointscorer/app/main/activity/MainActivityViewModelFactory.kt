package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class MainActivityViewModelFactory
@Inject internal constructor(private val playersRepository: PlayersRepository) : ViewModelProvider.NewInstanceFactory() {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainActivityViewModel(playersRepository,) as T
}
