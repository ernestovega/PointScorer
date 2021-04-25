package com.etologic.pointscorer.app.main.fragments.player

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import dagger.Module
import dagger.Provides

@Module
class PlayerFragmentModule {
    
    @Provides
    internal fun provideMainActivityViewModelFactory(playersRepository: PlayersRepository): PlayerFragmentViewModelFactory =
        PlayerFragmentViewModelFactory(playersRepository)
}
