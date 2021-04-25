package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.FINISH
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.MENU
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel
@Inject internal constructor(private val playersRepository: PlayersRepository) : ViewModel() {
    
    private val _screen = MutableLiveData<MainScreens>()
    fun liveScreen(): LiveData<MainScreens> = _screen
    
    private val _shouldRestoreAllPoints = MutableLiveData<Int>()
    fun liveShouldRestoreAllPoints(): LiveData<Int> = _shouldRestoreAllPoints
    
    fun navigateTo(screen: MainScreens) {
        _screen.postValue(screen)
    }
    
    fun navigateBack() {
        navigateTo(
            when (_screen.value) {
                MENU -> FINISH
                else -> MENU
            }
        )
    }
    
    fun restoreGamePlayersPoints(numberOfPlayers: Int) {
        _shouldRestoreAllPoints.value = numberOfPlayers //For control where is been executed
        _shouldRestoreAllPoints.value = 0 //For avoid execution on reloads
    }
    
    fun getInitialPoints() = runBlocking {
        withContext(Dispatchers.Default) {
            playersRepository.getInitialPoints()
        }
    }
    
    fun saveInitialPoints(newInitialPoints: Int) = runBlocking {
        withContext(Dispatchers.Default) {
            playersRepository.saveInitialPoints(newInitialPoints)
        }
    }
    
    fun restoreAllPoints() = runBlocking {
        withContext(Dispatchers.Default) {
            playersRepository.restoreAllPlayersPoints()
        }
    }
    
    fun getPlayerPoints(playerId: Int): Int = runBlocking {
        withContext(Dispatchers.Default) {
            playersRepository.getPlayerPoints(playerId)
        }
    }
    
    fun savePlayerPoints(playerId: Int, newPoints: Int) = runBlocking {
        withContext(Dispatchers.Default) {
            playersRepository.savePlayerPoints(playerId, newPoints)
        }
    }
    
    fun getPlayerName(playerId: Int): String = runBlocking {
        withContext(Dispatchers.Default) {
            playersRepository.getPlayerName(playerId)
        }
    }
    
    fun savePlayerName(playerId: Int, newName: String) = runBlocking {
        withContext(Dispatchers.Default) {
            playersRepository.savePlayerName(playerId, newName)
        }
    }
    
    fun getPlayerColor(playerId: Int): Int = runBlocking {
        withContext(Dispatchers.Default) {
            playersRepository.getPlayerColor(playerId)
        }
    }
    
    fun savePlayerColor(playerId: Int, newPlayerColor: Int) = runBlocking {
        withContext(Dispatchers.Default) {
            playersRepository.savePlayerColor(playerId, newPlayerColor)
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
