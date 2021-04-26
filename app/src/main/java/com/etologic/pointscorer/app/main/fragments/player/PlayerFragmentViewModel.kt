package com.etologic.pointscorer.app.main.fragments.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment.Companion.MAX_POINTS
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment.Companion.MIN_POINTS
import com.etologic.pointscorer.app.main.fragments.player.PlayerFragment.Companion.UNABLED_COUNT
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerFragmentViewModel
@Inject internal constructor(private val playersRepository: PlayersRepository) : ViewModel() {
    
    var playerId = 0
    var gamePlayersNum: Int? = null
        get() {
            if (field == null)
                field = playerId / 10
            return field
        }
    private val _playerPoints = MutableLiveData<Int>()
    fun livePlayerPoints(): LiveData<Int> = _playerPoints
    
    private val _playerName = MutableLiveData<String>()
    fun livePlayerName(): LiveData<String> = _playerName
    
    private val _playerColor = MutableLiveData<Int>()
    fun livePlayerColor(): LiveData<Int> = _playerColor
    
    private val _playerCount = MutableLiveData<Int>()
    fun livePlayerCount(): LiveData<Int> = _playerCount
    
    init {
        viewModelScope.launch(Dispatchers.IO) {
            _playerName.postValue(playersRepository.getPlayerName(playerId))
            _playerColor.postValue(playersRepository.getPlayerColor(playerId))
            _playerPoints.postValue(playersRepository.getPlayerPoints(playerId))
        }
        _playerCount.value = UNABLED_COUNT
    }
    
    fun upClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            val playerPoints = playersRepository.getPlayerPoints(playerId)
            if (playerPoints < MAX_POINTS) {
                val newPoints = playerPoints + 1
                playersRepository.savePlayerPoints(playerId, newPoints)
                _playerPoints.postValue(newPoints)
                _playerCount.postValue(if (_playerCount.value == UNABLED_COUNT) 1 else _playerCount.value?.plus(1))
            }
        }
    }
    
    fun downClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            val playerPoints = playersRepository.getPlayerPoints(playerId)
            if (playerPoints > MIN_POINTS) {
                val newPoints = playerPoints - 1
                playersRepository.savePlayerPoints(playerId, newPoints)
                _playerPoints.postValue(newPoints)
                _playerCount.postValue(if (_playerCount.value == UNABLED_COUNT) -1 else _playerCount.value?.minus(1))
            }
        }
    }
    
    fun savePlayerName(newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.savePlayerName(playerId, newName)
            _playerName.postValue(newName)
        }
    }
    
    fun savePlayerColor(newColor: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playersRepository.savePlayerColor(playerId, newColor)
            _playerColor.postValue(newColor)
        }
    }
    
    fun restorePlayerPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            val initialPoints = playersRepository.getInitialPoints()
            playersRepository.savePlayerPoints(playerId, initialPoints)
            _playerPoints.postValue(initialPoints)
        }
    }
    
    fun countAnimationEnded() {
        _playerCount.postValue(UNABLED_COUNT)
    }
    
}
