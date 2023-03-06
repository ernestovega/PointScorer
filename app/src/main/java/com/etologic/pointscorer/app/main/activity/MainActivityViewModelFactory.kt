package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.etologic.pointscorer.bussiness.*
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import javax.inject.Inject

class MainActivityViewModelFactory
@Inject internal constructor(
    private val getInitialPointsUseCase: GetInitialPointsUseCase,
    private val saveInitialPointsUseCase: SaveInitialPointsUseCase,
    private val resetAllPlayersPointsUseCase: ResetAllPlayersPointsUseCase,
    private val resetAllPlayersNamesAndColorsUseCase: ResetAllPlayersNamesAndColorsUseCase,
    private val invalidateUseCase: InvalidateUseCase,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainActivityViewModel(
            getInitialPointsUseCase,
            saveInitialPointsUseCase,
            resetAllPlayersPointsUseCase,
            resetAllPlayersNamesAndColorsUseCase,
            invalidateUseCase,
        ) as T

}
