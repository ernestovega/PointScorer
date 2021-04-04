package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.MainScreens.FINISH
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.MainScreens.MENU
import com.etologic.pointscorer.business.use_cases.initial_points.GetInitialPointsUseCase
import com.etologic.pointscorer.business.use_cases.initial_points.SaveInitialPointsUseCase
import com.etologic.pointscorer.business.use_cases.player.color.GetPlayerColorUseCase
import com.etologic.pointscorer.business.use_cases.player.color.SavePlayerColorUseCase
import com.etologic.pointscorer.business.use_cases.player.name.GetPlayerNameUseCase
import com.etologic.pointscorer.business.use_cases.player.name.SavePlayerNameUseCase
import com.etologic.pointscorer.business.use_cases.player.points.GetPlayerPointsUseCase
import com.etologic.pointscorer.business.use_cases.player.points.RestoreAllPlayersPointsUseCase
import com.etologic.pointscorer.business.use_cases.player.points.SavePlayerPointsUseCase
import javax.inject.Inject

class MainActivityViewModel
@Inject internal constructor(
    private val getInitialPointsUseCase: GetInitialPointsUseCase,
    private val saveInitialPointsUseCase: SaveInitialPointsUseCase,
    private val restoreAllPlayersPointsUseCase: RestoreAllPlayersPointsUseCase,
    private val getPlayerPointsUseCase: GetPlayerPointsUseCase,
    private val savePlayerPointsUseCase: SavePlayerPointsUseCase,
    private val getPlayerNameUseCase: GetPlayerNameUseCase,
    private val savePlayerNameUseCase: SavePlayerNameUseCase,
    private val getPlayerColorUseCase: GetPlayerColorUseCase,
    private val savePlayerColorUseCase: SavePlayerColorUseCase
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
    
    internal fun restoreGamePlayersPoints(numberOfPlayers: Int) {
        _shouldRestoreAllPoints.value = numberOfPlayers //For control where is been executed
        _shouldRestoreAllPoints.value = 0 //For avoid execution on reloads
    }
    
    //NEW USE CASES
    fun getInitialPoints(): Int =
        getInitialPointsUseCase.execute()
    
    fun saveInitialPoints(newInitialPoints: Int) {
        saveInitialPointsUseCase.execute(newInitialPoints)
    }
    
    fun restoreAllPoints() {
        restoreAllPlayersPointsUseCase.execute()
    }
    
    fun getPlayerPoints(playerId: Int): Int =
        getPlayerPointsUseCase.execute(playerId)
    
    fun savePlayerPoints(playerId: Int, newPoints: Int) {
        savePlayerPointsUseCase.execute(playerId, newPoints)
    }
    
    fun getPlayerName(playerId: Int): String =
        getPlayerNameUseCase.execute(playerId)
    
    fun savePlayerName(playerId: Int, newName: String) {
        savePlayerNameUseCase.execute(playerId, newName)
    }
    
    fun getPlayerColor(playerId: Int): Int =
        getPlayerColorUseCase.execute(playerId)
    
    fun savePlayerColor(playerId: Int, newPlayerColor: Int) {
        savePlayerColorUseCase.execute(playerId, newPlayerColor)
    }
    
}
