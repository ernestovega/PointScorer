package com.etologic.pointscorer.app.games.base.game

import android.net.Uri
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.BuildConfig
import com.etologic.pointscorer.bussiness.model.Player
import com.etologic.pointscorer.bussiness.use_cases.*
import com.etologic.pointscorer.common.Constants.A_MINUTE_IN_MILLIS
import com.etologic.pointscorer.common.Constants.A_SECOND_IN_MILLIS
import com.etologic.pointscorer.common.Constants.DEFAULT_INITIAL_POINTS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getInitialPointsUseCase: GetInitialPointsUseCase,
    private val savePlayerNameUseCase: SavePlayerNameUseCase,
    private val savePlayerColorUseCase: SavePlayerColorUseCase,
    private val savePlayerBackgroundUseCase: SavePlayerBackgroundUseCase,
    private val restorePlayerPointsUseCase: RestorePlayerPointsUseCase,
) : ViewModel() {

    var initialPoints: Int = DEFAULT_INITIAL_POINTS
    var gamePlayersNum: Int? = null
    private val countDownDuration = if (BuildConfig.DEBUG) 5 * A_SECOND_IN_MILLIS else A_MINUTE_IN_MILLIS
    private val adCountDownInterval = countDownDuration
    var shouldShowGameInterstitialAd = true
        set(value) {
            field = value
            if (!value) {
                countDownTimer.cancel()
            }
        }
    private val countDownTimer = object : CountDownTimer(countDownDuration, adCountDownInterval) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            if (shouldShowGameInterstitialAd) {
                _showAdWillBeShownDialog.value = true
                _showAdWillBeShownDialog.value = null //To avoid execution on reloads
                shouldShowGameInterstitialAd = false
            }
        }
    }

    data class PlayerSettingsMenuInitialData(
        val playerId: Int,
        val playerInitialName: String,
        val playerInitialColor: Int,
        val playerHasCustomBackground: Boolean,
    )

    private val _showAdWillBeShownDialog = MutableLiveData<Boolean?>()
    fun showAdWillBeShownDialogObservable(): LiveData<Boolean?> = _showAdWillBeShownDialog
    private val _showPlayerSettingsMenuRequested = MutableLiveData<PlayerSettingsMenuInitialData?>()
    fun showPlayerSettingsMenuRequestedObservable(): LiveData<PlayerSettingsMenuInitialData?> = _showPlayerSettingsMenuRequested
    private val _changePlayerBackgroundRequested = MutableLiveData<Int?>()
    fun changeBackgroundRequestedObservable(): LiveData<Int?> = _changePlayerBackgroundRequested
    private val _restorePlayerPointsRequested = MutableLiveData<Boolean?>()
    fun restorePlayerPointsRequestedObservable(): LiveData<Boolean?> = _restorePlayerPointsRequested
    private val _playerNameChanged = MutableLiveData<Player>()
    fun playerNameChangedObservable(): LiveData<Player> = _playerNameChanged
    private val _playerColorChanged = MutableLiveData<Player>()
    fun playerColorChangedObservable(): LiveData<Player> = _playerColorChanged
    private val _playerBackgroundChanged = MutableLiveData<Player>()
    fun playerBackgroundChangedObservable(): LiveData<Player> = _playerBackgroundChanged
    private val _playerPointsRestored = MutableLiveData<Player>()
    fun playerPointsRestoredObservable(): LiveData<Player> = _playerPointsRestored

    init {
        loadInitialPoints()
    }

    private fun loadInitialPoints() {
        viewModelScope.launch {
            initialPoints = getInitialPointsUseCase.invoke()
        }
    }

    fun onPointsTouched() {
        countDownTimer.cancel()
        if (shouldShowGameInterstitialAd) {
            countDownTimer.start()
        }
    }

    fun showPlayerSettingsMenuRequested(playerSettingsMenuInitialData: PlayerSettingsMenuInitialData) {
        _showPlayerSettingsMenuRequested.value = playerSettingsMenuInitialData
        _showPlayerSettingsMenuRequested.value = null //To avoid execution on reloads
    }

    fun restoreGamePoints() {
        _restorePlayerPointsRequested.value = true
        _restorePlayerPointsRequested.value = null //To avoid execution on reloads
    }

    fun changePlayerName(playerId: Int, newName: String) {
        viewModelScope.launch {
            _playerNameChanged.postValue(
                savePlayerNameUseCase.invoke(playerId, newName)
            )
        }
    }

    fun changePlayerColor(playerId: Int, newColor: Int) {
        viewModelScope.launch {
            _playerColorChanged.postValue(
                savePlayerColorUseCase.invoke(playerId, newColor)
            )
        }
    }

    fun changePlayerBackground(playerId: Int) {
        _changePlayerBackgroundRequested.value = playerId
        _changePlayerBackgroundRequested.value = null //To avoid execution on reloads
    }

    fun savePlayerBackground(playerId: Int, newBackgroundUri: Uri?) {
        viewModelScope.launch {
            _playerBackgroundChanged.postValue(
                savePlayerBackgroundUseCase.invoke(playerId, newBackgroundUri)
            )
        }
    }

    fun restorePlayerBackground(playerId: Int) {
        savePlayerBackground(playerId, null)
    }

    fun restorePlayerPoints(playerId: Int) {
        viewModelScope.launch {
            _playerPointsRestored.postValue(
                restorePlayerPointsUseCase.invoke(playerId)
            )
        }
    }

    fun onFragmentResumed() {
        shouldShowGameInterstitialAd = true
    }

    fun onFragmentPaused() {
        shouldShowGameInterstitialAd = false
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }

}