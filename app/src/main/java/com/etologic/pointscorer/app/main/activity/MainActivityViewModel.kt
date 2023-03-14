package com.etologic.pointscorer.app.main.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etologic.pointscorer.common.Constants.DEFAULT_INITIAL_POINTS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {

    private val _gamePlayersPointsRestored = MutableLiveData<Int>()
    fun gamePlayersPointsRestoredObservable(): LiveData<Int> = _gamePlayersPointsRestored

    var shouldShowBannerAds = true
    var shouldShowGameInterstitialAd = true
    var initialPoints = DEFAULT_INITIAL_POINTS

    fun restoreGamePoints(numberOfPlayersInTheGame: Int) {
        _gamePlayersPointsRestored.value = numberOfPlayersInTheGame //To control where will be executed
        _gamePlayersPointsRestored.value = 0 //To avoid execution on reloads
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }

}
