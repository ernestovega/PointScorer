package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.NavigationData
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens
import com.etologic.pointscorer.app.main.activity.MainActivityNavigator.Screens.MENU
import com.etologic.pointscorer.common.Constants.DEFAULT_INITIAL_POINTS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {

    private val _navigationData = MutableLiveData<NavigationData>()
    val navigationDataObservable: LiveData<NavigationData> = _navigationData
    private val _shouldRestoreAllPoints = MutableLiveData<Int>()
    val shouldRestoreAllPointsObservable: LiveData<Int> = _shouldRestoreAllPoints
    private val _gameInterstitialAd = MutableLiveData<MyInterstitialAd?>()
    val gameInterstitialAdObservable: LiveData<MyInterstitialAd?> = _gameInterstitialAd

    var shouldShowBannerAds = true
    var shouldShowGameInterstitialAd = true
    var initialPoints = DEFAULT_INITIAL_POINTS

    init {
        navigateTo(MENU)
    }

    fun navigateTo(screen: Screens) {
        _navigationData.postValue(NavigationData(screen))
    }

    fun navigateTo(navigationData: NavigationData) {
        _navigationData.postValue(navigationData)
    }

    fun restoreOneGamePoints(numberOfPlayersInTheGame: Int) {
        _shouldRestoreAllPoints.value = numberOfPlayersInTheGame //To control where will be executed
        _shouldRestoreAllPoints.value = 0 //To avoid execution on reloads
    }

    fun showGameInterstitialAd(myInterstitialAd: MyInterstitialAd?) {
        _gameInterstitialAd.postValue(myInterstitialAd)
    }

    override fun onCleared() {
        super.onCleared()
    }

}
