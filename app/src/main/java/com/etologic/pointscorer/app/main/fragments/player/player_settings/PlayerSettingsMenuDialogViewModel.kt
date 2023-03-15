package com.etologic.pointscorer.app.main.fragments.player.player_settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.bussiness.*
import com.etologic.pointscorer.common.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerSettingsMenuDialogViewModel @Inject constructor(
    private val getInitialPointsUseCase: GetInitialPointsUseCase,
    private val savePlayerNameUseCase: SavePlayerNameUseCase,
    private val savePlayerColorUseCase: SavePlayerColorUseCase,
    private val restoreOnePlayerPointsUseCase: RestoreOnePlayerPointsUseCase,
) : ViewModel() {

    var playerId: Int? = null
    var initialPoints = Constants.DEFAULT_INITIAL_POINTS

    private val _playerNameChanged = MutableLiveData<String>()
    fun playerNameChangedObservable(): LiveData<String> = _playerNameChanged
    private val _playerColorChanged = MutableLiveData<Int>()
    fun playerColorChangedObservable(): LiveData<Int> = _playerColorChanged
    private val _playerPointsRestored = MutableLiveData<Int>()
    fun playerPointsRestoredObservable(): LiveData<Int> = _playerPointsRestored

    init {
        loadInitialPoints()
    }

    private fun loadInitialPoints() {
        viewModelScope.launch {
            initialPoints = getInitialPointsUseCase.invoke()
        }
    }

    fun savePlayerName(newName: String) {
        playerId?.let { playerId ->
            viewModelScope.launch {
                _playerNameChanged.postValue(
                    savePlayerNameUseCase.invoke(playerId, newName)
                )
            }
        }
    }

    fun savePlayerColor(newColor: Int) {
        playerId?.let { playerId ->
            viewModelScope.launch {
                _playerColorChanged.postValue(
                    savePlayerColorUseCase.invoke(playerId, newColor)
                )
            }
        }
    }

    fun restorePlayerPoints() {
        playerId?.let { playerId ->
            viewModelScope.launch {
                _playerPointsRestored.postValue(
                    restoreOnePlayerPointsUseCase.invoke(playerId)
                )
            }
        }
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }

}
