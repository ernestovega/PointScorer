package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.MainScreens.FINISH
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.MainScreens.MENU
import javax.inject.Inject

class MainActivityViewModel
@Inject internal constructor(
) : ViewModel() {
    
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
    
    private val _screen = MutableLiveData<MainScreens>()
    internal fun getScreen(): LiveData<MainScreens> = _screen
    
    private val _shouldRestoreAllPoints = MutableLiveData<Int>()
    internal fun getShouldRestoreAllPoints(): LiveData<Int> = _shouldRestoreAllPoints
    
    internal fun navigateTo(screen: MainScreens) {
        _screen.postValue(screen)
    }
    
    internal fun navigateBack() {
        navigateTo(
            when (_screen.value) {
                MENU -> FINISH
                else -> MENU
            }
        )
    }
    
    internal fun restoreAllPlayersPoints(numberOfPlayers: Int) {
        _shouldRestoreAllPoints.setValue(numberOfPlayers) //For control where is been executed
        _shouldRestoreAllPoints.setValue(0) //For avoid execution on reloads
    }
}
