package com.etologic.pointscorer.app.main.fragments.player.player_settings

import android.net.Uri
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
    private val getPlayerNameUseCase: GetPlayerNameUseCase,
    private val getPlayerColorUseCase: GetPlayerColorUseCase,
    private val getPlayerBackgroundUseCase: GetPlayerBackgroundUseCase,
    private val savePlayerNameUseCase: SavePlayerNameUseCase,
    private val savePlayerColorUseCase: SavePlayerColorUseCase,
    private val savePlayerBackgroundUseCase: SavePlayerBackgroundUseCase,
    private val restoreOnePlayerPointsUseCase: RestoreOnePlayerPointsUseCase,
) : ViewModel() {

    var initialPoints = Constants.DEFAULT_INITIAL_POINTS
    private var playerId: Int? = null

    private val _playerName = MutableLiveData<String>()
    fun playerNameObservable(): LiveData<String> = _playerName
    private val _playerColor = MutableLiveData<Int>()
    fun playerColorObservable(): LiveData<Int> = _playerColor
    private val _playerBackground = MutableLiveData<Uri?>()
    fun playerBackgroundObservable(): LiveData<Uri?> = _playerBackground
    private val _playerNameChanged = MutableLiveData<String>()
    fun playerNameChangedObservable(): LiveData<String> = _playerNameChanged
    private val _playerColorChanged = MutableLiveData<Int>()
    fun playerColorChangedObservable(): LiveData<Int> = _playerColorChanged
    private val _playerBackgroundChanged = MutableLiveData<Uri?>()
    fun playerBackgroundChangedObservable(): LiveData<Uri?> = _playerBackgroundChanged
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

    fun loadScreen(playerId: Int) {

        fun getPlayerName() {
            viewModelScope.launch {
                _playerName.postValue(
                    getPlayerNameUseCase.invoke(playerId)
                )
            }
        }

        fun getPlayerColor() {
            viewModelScope.launch {
                _playerColor.postValue(
                    getPlayerColorUseCase.invoke(playerId)
                )
            }
        }

        fun getPlayerBackground() {
            viewModelScope.launch {
                _playerBackground.postValue(
                    getPlayerBackgroundUseCase.invoke(playerId)
                )
            }
        }

        this.playerId = playerId
        getPlayerName()
        getPlayerColor()
        getPlayerBackground()
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

    fun savePlayerBackground(newBackgroundUri: Uri?) {
        playerId?.let { playerId ->
            viewModelScope.launch {
                _playerBackgroundChanged.postValue(
                    savePlayerBackgroundUseCase.invoke(playerId, newBackgroundUri)
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
