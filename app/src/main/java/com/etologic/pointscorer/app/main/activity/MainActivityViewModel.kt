package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.app.common.ads.MyInterstitialAd
import com.etologic.pointscorer.app.common.ads.MyRewardedAd
import com.etologic.pointscorer.app.main.activity.MainActivityViewModel.Screens.MENU
import com.etologic.pointscorer.data.repositories.players.PlayersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel
@Inject internal constructor(private val playersRepository: PlayersRepository) : ViewModel() {

    enum class Screens {
        MENU,
        GAME_ONE_PLAYER,
        GAME_TWO_PLAYERS,
        GAME_THREE_PLAYERS,
        GAME_FOUR_PLAYERS,
        GAME_FIVE_PLAYERS,
        GAME_SIX_PLAYERS,
        GAME_SEVEN_PLAYERS,
        GAME_EIGHT_PLAYERS,
        GAME_INTERSTITIAL_COUNTDOWN,
        GAME_INTERSTITIAL,
        FINISH
    }

    private val _screen = MutableLiveData<Screens>()
    val screenObservable: LiveData<Screens> = _screen
    private val _initialPoints = MutableLiveData<Int>()
    val initialPointsObservable: LiveData<Int> = _initialPoints
    private val _shouldRestoreAllPoints = MutableLiveData<Int>()
    val shouldRestoreAllPointsObservable: LiveData<Int> = _shouldRestoreAllPoints

    var shouldShowAds = true
    var myRewardedAd: MyRewardedAd? = null
    var myInterstitialAdForShowLove: MyInterstitialAd? = null
    var myInterstitialAdForGame: MyInterstitialAd? = null

    init {
        navigateTo(MENU)
    }

    fun navigateTo(screen: Screens) {
        _screen.postValue(screen)
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

    fun restoreAllGamesNamesAndColors() {
        viewModelScope.launch {
            playersRepository.restoreAllPlayersNamesAndColors()
        }
    }

    fun restoreOneGamePoints(numberOfPlayersInTheGame: Int) {
        _shouldRestoreAllPoints.value = numberOfPlayersInTheGame //To control where will be executed
        _shouldRestoreAllPoints.value = 0 //To avoid execution on reloads
    }

    override fun onCleared() {
        myRewardedAd = null
        myInterstitialAdForShowLove = null
        myInterstitialAdForGame = null
        super.onCleared()
    }

}
