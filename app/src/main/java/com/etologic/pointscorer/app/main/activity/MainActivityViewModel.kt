package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.common.ads.MyRewardedAd
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.NavigationData
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens.MENU
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

    private val _navigationData = MutableLiveData<NavigationData>()
    val navigationDataObservable: LiveData<NavigationData> = _navigationData
    private val _initialPoints = MutableLiveData<Int>()
    val initialPointsObservable: LiveData<Int> = _initialPoints
    private val _shouldRestoreAllPoints = MutableLiveData<Int>()
    val shouldRestoreAllPointsObservable: LiveData<Int> = _shouldRestoreAllPoints
    private val _gameInterstitialAd = MutableLiveData<MyInterstitialAd?>()
    val gameInterstitialAdObservable: LiveData<MyInterstitialAd?> = _gameInterstitialAd

    var shouldShowBannerAds = true
    var shouldShowGameInterstitialAd = true
    var myRewardedAd: MyRewardedAd? = null
    var myInterstitialAdForShowLove: MyInterstitialAd? = null

    init {
        navigateTo(MENU)
    }

    fun navigateTo(screen: Screens) {
        _navigationData.postValue(NavigationData(screen))
    }

    fun navigateTo(navigationData: NavigationData) {
        _navigationData.postValue(navigationData)
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

    fun showGameInterstitialAd(myInterstitialAd: MyInterstitialAd?) {
        _gameInterstitialAd.postValue(myInterstitialAd)
    }

    override fun onCleared() {
        myRewardedAd = null
        myInterstitialAdForShowLove = null
        invalidateUseCase.invoke()
        super.onCleared()
    }

}
