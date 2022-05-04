package com.etologic.pointscorer.app.main.fragments.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.data.exceptions.MaxPointsReachedException
import com.etologic.pointscorer.data.exceptions.MinPointsReachedException
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerFragmentViewModel
@Inject internal constructor(private val playersRepository: PlayersRepository) : ViewModel() {

    var playerId: Int = 0
    var gamePlayersNum: Int? = null
        get() {
            if (field == null)
                field = playerId / 10
            return field
        }
    var playerAnimatePoints = true
    var playerCountEnabled = false
    private val _playerPoints = MutableLiveData<Int>()
    fun livePlayerPoints(): LiveData<Int> = _playerPoints
    private val _playerCount = MutableLiveData<Int>()
    fun livePlayerCount(): LiveData<Int> = _playerCount
    private val _playerName = MutableLiveData<String>()
    fun livePlayerName(): LiveData<String> = _playerName
    private val _playerColor = MutableLiveData<Int>()
    fun livePlayerColor(): LiveData<Int> = _playerColor

    fun initScreen(playerId: Int?) {
        playerId?.let {
            this.playerId = it
            viewModelScope.launch {
                _playerName.postValue(playersRepository.getPlayerName(playerId))
                _playerColor.postValue(playersRepository.getPlayerColor(playerId))
                _playerPoints.postValue(playersRepository.getPlayerPoints(playerId))
                playerAnimatePoints = playersRepository.getPlayerAnimatePoints(playerId)
            }
        }
    }

    fun upClicked() {
        viewModelScope.launch {
            try {
                val newPoints = playersRepository.plus1PlayerPoint(playerId)
                playerCountEnabled = true
                _playerPoints.postValue(newPoints)
                _playerCount.postValue((_playerCount.value ?: 0).plus(1))
            } catch (_: MaxPointsReachedException) {
                playerCountEnabled = false
                _playerPoints.postValue(_playerPoints.value)
                _playerCount.postValue(_playerCount.value ?: 0)
            }
        }
    }

    fun downClicked() {
        viewModelScope.launch {
            try {
                val newPoints = playersRepository.minus1PlayerPoint(playerId)
                playerCountEnabled = true
                _playerPoints.postValue(newPoints)
                _playerCount.postValue((_playerCount.value ?: 0).minus(1))
            } catch (_: MinPointsReachedException) {
                playerCountEnabled = false
                _playerPoints.postValue(_playerPoints.value)
                _playerCount.postValue(_playerCount.value ?: 0)
            }
        }
    }

    fun savePlayerName(newName: String) {
        viewModelScope.launch {
            playersRepository.savePlayerName(playerId, newName)
            _playerName.postValue(newName)
        }
    }

    fun savePlayerColor(newColor: Int) {
        viewModelScope.launch {
            playersRepository.savePlayerColor(playerId, newColor)
            _playerColor.postValue(newColor)
        }
    }

    fun animatePoints(animate: Boolean) {
        viewModelScope.launch {
            playersRepository.savePlayerAnimatePoints(playerId, animate)
            playerAnimatePoints = animate
        }
    }

    fun countAnimationEnded() {
        playerCountEnabled = false
        _playerCount.postValue(0)
    }


    fun restorePlayerPoints() {
        viewModelScope.launch {
            val initialPoints = playersRepository.getInitialPoints()
            playersRepository.resetPlayerPoints(playerId)
            _playerPoints.postValue(initialPoints)
        }
    }

    override fun onCleared() {
        super.onCleared()
        playersRepository.invalidate()
    }

}
