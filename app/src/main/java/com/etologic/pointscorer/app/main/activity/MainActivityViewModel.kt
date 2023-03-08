package com.etologic.pointscorer.app.main.activity

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.NavigationData
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.common.ads.MyRewardedAd
import com.etologic.pointscorer.app.common.utils.MyAnimationUtils
import com.etologic.pointscorer.bussiness.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getInitialPointsUseCase: GetInitialPointsUseCase,
    private val saveInitialPointsUseCase: SaveInitialPointsUseCase,
    private val resetAllPlayersPointsUseCase: ResetAllPlayersPointsUseCase,
    private val resetAllPlayersNamesAndColorsUseCase: ResetAllPlayersNamesAndColorsUseCase,
    private val invalidateUseCase: InvalidateUseCase,
) : ViewModel() {

    companion object {
        const val ONE_MINUTE_IN_MILLIS = 1 * 1000L
        const val TOKEN_HANDLER_GAME_AD = "TOKEN_HANDLER_GAME_AD"
        const val TOKEN_HANDLER_CAN_RESTART_GAME_AD_COUNTDOWN = "TOKEN_HANDLER_CAN_RESTART_GAME_AD_COUNTDOWN"
    }

    private val _screen = MutableLiveData<NavigationData>()
    val screenObservable: LiveData<NavigationData> = _screen
    private val _initialPoints = MutableLiveData<Int>()
    val initialPointsObservable: LiveData<Int> = _initialPoints
    private val _shouldRestoreAllPoints = MutableLiveData<Int>()
    val shouldRestoreAllPointsObservable: LiveData<Int> = _shouldRestoreAllPoints

    var shouldShowAds = true
    var myRewardedAd: MyRewardedAd? = null
    var myInterstitialAdForShowLove: MyInterstitialAd? = null
    var myInterstitialAdForGame: MyInterstitialAd? = null
    private val gameAdHandler: Handler = Handler(Looper.getMainLooper())
    private var gameAdHandlerRunnable: Runnable? = null
    private var canRestartTheCountDownToShowAdHandler: Handler = Handler(Looper.getMainLooper())
    private var canRestartTheCountDownToShowGameAdHandlerRunnable: Runnable? = null
    var canRestartTheCountDownToShowAd: Boolean = true

    init {
        navigateTo(NavigationData.AppScreens.MENU)
    }

    fun navigateTo(screen: NavigationData.AppScreens) {
        _screen.postValue(NavigationData(screen))
    }

    fun navigateTo(navigationData: NavigationData) {
        _screen.postValue(navigationData)
    }

    fun getInitialPoints() {
        viewModelScope.launch {
            _initialPoints.postValue(getInitialPointsUseCase.invoke())
        }
    }

    fun saveInitialPoints(newInitialPoints: Int) {
        viewModelScope.launch {
            saveInitialPointsUseCase.invoke(newInitialPoints)
            _initialPoints.postValue(newInitialPoints)
        }
    }

    fun restoreAllGamesPoints() {
        viewModelScope.launch {
            resetAllPlayersPointsUseCase.invoke()
        }
    }

    fun restoreAllGamesNamesAndColors() {
        viewModelScope.launch {
            resetAllPlayersNamesAndColorsUseCase.invoke()
        }
    }

    fun restoreOneGamePoints(numberOfPlayersInTheGame: Int) {
        _shouldRestoreAllPoints.value = numberOfPlayersInTheGame //To control where will be executed
        _shouldRestoreAllPoints.value = 0 //To avoid execution on reloads
    }

    fun restartCountDownToShowGameAd() {
        cancelHandlerCountDownToShowGameAd()
        if (canRestartTheCountDownToShowAd) {
            gameAdHandlerRunnable = gameAdHandler.postDelayed(
                ONE_MINUTE_IN_MILLIS,
                TOKEN_HANDLER_GAME_AD
            ) { navigateTo(NavigationData.AppScreens.GAME_INTERSTITIAL_COUNTDOWN) }
        }
    }

    fun cancelHandlerCountDownToShowGameAd() {
        gameAdHandler.removeCallbacksAndMessages(TOKEN_HANDLER_GAME_AD)
    }

    fun avoidRestartTheCountDownToShowGameAdDuringAuxPointsAnimationDuration() {
        canRestartTheCountDownToShowGameAdHandlerRunnable =
            gameAdHandler.postDelayed(
                MyAnimationUtils.AUX_POINTS_FADE_OUT_ANIMATION_DURATION,
                TOKEN_HANDLER_CAN_RESTART_GAME_AD_COUNTDOWN
            ) { canRestartTheCountDownToShowAd = true }
    }

    override fun onCleared() {
        myRewardedAd = null
        myInterstitialAdForShowLove = null
        myInterstitialAdForGame = null
        invalidateUseCase.invoke()
        cancelHandlerCountDownToShowGameAd()
        cancelCanRestartHandlerCountDownToShowGameAd()
        super.onCleared()
    }

    private fun cancelCanRestartHandlerCountDownToShowGameAd() {
        canRestartTheCountDownToShowAdHandler.removeCallbacksAndMessages(TOKEN_HANDLER_CAN_RESTART_GAME_AD_COUNTDOWN)
    }

    fun cancelAdsCountDowns() {
        cancelHandlerCountDownToShowGameAd()
        cancelCanRestartHandlerCountDownToShowGameAd()
    }

}
