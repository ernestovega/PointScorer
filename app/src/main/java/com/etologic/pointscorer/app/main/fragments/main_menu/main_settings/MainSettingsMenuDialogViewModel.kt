package com.etologic.pointscorer.app.main.fragments.main_menu.main_settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.R
import com.etologic.pointscorer.bussiness.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSettingsMenuDialogViewModel @Inject constructor(
    private val getInitialPointsUseCase: GetInitialPointsUseCase,
    private val saveInitialPointsUseCase: SaveInitialPointsUseCase,
    private val restoreAllPlayersPointsUseCase: RestoreAllPlayersPointsUseCase,
    private val restoreAllPlayersNamesUseCase: RestoreAllPlayersNamesUseCase,
    private val restoreAllPlayersColorsUseCase: RestoreAllPlayersColorsUseCase,
    private val restoreAllPlayersBackgroundsUseCase: RestoreAllPlayersBackgroundsUseCase,
    private val restoreAllPlayersUseCase: RestoreAllPlayersUseCase,
) : ViewModel() {

    private val _initialPoints = MutableLiveData<Int>()
    fun initialPointsObservable(): LiveData<Int> = _initialPoints
    private val _toastStringId = MutableLiveData<Int>()
    fun toastStringIdObservable(): LiveData<Int> = _toastStringId

    fun loadInitialPoints() {
        viewModelScope.launch {
            _initialPoints.postValue(
                getInitialPointsUseCase.invoke()
            )
        }
    }

    fun saveInitialPoints(newPoints: Int) {
        viewModelScope.launch {
            saveInitialPointsUseCase.invoke(newPoints)
        }
    }

    fun restoreAllPoints() {
        viewModelScope.launch {
            restoreAllPlayersPointsUseCase.invoke()
            _toastStringId.postValue(R.string.all_players_points_restored)
        }
    }

    fun restoreAllNames() {
        viewModelScope.launch {
            restoreAllPlayersNamesUseCase.invoke()
            _toastStringId.postValue(R.string.all_players_names_restored)
        }
    }

    fun restoreAllColors() {
        viewModelScope.launch {
            restoreAllPlayersColorsUseCase.invoke()
            _toastStringId.postValue(R.string.all_players_colors_restored)
        }
    }

    fun restoreAllBackgrounds() {
        viewModelScope.launch {
            restoreAllPlayersBackgroundsUseCase.invoke()
            _toastStringId.postValue(R.string.all_players_backgrounds_restored)
        }
    }

    fun restoreEverything() {
        viewModelScope.launch {
            restoreAllPlayersUseCase.invoke()
            _toastStringId.postValue(R.string.everything_restored)
        }
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }

}
