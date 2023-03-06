package com.etologic.pointscorer.app.main.activity

import com.etologic.pointscorer.bussiness.*
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMainActivityViewModelFactory(
        getInitialPointsUseCase: GetInitialPointsUseCase,
        saveInitialPointsUseCase: SaveInitialPointsUseCase,
        resetAllPlayersPointsUseCase: ResetAllPlayersPointsUseCase,
        resetAllPlayersNamesAndColorsUseCase: ResetAllPlayersNamesAndColorsUseCase,
        invalidateUseCase: InvalidateUseCase,
    ): MainActivityViewModelFactory =
        MainActivityViewModelFactory(
            getInitialPointsUseCase,
            saveInitialPointsUseCase,
            resetAllPlayersPointsUseCase,
            resetAllPlayersNamesAndColorsUseCase,
            invalidateUseCase,
        )

}
