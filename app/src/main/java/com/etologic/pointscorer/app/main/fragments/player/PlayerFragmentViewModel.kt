package com.etologic.pointscorer.app.main.fragments.player

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.bussiness.*
import com.etologic.pointscorer.data.exceptions.MaxPointsReachedException
import com.etologic.pointscorer.data.exceptions.MinPointsReachedException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerFragmentViewModel @Inject constructor(
    private val getPlayerNameUseCase: GetPlayerNameUseCase,
    private val getPlayerColorUseCase: GetPlayerColorUseCase,
    private val getPlayerPointsUseCase: GetPlayerPointsUseCase,
    private val getPlayerBackgroundUseCase: GetPlayerBackgroundUseCase,
    private val savePlayerNameUseCase: SavePlayerNameUseCase,
    private val savePlayerColorUseCase: SavePlayerColorUseCase,
    private val add1PointToAPlayerUseCase: Add1PointToAPlayerUseCase,
    private val substract1PointToAPlayerUseCase: Substract1PointToAPlayerUseCase,
    private val getInitialPointsUseCase: GetInitialPointsUseCase,
    private val resetPlayerPointsUseCase: ResetPlayerPointsUseCase,
    private val saveNewBackgroundUseCase: SaveNewBackgroundUseCase,
    private val invalidateUseCase: InvalidateUseCase,
) : ViewModel() {

    var playerId: Int = 0
    var gamePlayersNum: Int = playerId / 10
    var playerAuxPointsEnabled = false
    private val _playerPoints = MutableLiveData<Int>()
    fun livePlayerPoints(): LiveData<Int> = _playerPoints
    private val _playerAuxPoints = MutableLiveData<Int>()
    fun livePlayerAuxPoints(): LiveData<Int> = _playerAuxPoints
    private val _playerName = MutableLiveData<String>()
    fun livePlayerName(): LiveData<String> = _playerName
    private val _playerColor = MutableLiveData<Int>()
    fun livePlayerColor(): LiveData<Int> = _playerColor
    private val _playerBackground = MutableLiveData<Uri?>()
    fun livePlayerBackground(): LiveData<Uri?> = _playerBackground

    fun initScreen(playerId: Int?) {
        playerId?.let {
            this.playerId = it
            viewModelScope.launch {
                _playerName.postValue(getPlayerNameUseCase.invoke(playerId))
                _playerColor.postValue(getPlayerColorUseCase.invoke(playerId))
                _playerBackground.postValue(getPlayerBackgroundUseCase.invoke(playerId))
                _playerPoints.postValue(getPlayerPointsUseCase.invoke(playerId))
            }
        }
    }

    fun upClicked() {
        viewModelScope.launch {
            try {
                val newPoints = add1PointToAPlayerUseCase.invoke(playerId)
                playerAuxPointsEnabled = true
                _playerPoints.postValue(newPoints)
                _playerAuxPoints.postValue((_playerAuxPoints.value ?: 0).plus(1))
            } catch (_: MaxPointsReachedException) {
                playerAuxPointsEnabled = false
                _playerPoints.postValue(_playerPoints.value)
                _playerAuxPoints.postValue(_playerAuxPoints.value ?: 0)
            }
        }
    }

    fun downClicked() {
        viewModelScope.launch {
            try {
                val newPoints = substract1PointToAPlayerUseCase.invoke(playerId)
                playerAuxPointsEnabled = true
                _playerPoints.postValue(newPoints)
                _playerAuxPoints.postValue((_playerAuxPoints.value ?: 0).minus(1))
            } catch (_: MinPointsReachedException) {
                playerAuxPointsEnabled = false
                _playerPoints.postValue(_playerPoints.value)
                _playerAuxPoints.postValue(_playerAuxPoints.value ?: 0)
            }
        }
    }

    fun savePlayerName(newName: String) {
        viewModelScope.launch {
            _playerName.postValue(savePlayerNameUseCase.invoke(playerId, newName))
        }
    }

    fun savePlayerColor(newColor: Int) {
        viewModelScope.launch {
            _playerColor.postValue(savePlayerColorUseCase.invoke(playerId, newColor))
        }
    }

    fun saveNewBackground(newBackgroundUri: Uri?) {
        viewModelScope.launch {
            _playerBackground.postValue(
                saveNewBackgroundUseCase.invoke(playerId, newBackgroundUri)
            )
        }
    }

    fun auxPointsAnimationEnded() {
        playerAuxPointsEnabled = false
        _playerAuxPoints.postValue(0)
    }


    fun restorePlayerPoints() {
        viewModelScope.launch {
            val initialPoints = getInitialPointsUseCase.invoke()
            resetPlayerPointsUseCase.invoke(playerId)
            _playerPoints.postValue(initialPoints)
        }
    }

    override fun onCleared() {
        invalidateUseCase.invoke()
        super.onCleared()
    }

}
