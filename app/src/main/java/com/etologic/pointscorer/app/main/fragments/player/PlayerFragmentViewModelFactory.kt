package com.etologic.pointscorer.app.main.fragments.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.bussiness.*
import javax.inject.Inject

class PlayerFragmentViewModelFactory
@Inject internal constructor(
    private val getPlayerNameUseCase: GetPlayerNameUseCase,
    private val getPlayerColorUseCase: GetPlayerColorUseCase,
    private val getPlayerPointsUseCase: GetPlayerPointsUseCase,
    private val savePlayerNameUseCase: SavePlayerNameUseCase,
    private val savePlayerColorUseCase: SavePlayerColorUseCase,
    private val add1PointToAPlayerUseCase: Add1PointToAPlayerUseCase,
    private val substract1PointToAPlayerUseCase: Substract1PointToAPlayerUseCase,
    private val getInitialPointsUseCase: GetInitialPointsUseCase,
    private val resetPlayerPointsUseCase: ResetPlayerPointsUseCase,
    private val invalidateUseCase: InvalidateUseCase,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PlayerFragmentViewModel(
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
        ) as T

}
