package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.business.use_cases.initial_points.GetInitialPointsUseCase
import com.etologic.pointscorer.business.use_cases.initial_points.SaveInitialPointsUseCase
import com.etologic.pointscorer.business.use_cases.player.color.GetPlayerColorUseCase
import com.etologic.pointscorer.business.use_cases.player.color.SavePlayerColorUseCase
import com.etologic.pointscorer.business.use_cases.player.name.GetPlayerNameUseCase
import com.etologic.pointscorer.business.use_cases.player.name.SavePlayerNameUseCase
import com.etologic.pointscorer.business.use_cases.player.points.GetPlayerPointsUseCase
import com.etologic.pointscorer.business.use_cases.player.points.RestoreAllPlayersPointsUseCase
import com.etologic.pointscorer.business.use_cases.player.points.SavePlayerPointsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class MainActivityModule {
    
    @Provides
    internal fun provideMainActivityViewModelFactory(
        getInitialPointsUseCase: GetInitialPointsUseCase,
        saveInitialPointsUseCase: SaveInitialPointsUseCase,
        restoreAllPlayersPointsUseCase: RestoreAllPlayersPointsUseCase,
        getPlayerPointsUseCase: GetPlayerPointsUseCase,
        savePlayerPointsUseCase: SavePlayerPointsUseCase,
        getPlayerNameUseCase: GetPlayerNameUseCase,
        savePlayerNameUseCase: SavePlayerNameUseCase,
        getPlayerColorUseCase: GetPlayerColorUseCase,
        savePlayerColorUseCase: SavePlayerColorUseCase
    )
        : MainActivityViewModelFactory =
        MainActivityViewModelFactory(
            getInitialPointsUseCase,
            saveInitialPointsUseCase,
            restoreAllPlayersPointsUseCase,
            getPlayerPointsUseCase,
            savePlayerPointsUseCase,
            getPlayerNameUseCase,
            savePlayerNameUseCase,
            getPlayerColorUseCase,
            savePlayerColorUseCase
        )
}
