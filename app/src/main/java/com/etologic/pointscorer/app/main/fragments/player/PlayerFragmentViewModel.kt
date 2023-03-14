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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerFragmentViewModel @Inject constructor(
    private val getPlayerNameUseCase: GetPlayerNameUseCase,
    private val getPlayerColorUseCase: GetPlayerColorUseCase,
    private val getPlayerPointsUseCase: GetPlayerPointsUseCase,
    private val getPlayerBackgroundUseCase: GetPlayerBackgroundUseCase,
    private val add1PointToAPlayerUseCase: Add1PointToAPlayerUseCase,
    private val substract1PointToAPlayerUseCase: Substract1PointToAPlayerUseCase,
) : ViewModel() {

    var playerId: Int = 0
    var gamePlayersNum: Int = 0
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

    fun initScreen(playerId: Int, gamePlayersNum: Int) {
        this.playerId = playerId
        this.gamePlayersNum = gamePlayersNum
        loadPlayerName()
        loadPlayerColor()
        loadPlayerBackground()
        loadPlayerPoints()
    }

    fun loadPlayerName() {
        viewModelScope.launch {
            _playerName.postValue(
                getPlayerNameUseCase.invoke(playerId)
            )
        }
    }

    fun loadPlayerColor() {
        viewModelScope.launch {
            _playerColor.postValue(
                getPlayerColorUseCase.invoke(playerId)
            )
        }
    }

    fun loadPlayerBackground() {
        viewModelScope.launch {
            _playerBackground.postValue(
                getPlayerBackgroundUseCase.invoke(playerId)
            )
        }
    }

    fun loadPlayerPoints() {
        viewModelScope.launch {
            _playerPoints.postValue(
                getPlayerPointsUseCase.invoke(playerId)
            )
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

    fun auxPointsAnimationEnded() {
        playerAuxPointsEnabled = false
        _playerAuxPoints.postValue(0)
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }

}
