package com.etologic.pointscorer.app.main.fragments.player

import com.etologic.pointscorer.bussiness.*
import dagger.Module
import dagger.Provides

@Module
class PlayerFragmentModule {

    @Provides
    internal fun provideMainActivityViewModelFactory(
        getPlayerNameUseCase: GetPlayerNameUseCase,
        getPlayerColorUseCase: GetPlayerColorUseCase,
        getPlayerPointsUseCase: GetPlayerPointsUseCase,
        savePlayerNameUseCase: SavePlayerNameUseCase,
        savePlayerColorUseCase: SavePlayerColorUseCase,
        add1PointToAPlayerUseCase: Add1PointToAPlayerUseCase,
        substract1PointToAPlayerUseCase: Substract1PointToAPlayerUseCase,
        getInitialPointsUseCase: GetInitialPointsUseCase,
        resetPlayerPointsUseCase: ResetPlayerPointsUseCase,
        invalidateUseCase: InvalidateUseCase,
    ): PlayerFragmentViewModelFactory =
        PlayerFragmentViewModelFactory(
            getPlayerNameUseCase,
            getPlayerColorUseCase,
            getPlayerPointsUseCase,
            savePlayerNameUseCase,
            savePlayerColorUseCase,
            add1PointToAPlayerUseCase,
            substract1PointToAPlayerUseCase,
            getInitialPointsUseCase,
            resetPlayerPointsUseCase,
            invalidateUseCase,
        )

}
