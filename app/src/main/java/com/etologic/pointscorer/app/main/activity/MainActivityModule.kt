package com.etologic.pointscorer.app.main.activity

import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMainActivityViewModelFactory(playersRepository: PlayersRepository): MainActivityViewModelFactory =
        MainActivityViewModelFactory(playersRepository)
}
