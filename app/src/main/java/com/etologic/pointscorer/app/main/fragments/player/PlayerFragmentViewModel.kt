package com.etologic.pointscorer.app.main.fragments.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerFragmentViewModel
@Inject internal constructor(private val playersRepository: PlayersRepository) : ViewModel() {
    
    private val _playerPoints = MutableLiveData<Int>()
    fun livePlayerPoints(): LiveData<Int> = _playerPoints
    
    private val _playerName = MutableLiveData<String>()
    fun livePlayerName(): LiveData<String> = _playerName
    
    private val _playerColor = MutableLiveData<Int>()
    fun livePlayerColor(): LiveData<Int> = _playerColor
    
    fun getPlayerPoints(playerId: Int) {
        viewModelScope.launch {
            _playerPoints.postValue(playersRepository.getPlayerPoints(playerId))
        }
    }
    
    fun savePlayerPoints(playerId: Int, newPoints: Int) {
        viewModelScope.launch {
            playersRepository.savePlayerPoints(playerId, newPoints)
            _playerPoints.postValue(newPoints)
        }
    }
    
    fun getPlayerName(playerId: Int) {
        viewModelScope.launch {
            _playerName.postValue(playersRepository.getPlayerName(playerId))
        }
    }
    
    fun savePlayerName(playerId: Int, newName: String) {
        viewModelScope.launch {
            playersRepository.savePlayerName(playerId, newName)
            _playerName.postValue(newName)
        }
    }
    
    fun getPlayerColor(playerId: Int) {
        viewModelScope.launch {
            _playerColor.postValue(playersRepository.getPlayerColor(playerId))
        }
    }
    
    fun savePlayerColor(playerId: Int, newColor: Int) {
        viewModelScope.launch {
            playersRepository.savePlayerColor(playerId, newColor)
            _playerColor.postValue(newColor)
        }
    }
    
    companion object {
        enum class MainScreens {
            MENU,
            ONE_PLAYER,
            TWO_PLAYER,
            THREE_PLAYER,
            FOUR_PLAYER,
            FIVE_PLAYER,
            SIX_PLAYER,
            SEVEN_PLAYER,
            EIGHT_PLAYER,
            FINISH
        }
    }
    
}
