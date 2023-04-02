package com.etologic.pointscorer.app.games.base.player

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.bussiness.use_cases.Add1PointToAPlayerUseCase
import com.etologic.pointscorer.bussiness.use_cases.GetPlayerUseCase
import com.etologic.pointscorer.bussiness.use_cases.Subtract1PointToAPPlayerUseCase
import com.etologic.pointscorer.data.exceptions.MaxPointsReachedException
import com.etologic.pointscorer.data.exceptions.MinPointsReachedException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getPlayerUseCase: GetPlayerUseCase,
    private val add1PointToAPlayerUseCase: Add1PointToAPlayerUseCase,
    private val subtract1PointToAPPlayerUseCase: Subtract1PointToAPPlayerUseCase,
) : ViewModel() {

    var playerId: Int = 0
    var playerAuxPointsEnabled = false

    private val _playerAuxPoints = MutableLiveData<Int>()
    fun playerAuxPointsObservable(): LiveData<Int> = _playerAuxPoints
    private val _playerPoints = MutableLiveData<Int>()
    fun playerPointsObservable(): LiveData<Int> = _playerPoints
    private val _playerName = MutableLiveData<String>()
    fun playerNameObservable(): LiveData<String> = _playerName
    private val _playerColor = MutableLiveData<Int>()
    fun playerColorObservable(): LiveData<Int> = _playerColor
    private val _playerBackground = MutableLiveData<Uri?>()
    fun playerBackgroundObservable(): LiveData<Uri?> = _playerBackground

    fun loadScreen() {
        viewModelScope.launch {
            val player = getPlayerUseCase.invoke(playerId)
            _playerName.postValue(player.name)
            _playerColor.postValue(player.color)
            _playerBackground.postValue(player.background)
            _playerPoints.postValue(player.points)
        }
    }

    fun loadPlayerName() {
        viewModelScope.launch {
            _playerName.postValue(
                getPlayerUseCase.invoke(playerId).name
            )
        }
    }

    fun loadPlayerColor() {
        viewModelScope.launch {
            _playerColor.postValue(
                getPlayerUseCase.invoke(playerId).color
            )
        }
    }

    fun loadPlayerBackground() {
        viewModelScope.launch {
            _playerBackground.postValue(
                getPlayerUseCase.invoke(playerId).background
            )
        }
    }

    fun loadPlayerPoints() {
        viewModelScope.launch {
            _playerPoints.postValue(
                getPlayerUseCase.invoke(playerId).points
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
                val newPoints = subtract1PointToAPPlayerUseCase.invoke(playerId)
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
