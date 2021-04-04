package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.ViewModel
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
import javax.inject.Inject

class MainActivityViewModelFactory
@Inject internal constructor(
    private val getInitialPointsUseCase: GetInitialPointsUseCase,
    private val saveInitialPointsUseCase: SaveInitialPointsUseCase,
    private val restoreAllPlayersPointsUseCase: RestoreAllPlayersPointsUseCase,
    private val getPlayerPointsUseCase: GetPlayerPointsUseCase,
    private val savePlayerPointsUseCase: SavePlayerPointsUseCase,
    private val getPlayerNameUseCase: GetPlayerNameUseCase,
    private val savePlayerNameUseCase: SavePlayerNameUseCase,
    private val getPlayerColorUseCase: GetPlayerColorUseCase,
    private val savePlayerColorUseCase: SavePlayerColorUseCase
) : ViewModelProvider.NewInstanceFactory() {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainActivityViewModel(
            getInitialPointsUseCase,
            saveInitialPointsUseCase,
            restoreAllPlayersPointsUseCase,
            getPlayerPointsUseCase,
            savePlayerPointsUseCase,
            getPlayerNameUseCase,
            savePlayerNameUseCase,
            getPlayerColorUseCase,
            savePlayerColorUseCase
        ) as T
}
