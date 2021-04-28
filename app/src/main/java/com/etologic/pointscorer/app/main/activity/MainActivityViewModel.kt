package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.FINISH
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Companion.MainScreens.MENU
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel
@Inject internal constructor(private val playersRepository: PlayersRepository) : ViewModel() {
    
    private val _screen = MutableLiveData<MainScreens>()
    private val _initialPoints = MutableLiveData<Int>()
    private val _shouldRestoreAllPoints = MutableLiveData<Int>()
    val liveScreen: LiveData<MainScreens> = _screen
    val liveInitialPoints: LiveData<Int> = _initialPoints
    val liveShouldRestoreAllPoints: LiveData<Int> = _shouldRestoreAllPoints
    
    init {
        navigateTo(MENU)
    }
    
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
    
    fun getInitialPoints() {
        viewModelScope.launch {
            _initialPoints.postValue(playersRepository.getInitialPoints())
        }
    }
    
    fun saveInitialPoints(newInitialPoints: Int) {
        viewModelScope.launch {
            playersRepository.saveInitialPoints(newInitialPoints)
            _initialPoints.postValue(newInitialPoints)
        }
    }
    
    fun restoreAllGamesPoints() {
        viewModelScope.launch {
            playersRepository.restoreAllPlayersPoints()
        }
    }
    
    fun restoreOneGamePoints(numberOfPlayersInTheGame: Int) {
        _shouldRestoreAllPoints.value = numberOfPlayersInTheGame //For control where will be executed
        _shouldRestoreAllPoints.value = 0 //For avoid execution on reloads
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
